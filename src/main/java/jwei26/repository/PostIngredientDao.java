package jwei26.repository;

import jwei26.model.PostIngredient;
import jwei26.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class PostIngredientDao implements IPostIngredientDao {
    private static final Logger logger = LoggerFactory.getLogger(HibernateUtil.class);

    @Override
    public void addPostIngredient(PostIngredient postIngredient) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(postIngredient);
            transaction.commit();
        } catch (HibernateException exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error to add PostIngredient {}", postIngredient, exception);
            throw exception;
        }
    }

    @Override
    public void deletePostIngredient(Long postId, Long ingredientId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String hql = "DELETE FROM PostIngredient WHERE postId = :postId AND ingredientId = :ingredientId";
            session.createQuery(hql)
                    .setParameter("postId", postId)
                    .setParameter("ingredientId", ingredientId)
                    .executeUpdate();
            transaction.commit();
        } catch (HibernateException exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error to delete PostIngredient with postId {} and ingredientId {}", postId, ingredientId, exception);
            throw exception;
        }
    }
}

