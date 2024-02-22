package jwei26.repository;

import jwei26.model.Image;

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
public class ImageDao implements IImageDao{
    private static final Logger logger = LoggerFactory.getLogger(HibernateUtil.class);

    @Override
    public void createImage(Image image) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(image);
            transaction.commit();
        } catch (HibernateException exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error to save {}", image, exception);
            throw exception;
        }
    }

    @Override
    public Image getImageById(Long imageId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Image.class, imageId);
        } catch (HibernateException exception) {
            logger.error("Error when getting image with id {}", imageId, exception);
            throw exception;
        }
    }

    @Override
    public void updateImage(Image image) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(image);
            transaction.commit();
        } catch (HibernateException exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error to update {}", image, exception);
            throw exception;
        }
    }

    @Override
    public void deleteImage(Long imageId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Image image = session.get(Image.class, imageId);
            if (image != null) {
                session.delete(image);
            }
            transaction.commit();
        } catch (HibernateException exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error to delete image with id {}", imageId, exception);
            throw exception;
        }
    }

    @Override
    public List<Image> getImagesByPostId(Long postId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Image WHERE post.id = :postId";
            Query<Image> query = session.createQuery(hql, Image.class);
            query.setParameter("postId", postId);
            return query.list();
        } catch (HibernateException exception) {
            logger.error("Error to get image with post id {}", postId, exception);
            throw exception;
        }
    }

    @Override
    public void deleteImagesByPostId(Long postId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createQuery("DELETE FROM Image WHERE post.id = :postId");
            query.setParameter("postId", postId);
            query.executeUpdate();
            transaction.commit();
        } catch (HibernateException exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error to delete image with post id {}", postId, exception);
            throw exception;
        }
    }
}
