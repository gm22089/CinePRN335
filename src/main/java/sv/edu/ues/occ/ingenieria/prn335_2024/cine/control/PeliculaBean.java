package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Pelicula;

import java.io.Serializable;
import java.util.Collections;
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
        if (registro == null) {
            logger.log(Level.SEVERE, "Error al crear la película: El registro es nulo");
            throw new IllegalArgumentException("El registro no puede ser nulo.");
        }
        try {
            super.create(registro);
            logger.log(Level.INFO, "Película creada con éxito: {0}", registro);
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
     * @param max   Número máximo de registros
     * @return Lista de películas en el rango indicado o una lista vacía en caso de error
     */
    public List<Pelicula> findRange(int first, int max) {
        if (first < 0 || max <= 0) {
            logger.log(Level.WARNING, "Parámetros de rango inválidos: first={0}, max={1}", new Object[]{first, max});
            return Collections.emptyList();
        }
        try {
            return em.createQuery("SELECT p FROM Pelicula p", Pelicula.class)
                    .setFirstResult(first)
                    .setMaxResults(max)
                    .getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al obtener el rango de películas", e);
            return Collections.emptyList();
        }
    }

    /**
     * Encuentra una película por su ID.
     *
     * @param idPelicula El ID de la película
     * @return La película encontrada o null si no existe
     * @throws IllegalArgumentException Si el ID es inválido
     */
    public Pelicula findById(final Integer idPelicula) {
        if (idPelicula == null || idPelicula <= 0) {
            logger.log(Level.WARNING, "ID de película no válido: {0}", idPelicula);
            throw new IllegalArgumentException("ID de película no válido");
        }
        try {
            return em.find(Pelicula.class, idPelicula);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al buscar la película por ID", e);
            return null;
        }
    }

    /**
     * Obtiene todas las películas de la base de datos.
     *
     * @return Lista de todas las películas o una lista vacía en caso de error
     */
    public List<Pelicula> findAll() {
        try {
            return em.createNamedQuery("Pelicula.findAll", Pelicula.class).getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al obtener todas las películas", e);
            return Collections.emptyList();
        }
    }

    /**
     * Busca las películas por nombre.
     *
     * @param nombre Nombre de la película a buscar
     * @return Lista de películas que coinciden con el nombre o una lista vacía si no hay coincidencias
     */
    public List<Pelicula> findByName(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            logger.log(Level.WARNING, "Nombre de búsqueda inválido o vacío");
            return Collections.emptyList();
        }
        try {
            return em.createNamedQuery("Pelicula.findByName", Pelicula.class)
                    .setParameter("nombre", "%" + nombre.trim() + "%")
                    .getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al buscar películas por nombre", e);
            return Collections.emptyList();
        }
    }

    /**
     * Cuenta todas las películas en la base de datos.
     *
     * @return El número total de películas o 0 en caso de error
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
     * @return Lista de películas que contienen la sinopsis especificada o una lista vacía si no hay coincidencias
     */
    public List<Pelicula> findBySinopsis(String sinopsis) {
        if (sinopsis == null || sinopsis.trim().isEmpty()) {
            logger.log(Level.WARNING, "Sinopsis de búsqueda inválida o vacía");
            return Collections.emptyList();
        }
        try {
            return em.createNamedQuery("Pelicula.findBySinopsis", Pelicula.class)
                    .setParameter("sinopsis", "%" + sinopsis.trim() + "%")
                    .getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al buscar películas por sinopsis", e);
            return Collections.emptyList();
        }
    }
}
