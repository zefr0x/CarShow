package carshow.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DbConnection {
    private final static String DATABASE_URL = "jdbc:sqlite:car_show.sqlite3";
    private ConnectionSource connectionSource;

    private Dao<AdminAccount, Integer> adminAccountDao;
    private Dao<CostomerAccount, Integer> costomerAccountDao;
    private Dao<Car, Integer> carDao;
    private Dao<CarSale, Integer> carSaleDao;

    public DbConnection() throws Exception {
        this(DATABASE_URL);
    }

    public DbConnection(String dbUrl) throws Exception {
        this.connectionSource = new JdbcConnectionSource(dbUrl);

        this.setupDb(connectionSource);
    }

    private void setupDb(ConnectionSource connectionSource) throws Exception {
        this.adminAccountDao = DaoManager.createDao(connectionSource, AdminAccount.class);
        this.costomerAccountDao = DaoManager.createDao(connectionSource, CostomerAccount.class);
        this.carDao = DaoManager.createDao(connectionSource, Car.class);
        this.carSaleDao = DaoManager.createDao(connectionSource, CarSale.class);

        // Create tables
        TableUtils.createTableIfNotExists(connectionSource, AdminAccount.class);
        TableUtils.createTableIfNotExists(connectionSource, CostomerAccount.class);
        TableUtils.createTableIfNotExists(connectionSource, Car.class);
        TableUtils.createTableIfNotExists(connectionSource, CarSale.class);
    }

    public void closeDb() throws Exception {
        if (this.connectionSource != null) {
            this.connectionSource.close();
        }
    }

    // TODO: Implement dababase methods.
}
