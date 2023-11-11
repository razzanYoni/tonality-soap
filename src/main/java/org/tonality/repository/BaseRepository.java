package org.tonality.repository;

import oracle.ucp.proxy.annotation.Pre;
import org.hibernate.SessionFactory;
import org.tonality.util.HibernateUtil;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;

public abstract class BaseRepository<T> {
    protected abstract Class<T> getEntityClass();
    protected boolean add(T entity) {
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            EntityManager entityManager = sessionFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(entity);
            entityManager.getTransaction().commit();
            entityManager.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    protected boolean update(T entity) {
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            EntityManager entityManager = sessionFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(entity);
            entityManager.getTransaction().commit();
            entityManager.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // get by id
    protected <V> T getById(V id) {
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            EntityManager entityManager = sessionFactory.createEntityManager();
            entityManager.getTransaction().begin();
            T entity = entityManager.find(this.getEntityClass(), id);
            entityManager.getTransaction().commit();
            entityManager.close();
            return entity;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // get by conditions
    protected List<T> search(Map<String, Object>andConditions, Map<String, Object> orConditions) {
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            EntityManager entityManager = sessionFactory.createEntityManager();
            entityManager.getTransaction().begin();
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(this.getEntityClass());
            Root<T> root = criteriaQuery.from(this.getEntityClass());

            // and conditions
            Predicate andPredicate = criteriaBuilder.conjunction();
            for (Map.Entry<String, Object> entry : andConditions.entrySet()) {
                andPredicate = criteriaBuilder.and(andPredicate, criteriaBuilder.equal(root.get(entry.getKey()), entry.getValue()));
            }

            // or conditions
            Predicate orPredicate = criteriaBuilder.disjunction();
            for (Map.Entry<String, Object> entry : orConditions.entrySet()) {
                orPredicate = criteriaBuilder.or(orPredicate, criteriaBuilder.equal(root.get(entry.getKey()), entry.getValue()));
            }

            if (andPredicate.getExpressions().size() > 0 && orPredicate.getExpressions().size() > 0) {
                criteriaQuery.where(criteriaBuilder.and(andPredicate, orPredicate));
            } else if (andPredicate.getExpressions().size() > 0) {
                criteriaQuery.where(andPredicate);
            } else if (orPredicate.getExpressions().size() > 0) {
                criteriaQuery.where(orPredicate);
            }
            List<T> entities = entityManager.createQuery(criteriaQuery).getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return entities;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // delete by id
    protected <V> boolean deleteById(V id) {
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            EntityManager entityManager = sessionFactory.createEntityManager();
            entityManager.getTransaction().begin();
            T entity = entityManager.find(this.getEntityClass(), id);
            entityManager.remove(entity);
            entityManager.getTransaction().commit();
            entityManager.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // delete by entity
    protected boolean delete(T entity) {
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            EntityManager entityManager = sessionFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.remove(entity);
            entityManager.getTransaction().commit();
            entityManager.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // delete by conditions
    protected boolean delete(Map<String, Object> conditions) {
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            EntityManager entityManager = sessionFactory.createEntityManager();
            entityManager.getTransaction().begin();
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(this.getEntityClass());
            Root<T> root = criteriaQuery.from(this.getEntityClass());

            // and conditions
            Predicate predicate = criteriaBuilder.conjunction();
            for (Map.Entry<String, Object> entry : conditions.entrySet()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get(entry.getKey()), entry.getValue()));
            }

            if (predicate.getExpressions().size() > 0) {
                criteriaQuery.where(predicate);
            }
            List<T> entities = entityManager.createQuery(criteriaQuery).getResultList();
            for (T entity : entities) {
                entityManager.remove(entity);
            }
            entityManager.getTransaction().commit();
            entityManager.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}


