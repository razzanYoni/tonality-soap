package org.tonality.repository;

import java.sql.SQLException;

public class Logging extends BaseRepository<org.tonality.model.Logging> {
    private static Logging instance = null;

    @Override
    protected Class<org.tonality.model.Logging> getEntityClass() {
        return org.tonality.model.Logging.class;
    }

    public static Logging getInstance() {
        if (instance == null) {
            instance = new Logging();
        }

        return instance;
    }

    public org.tonality.model.Logging create(String endpoint, String IP, String description) throws SQLException {
        try {
            org.tonality.model.Logging log = new org.tonality.model.Logging();
            log.setEndpoint(endpoint);
            log.setIP(IP);
            log.setDescription(description);
            if (org.tonality.repository.Logging.getInstance().add(log) == null) {
                throw new Exception("Failed to create log");
            }

            return log;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
