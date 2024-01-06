package jwei26.repository;

import jwei26.model.User;
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
public class UserDao implements IUserDao {
    private static final Logger logger = LoggerFactory.getLogger(HibernateUtil.class);

    @Override
    public void createUser(User user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (HibernateException exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error to save {}", user, exception);
            throw exception;
        }
    }

    @Override
    public User getUserById(Long userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(User.class, userId);
        } catch (HibernateException exception) {
            logger.error("Error when getting user with id {}", userId, exception);
            throw exception;
        }
    }

    @Override
    public List<User> getUsers() {
        List<User> users;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "from User";
            Query<User> query = session.createQuery(hql);
            users = query.list();
            session.close();
            return users;
        } catch (HibernateException exception) {
            logger.error("GetDepartments Error when Open session or Close session error", exception);
            throw exception;
        }
    }

    @Override
    public void updateUser(User user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
        } catch (HibernateException exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error to update {}", user, exception);
            throw exception;
        }
    }

    @Override
    public void deleteUser(Long userId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, userId);
            if (user != null) {
                session.delete(user);
            }
            transaction.commit();
        } catch (HibernateException exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error to delete user with id {}", userId, exception);
            throw exception;
        }
    }
}

