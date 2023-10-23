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
    private Dao<SalesManAccount, Integer> salesManAccountDao;
    private Dao<CostomerAccount, Integer> costomerAccountDao;
    private Dao<ProductTypesWrapper, Integer> productDao;
    private Dao<Car, Integer> carDao;
    private Dao<Carvan, Integer> carvanDao;
    private Dao<Bus, Integer> busDao;
    private Dao<Sale, Integer> saleDao;

    public DbConnection() throws Exception {
        this(DATABASE_URL);
    }

    public DbConnection(String dbUrl) throws Exception {
        this.connectionSource = new JdbcConnectionSource(dbUrl);

        this.setupDb(connectionSource);
    }

    private void setupDb(ConnectionSource connectionSource) throws Exception {
        this.adminAccountDao = DaoManager.createDao(connectionSource, AdminAccount.class);
        this.salesManAccountDao = DaoManager.createDao(connectionSource, SalesManAccount.class);
        this.costomerAccountDao = DaoManager.createDao(connectionSource, CostomerAccount.class);
        this.productDao = DaoManager.createDao(connectionSource, ProductTypesWrapper.class);
        this.carDao = DaoManager.createDao(connectionSource, Car.class);
        this.carvanDao = DaoManager.createDao(connectionSource, Carvan.class);
        this.busDao = DaoManager.createDao(connectionSource, Bus.class);
        this.saleDao = DaoManager.createDao(connectionSource, Sale.class);

        // Create tables
        TableUtils.createTableIfNotExists(connectionSource, AdminAccount.class);
        TableUtils.createTableIfNotExists(connectionSource, SalesManAccount.class);
        TableUtils.createTableIfNotExists(connectionSource, CostomerAccount.class);
        TableUtils.createTableIfNotExists(connectionSource, ProductTypesWrapper.class);
        TableUtils.createTableIfNotExists(connectionSource, Car.class);
        TableUtils.createTableIfNotExists(connectionSource, Carvan.class);
        TableUtils.createTableIfNotExists(connectionSource, Bus.class);
        TableUtils.createTableIfNotExists(connectionSource, Sale.class);
    }

    public void closeDb() throws Exception {
        if (this.connectionSource != null) {
            this.connectionSource.close();
        }
    }

    // TODO: Implement dababase methods.
}
