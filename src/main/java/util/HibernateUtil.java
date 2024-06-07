package util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();
//
    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            // Log the exception.
            System.err.println("Loi ko the tao SessionFactory");
            return null;
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
//
    public static void shutdown() {
        // Close caches and connection pools
        getSessionFactory().close();
    }
}