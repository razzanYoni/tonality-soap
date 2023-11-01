package org.tonality.db;

import org.hibernate.SessionFactory;
import org.tonality.util.HibernateUtil;

import static java.lang.System.exit;

public class Migration {
    public static void main(String[] args) {
        System.out.println("Migrating Data...");

        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

            System.out.println(sessionFactory);
            System.out.println("Migration Complete!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            exit(1);
        }
    }
}
