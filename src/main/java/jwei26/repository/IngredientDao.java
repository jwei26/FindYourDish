package jwei26.repository;

import jwei26.model.Ingredient;
import jwei26.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class IngredientDao implements IIngredientDao {
    private static final Logger logger = LoggerFactory.getLogger(HibernateUtil.class);

    @Override
    public void createIngredient(Ingredient ingredient) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(ingredient);
            transaction.commit();
        } catch (HibernateException exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error to save {}", ingredient, exception);
            throw exception;
        }
    }

    @Override
    public Ingredient getIngredientById(Long ingredientId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Ingredient.class, ingredientId);
        } catch (HibernateException exception) {
            logger.error("Error when getting ingredient with id {}", ingredientId, exception);
            throw exception;
        }
    }

    @Override
    public List<Ingredient> getAllIngredients() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Ingredient";
            Query<Ingredient> query = session.createQuery(hql, Ingredient.class);
            return query.list();
        } catch (HibernateException exception) {
            logger.error("Error when getting all ingredients", exception);
            throw exception;
        }
    }

    @Override
    public void updateIngredient(Ingredient ingredient) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(ingredient);
            transaction.commit();
        } catch (HibernateException exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error to update {}", ingredient, exception);
            throw exception;
        }
    }

    @Override
    public void deleteIngredient(Long ingredientId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Ingredient ingredient = session.get(Ingredient.class, ingredientId);
            if (ingredient != null) {
                session.delete(ingredient);
            }
            transaction.commit();
        } catch (HibernateException exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error to delete ingredient with id {}", ingredientId, exception);
            throw exception;
        }
    }
}

