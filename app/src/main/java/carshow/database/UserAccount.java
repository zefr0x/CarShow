package carshow.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.mindrot.jbcrypt.BCrypt;

class UserAccount {
    // Table fields names
    public static final String USER_NAME_FIELD = "user_name";
    public static final String PASSWORD_HASH_FIELD = "password_hash";
    public static final String FIRST_NAME_FIELD = "first_name";
    public static final String LAST_NAME_FIELD = "last_name";

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = USER_NAME_FIELD, canBeNull = false, unique = true)
    private String userName;

    @DatabaseField(columnName = PASSWORD_HASH_FIELD, canBeNull = false)
    private String passwordHash;

    @DatabaseField(columnName = FIRST_NAME_FIELD, canBeNull = false)
    private String firstName;

    @DatabaseField(columnName = LAST_NAME_FIELD, canBeNull = false)
    private String lastName;

    UserAccount(String userName, String password, String firstName, String lastName) {
        this.setUserName(userName);
        this.setPassword(password);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    private void setUserName(String userName) {
        // TODO: Apply restrictions on user names.
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public int getId() {
        return this.id;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    public boolean validatePassword(String password) {
        return BCrypt.checkpw(password, this.passwordHash);
    }
}

@DatabaseTable(tableName = "admins")
class AdminAccount extends UserAccount {
    public static final String BRANCH_FIELD = "branch";
    public static final String SALARY_FIELD = "salary";
    public static final String OFFICE_NUMBER_FIELD = "office_number";

    @DatabaseField(columnName = BRANCH_FIELD, canBeNull = false)
    private String branch;

    @DatabaseField(columnName = SALARY_FIELD, canBeNull = false)
    private double salary;

    @DatabaseField(columnName = OFFICE_NUMBER_FIELD, canBeNull = false)
    private int officeNumber;

    AdminAccount() {
        this("admin", "password", "", "", "", 0.0, 0);
    }

    AdminAccount(String userName, String password, String firstName, String lastName, String branch, double salary,
            int officeNumber) {
        super(userName, password, firstName, lastName);

        setBranch(branch);
        setSalary(salary);
        setOfficeNumber(officeNumber);

    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setOfficeNumber(int officeNumber) {
        this.officeNumber = officeNumber;
    }

    public String getBranch() {
        return this.branch;
    }

    public double getSalary() {
        return this.salary;
    }

    public int getOfficeNumber() {
        return this.officeNumber;
    }
}

@DatabaseTable(tableName = "sales_men")
class SalesManAccount extends UserAccount {
    public static final String BRANCH_FIELD = "branch";
    public static final String SALARY_FIELD = "salary";
    public static final String PRODUCT_TYPE_FIELD = "product_type";

    @DatabaseField(columnName = BRANCH_FIELD, canBeNull = false)
    private String branch;

    @DatabaseField(columnName = SALARY_FIELD, canBeNull = false)
    private double salary;

    @DatabaseField(columnName = PRODUCT_TYPE_FIELD, canBeNull = false)
    private ProductType productType;

    SalesManAccount() {
        this("admin", "password", "", "", "", 0.0, ProductType.Car);
    }

    SalesManAccount(String userName, String password, String firstName, String lastName, String branch, double salary,
            ProductType productType) {
        super(userName, password, firstName, lastName);
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public String getBranch() {
        return this.branch;
    }

    public double getSalary() {
        return this.salary;
    }

    public ProductType getProductType() {
        return this.productType;
    }
}

@DatabaseTable(tableName = "costomers")
class CostomerAccount extends UserAccount {
    public static final String PHONE_NUMBER_FIELD = "phone";
    public static final String EMAIL_ADDRESS_FIELD = "email";
    public static final String LOYALITY_POINTS_FIELD = "loyality_points";

    @DatabaseField(columnName = PHONE_NUMBER_FIELD, canBeNull = false)
    private String phoneNumber;

    @DatabaseField(columnName = EMAIL_ADDRESS_FIELD, canBeNull = false)
    private String emailAddress;

    @DatabaseField(columnName = LOYALITY_POINTS_FIELD, canBeNull = false)
    private int loyalityPoints;

    CostomerAccount() {
        this("costomer", "password", "", "", "", "");
    }

    CostomerAccount(String userName, String password, String firstName, String lastName, String phoneNumber,
            String emailAddress) {
        super(userName, password, firstName, lastName);

        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.loyalityPoints = 0;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public int getLoyalityPoints() {
        return loyalityPoints;
    }

    public void addLoyalityPoints(int points) {
        this.loyalityPoints += points;
    }
}
