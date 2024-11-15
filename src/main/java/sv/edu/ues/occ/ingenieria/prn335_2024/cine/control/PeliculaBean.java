package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Pelicula;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@LocalBean
public class PeliculaBean extends AbstractDataPersist<Pelicula> implements Serializable {

    @PersistenceContext(unitName = "cinePU")
    private EntityManager em;

    private static final Logger logger = Logger.getLogger(PeliculaBean.class.getName());

    public PeliculaBean() {
        super(Pelicula.class);
    }

    @Override
    public void create(Pelicula registro) throws IllegalStateException, IllegalArgumentException {
        try {
            super.create(registro);
        } catch (IllegalArgumentException | IllegalStateException e) {
            logger.log(Level.SEVERE, "Error al crear la película", e);
            throw e;
        }
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    /**
     * Obtiene un rango de películas desde la base de datos.
     *
     * @param first Índice de inicio
     * @param max Número máximo de registros
     * @return Lista de películas en el rango indicado
     */
    public List<Pelicula> findRange(int first, int max) {
        try {
            return em.createQuery("SELECT p FROM Pelicula p", Pelicula.class)
                    .setFirstResult(first)
                    .setMaxResults(max)
                    .getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al obtener el rango de películas", e);
            return List.of();
        }
    }

    /**
     * Encuentra una película por su ID.
     *
     * @param idPelicula El ID de la película
     * @return La película encontrada
     * @throws IllegalArgumentException Si el ID es inválido
     */
    public Pelicula findById(final Integer idPelicula) {
        if (idPelicula == null || idPelicula <= 0) {
            throw new IllegalArgumentException("ID de película no válido");
        }
        return em.find(Pelicula.class, idPelicula);
    }

    /**
     * Obtiene todas las películas de la base de datos.
     *
     * @return Lista de todas las películas
     */
    public List<Pelicula> findAll() {
        try {
            return em.createNamedQuery("Pelicula.findAll", Pelicula.class).getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al obtener todas las películas", e);
            return List.of();
        }
    }

    /**
     * Busca las películas por nombre.
     *
     * @param nombre Nombre de la película a buscar
     * @return Lista de películas que coinciden con el nombre
     */
    public List<Pelicula> findByName(String nombre) {
        try {
            return em.createNamedQuery("Pelicula.findByName", Pelicula.class)
                    .setParameter("nombre", "%" + nombre + "%")
                    .getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al buscar películas por nombre", e);
            return List.of();
        }
    }

    /**
     * Cuenta todas las películas.
     *
     * @return El número total de películas
     */
    public int countAll() {
        try {
            Number result = (Number) em.createNamedQuery("Pelicula.countAll").getSingleResult();
            return result != null ? result.intValue() : 0;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al contar las películas", ex);
            return 0;
        }
    }

    /**
     * Busca las películas por sinopsis.
     *
     * @param sinopsis Sinopsis de la película a buscar
     * @return Lista de películas que contienen la sinopsis especificada
     */
    public List<Pelicula> findBySinopsis(String sinopsis) {
        try {
            return em.createNamedQuery("Pelicula.findBySinopsis", Pelicula.class)
                    .setParameter("sinopsis", "%" + sinopsis + "%")
                    .getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al buscar películas por sinopsis", e);
            return List.of();
        }
    }
}
