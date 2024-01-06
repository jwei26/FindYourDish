package jwei26.repository;

import jwei26.model.Post;
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
public class PostDao implements IPostDao {
    private static final Logger logger = LoggerFactory.getLogger(HibernateUtil.class);

    @Override
    public void createPost(Post post) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(post);
            transaction.commit();
        } catch (HibernateException exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error to save {}", post, exception);
            throw exception;
        }
    }

    @Override
    public Post getPostById(Long postId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Post.class, postId);
        } catch (HibernateException exception) {
            logger.error("Error when getting post with id {}", postId, exception);
            throw exception;
        }
    }

    @Override
    public List<Post> getAllPosts() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Post";
            Query<Post> query = session.createQuery(hql, Post.class);
            return query.list();
        } catch (HibernateException exception) {
            logger.error("Error when getting all posts", exception);
            throw exception;
        }
    }

    @Override
    public void updatePost(Post post) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(post);
            transaction.commit();
        } catch (HibernateException exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error to update {}", post, exception);
            throw exception;
        }
    }

    @Override
    public void deletePost(Long postId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Post post = session.get(Post.class, postId);
            if (post != null) {
                session.delete(post);
            }
            transaction.commit();
        } catch (HibernateException exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error to delete post with id {}", postId, exception);
            throw exception;
        }
    }
}

