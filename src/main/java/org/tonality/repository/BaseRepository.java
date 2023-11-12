package org.tonality.repository;

import org.hibernate.SessionFactory;
import org.tonality.model.Subscription;
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
    public T add(T entity) {
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            EntityManager entityManager = sessionFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(entity);
            entityManager.getTransaction().commit();
            entityManager.close();
            return entity;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public T update(T entity) {
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            EntityManager entityManager = sessionFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(entity);
            entityManager.getTransaction().commit();
            entityManager.close();
            return entity;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // get by id
    public  <V> T getById(V id) {
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
    public List<T> search(Map<String, Object> andConditions, Map<String, Object> orConditions, String searchInput, List<String> searchField, String orderBy, int page, int size) {
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            EntityManager entityManager = sessionFactory.createEntityManager();
            entityManager.getTransaction().begin();
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(this.getEntityClass());
            Root<T> root = criteriaQuery.from(this.getEntityClass());
            List<Predicate> predicates = new java.util.ArrayList<>();

            // and conditions
            Predicate andPredicate = criteriaBuilder.conjunction();
            for (Map.Entry<String, Object> entry : andConditions.entrySet()) {
                andPredicate = criteriaBuilder.and(andPredicate, criteriaBuilder.equal(root.get(entry.getKey()), entry.getValue()));
            }
            if (andPredicate.getExpressions().size() > 0) {
                predicates.add(andPredicate);
            }

            // or conditions
            Predicate orPredicate = criteriaBuilder.disjunction();
            for (Map.Entry<String, Object> entry : orConditions.entrySet()) {
                orPredicate = criteriaBuilder.or(orPredicate, criteriaBuilder.equal(root.get(entry.getKey()), entry.getValue()));
            }
            if (orPredicate.getExpressions().size() > 0) {
                predicates.add(orPredicate);
            }

            // order by
            criteriaQuery.orderBy(criteriaBuilder.asc(root.get(orderBy)));

            // pagination
            int offset = (page - 1) * size;

            // like query
            Predicate likePredicate = criteriaBuilder.disjunction();
            if (!searchInput.isEmpty()) {
                for (String field : searchField) {
                    likePredicate = criteriaBuilder.or(likePredicate, criteriaBuilder.like(root.get(field), "%" + searchInput + "%"));
                }
            }
            if (likePredicate.getExpressions().size() > 0) {
                predicates.add(likePredicate);
            }

            if (predicates.size() > 0) {
                criteriaQuery.where(predicates.toArray(new Predicate[0]));
            } else {
                criteriaQuery.where();
            }

            List<T> entities = entityManager.createQuery(criteriaQuery).setFirstResult(offset).setMaxResults(size).getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return entities;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // delete by id
    public  <V> boolean deleteById(V id) {
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
    public boolean delete(T entity) {
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
    public boolean delete(Map<String, Object> conditions) {
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


