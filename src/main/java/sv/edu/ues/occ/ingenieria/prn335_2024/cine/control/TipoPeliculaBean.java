package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.PeliculaCaracteristica;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoPelicula;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Stateless
@LocalBean
public class TipoPeliculaBean extends AbstractDataPersist<TipoPelicula> implements Serializable {

    @PersistenceContext(unitName = "cinePU")
    private EntityManager em;

    public TipoPeliculaBean() {
        super(TipoPelicula.class);
    }

    @Override
    public void create(TipoPelicula registro) throws IllegalStateException, IllegalArgumentException {
        if (registro == null) {
            throw new IllegalArgumentException("El registro no puede ser nulo");
        }
        super.create(registro);
    }

    public TipoPelicula findById(Integer idTipoPelicula) {
        if (idTipoPelicula == null || idTipoPelicula <= 0) {
            throw new IllegalArgumentException("ID de tipo de película no válido");
        }
        return em.find(TipoPelicula.class, idTipoPelicula);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<TipoPelicula> findRange(int first, int max) {
        return em.createQuery("SELECT t FROM TipoPelicula t", TipoPelicula.class)
                .setFirstResult(first)
                .setMaxResults(max)
                .getResultList();
    }

    public List<TipoPelicula> findAll() {
        return getEntityManager().createNamedQuery("TipoPelicula.findAll", TipoPelicula.class).getResultList();
    }

    public List<TipoPelicula> findByName(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacío");
        }
        return getEntityManager()
                .createNamedQuery("TipoPelicula.findByNombre", TipoPelicula.class) // Query corregida
                .setParameter("nombre", "%" + nombre + "%")
                .getResultList();
    }

    public Long countActive() {
        return getEntityManager()
                .createNamedQuery("TipoPelicula.findByActivo", Long.class)
                .getSingleResult();
    }

    public List<TipoPelicula> findByExpresionRegular(String expresionRegular) {
        if (expresionRegular == null || expresionRegular.isEmpty()) {
            throw new IllegalArgumentException("La expresión regular no puede ser nula o vacía");
        }
        return getEntityManager()
                .createNamedQuery("TipoPelicula.findByExpresionRegular", TipoPelicula.class)
                .setParameter("expresionRegular", expresionRegular)
                .getResultList();
    }

    public List<PeliculaCaracteristica> getCaracteristicasPorTipo(Integer idTipoPelicula) {
        if (idTipoPelicula == null) {
            throw new IllegalArgumentException("ID no puede ser nulo");
        }
        TipoPelicula tipoPelicula = findById(idTipoPelicula);
        return tipoPelicula != null ? new ArrayList<>(tipoPelicula.getPeliculaCaracteristicas()) : null;
    }
}
