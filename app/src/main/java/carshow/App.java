package carshow;

import carshow.database.DbConnection;

public class App {
    public static void main(String[] args) throws Exception {
        // Set logging level for OrmLite
        com.j256.ormlite.logger.Logger.setGlobalLogLevel(com.j256.ormlite.logger.Level.ERROR);

        // Create DataBase connection
        DbConnection dbConnection = new DbConnection();

        // Close DataBase connection
        dbConnection.closeDb();
    }
}
