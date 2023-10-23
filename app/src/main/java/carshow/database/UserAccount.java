package carshow.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.mindrot.jbcrypt.BCrypt;

class UserAccount {
    // Table fields names
    public static final String USER_NAME_FIELD = "user_name";
    public static final String PASSWORD_HASH_FIELD = "password_hash";
    public static final String PASSWORD_SALT_FIELD = "password_salt";

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = USER_NAME_FIELD, canBeNull = false, unique = true)
    private String userName;

    @DatabaseField(columnName = PASSWORD_HASH_FIELD, canBeNull = false)
    private String password_hash;

    @DatabaseField(columnName = PASSWORD_SALT_FIELD, canBeNull = false)
    private String password_salt;

    UserAccount(String userName, String password) {
        this.setUserName(userName);
        this.setPassword(password);
    }

    public void setUserName(String userName) {
        // TODO: Apply restrictions on user names.
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password_salt = BCrypt.gensalt();

        this.password_hash = BCrypt.hashpw(password, this.password_salt);
    }

    public int getId() {
        return this.id;
    }

    public String getUserName() {
        return this.userName;
    }

    public boolean validatePassword(String password) {
        return BCrypt.checkpw(password, this.password_hash);
    }
}

@DatabaseTable(tableName = "admins")
class AdminAccount extends UserAccount {
    // TODO: Define admin specific parameters.

    AdminAccount() {
        super("admin", "password");
    }

    AdminAccount(String userName, String password) {
        super(userName, password);
    }

    // TODO: Implement admin specific metionds.
}

@DatabaseTable(tableName = "sales_men")
class SalesManAccount extends UserAccount {
    // TODO: Define sales man specific parameters.

    SalesManAccount() {
        super("admin", "password");
    }

    SalesManAccount(String userName, String password) {
        super(userName, password);
    }

    // TODO: Implement sales man specific metionds.
}

@DatabaseTable(tableName = "costomers")
class CostomerAccount extends UserAccount {
    // TODO: Define user specific parameters.

    CostomerAccount() {
        super("costomer", "password");
    }

    CostomerAccount(String userName, String password) {
        super(userName, password);
    }

    // TODO: Implemente user specific metionds.
}
