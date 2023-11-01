package org.tonality.util;

import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    @Getter
    private static SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.setProperty("hibernate.connection.url", System.getenv("DATABASE_URL"));
            configuration.setProperty("hibernate.connection.username", System.getenv("DATABASE_USERNAME"));
            configuration.setProperty("hibernate.connection.password", System.getenv("DATABASE_PASSWORD"));
            configuration.configure("hibernate.cfg.xml");

            sessionFactory = configuration.buildSessionFactory();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
