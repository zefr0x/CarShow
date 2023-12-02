package carshow;

import java.util.UUID;

import org.mindrot.jbcrypt.BCrypt;

class UserAccount {
    private String id;
    private String userName;
    private String passwordHash;
    private String firstName;
    private String lastName;

    UserAccount(String userName, String password, String firstName, String lastName) {
        this.setUserName(userName);
        this.setPassword(password);
        this.firstName = firstName;
        this.lastName = lastName;

        this.id = UUID.randomUUID().toString();
    }

    private void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public String getId() {
        return this.id;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    public boolean validatePassword(String password) {
        return BCrypt.checkpw(password, this.passwordHash);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " / " + "ID: " + this.id + ", UserName: " + this.userName + ", Name: "
                + getFullName();
    }
}

class AdminAccount extends UserAccount implements Searchable {
    private String branch;
    private double salary;
    private int officeNumber;

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

    @Override
    public String toString() {
        return super.toString() + ", Branch: " + this.branch + ", Salary: " + this.salary + ", OfficeNumber: "
                + this.officeNumber;
    }

    @Override
    public boolean passSearchTerm(String filterTerm) {
        if (this.getId().equals(filterTerm)
                || this.getUserName().toLowerCase().contains(filterTerm)
                || this.getFirstName().toLowerCase().contains(filterTerm)
                || this.getLastName().toLowerCase().contains(filterTerm)
                || this.branch.toLowerCase().contains(filterTerm)
                || Integer.toString(this.officeNumber).equals(filterTerm)) {
            return true;
        }
        return false;
    }
}

class SalesManAccount extends UserAccount implements Searchable {
    private String branch;
    private double salary;
    private ProductType productType;

    SalesManAccount(String userName, String password, String firstName, String lastName, String branch, double salary,
            ProductType productType) {
        super(userName, password, firstName, lastName);

        this.branch = branch;
        this.salary = salary;
        this.productType = productType;
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

    @Override
    public String toString() {
        return super.toString() + ", Branch: " + this.branch + ", Salary: " + this.salary + ", Related product type: "
                + this.productType.toString();
    }

    @Override
    public boolean passSearchTerm(String filterTerm) {
        if (this.getId().equals(filterTerm)
                || this.getUserName().toLowerCase().contains(filterTerm)
                || this.getFirstName().toLowerCase().contains(filterTerm)
                || this.getLastName().toLowerCase().contains(filterTerm)
                || this.branch.toLowerCase().contains(filterTerm)
                || productType.toString().toLowerCase().contains(filterTerm)) {
            return true;
        }
        return false;
    }
}

class CostomerAccount extends UserAccount implements Searchable {
    private String phoneNumber;
    private String emailAddress;
    private int loyalityPoints;

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

    @Override
    public String toString() {
        return super.toString() + ", PhoneNumber: " + this.phoneNumber + ", EmailAddress: " + this.emailAddress
                + ", LoyalityPoints: " + this.loyalityPoints;
    }

    @Override
    public boolean passSearchTerm(String filterTerm) {
        if (this.getId().equals(filterTerm)
                || this.getUserName().toLowerCase().contains(filterTerm)
                || this.getFirstName().toLowerCase().contains(filterTerm)
                || this.getLastName().toLowerCase().contains(filterTerm)
                || this.emailAddress.contains(filterTerm)) {
            return true;
        }
        return false;
    }
}
