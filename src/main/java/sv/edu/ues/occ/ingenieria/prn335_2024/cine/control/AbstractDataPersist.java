package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoSala;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractDataPersistence<T> {

    public abstract EntityManager getEntityManager();

    final Class tipoDeDato;

    public AbstractDataPersistence(Class t) {
        this.tipoDeDato = t;
    }

    /**
     * BUsca un regiistro en el repositorio por su identificador unico
     *
     * @param id Identificador unico buscado
     * @return NUlo si no se encuentra o el registro encontrado
     * @throws IllegalArgumentException si el id es nulo
     * @throws IllegalStateException    si no se puede acceder al repositorio.
     */
    public T findById(final Object id) throws IllegalArgumentException, IllegalStateException {
        EntityManager em = null;
        if (id == null) {
            throw new IllegalStateException("parametro no valido");
        }
        try {
            em = getEntityManager();
            if (em == null) {
                throw new IllegalStateException("error al aceder al repositorio");
            }
        } catch (IllegalStateException e) {
            throw new IllegalStateException("error al aceder al repositorio", e);

        }
        return (T) em.find(this.tipoDeDato, id);
    }

    public List<T> findAll() throws IllegalStateException {
        EntityManager em = null;
        List<T> resultados = null;
        try {
            em = getEntityManager();
            if (em == null) {
                throw new IllegalStateException("error al aceder al repositorio");
            }
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery cq = cb.createQuery(tipoDeDato);
            Root<T> r = cq.from(tipoDeDato);
            CriteriaQuery<T> cq2 = cq.select(r);
            TypedQuery<T> q = em.createQuery(cq2);
            resultados = q.getResultList();
            return resultados;
        } catch (java.lang.IllegalStateException e) {
            //error de entity manager
            throw new IllegalStateException("error al aceder al repositorio", e);
        }
    }

    public List<T> findRange(int first, int max) throws IllegalStateException, IllegalArgumentException {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery q = cb.createQuery(tipoDeDato);
        Root<T> raiz = q.from(tipoDeDato);
        CriteriaQuery cq = q.select(raiz);
        TypedQuery query = getEntityManager().createQuery(cq);
        query.setFirstResult(first);
        query.setMaxResults(max);
        EntityManager em = null;
        return query.getResultList();
    }


    /**
     * almacena un registro en le repositorio
     *
     * @param entity registro en el repostorio
     * @throws IllegalStateException    lanza error si no puede acceder al repositorio
     * @throws IllegalArgumentException lanza error si el registr es nulo
     * @throws
     */
    public void create(T entity) throws IllegalStateException, IllegalArgumentException {
        EntityManager em = null;
        if (entity == null) {
            throw new IllegalArgumentException("El registro no puede ser nulo");
        }
        try {
            em = getEntityManager();
            if (em == null) {
                throw new IllegalStateException("Error al acceder al repositorio");
            }
            em.persist(entity);
        } catch (Exception e) {
            throw new IllegalStateException("Error al acceder al repositorio jj", e);
        }
    }


    public void modify(T registro) {
        if (registro == null) {
            throw new IllegalArgumentException("El registro no puede ser nulo");
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            if (em != null) {
                em.merge(registro);
            }
        } catch (IllegalStateException e) {
            throw new IllegalStateException("Error al acceder al repositorio", e);
        }
    }

    public int count() {
        EntityManager em = null;
        try {
            em = getEntityManager();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        if (em != null) {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery cq = cb.createQuery(this.tipoDeDato);
            Root<T> raiz = cq.from(this.tipoDeDato);

            CriteriaQuery crq = cq.select(cb.count(raiz));

            TypedQuery q = em.createQuery(crq);

            return Integer.parseInt(q.getSingleResult().toString());
        }
        return 0;
    }

    public void delete(T registro) {
        if (registro != null) {
            EntityManager em = null;
            try {
                em = getEntityManager();
                if (em != null) {
                    CriteriaBuilder cb = em.getCriteriaBuilder();
                    CriteriaDelete<T> dq = cb.createCriteriaDelete(this.tipoDeDato);
                    Root<T> raiz = dq.from(this.tipoDeDato);
                    dq.where(cb.equal(raiz, registro));
                    em.createQuery(dq).executeUpdate();
                    return;
                }
            } catch (Exception e) {
                System.out.println("error: " + e.getMessage());
            }


            throw new IllegalStateException();
        }
        throw new IllegalArgumentException();
    }


    public abstract boolean imprimirCarnet();

    public TipoSala update(TipoSala modificado) {
        return modificado;
    }
}

