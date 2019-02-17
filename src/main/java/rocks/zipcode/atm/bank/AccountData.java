package rocks.zipcode.atm.bank;

/**
 * @author ZipCodeWilmington
 */
public final class AccountData {

    private final int id;
    private final String name;
    private final String email;
    private final Double balance;
    private final String pin;

    AccountData(int id, String name, String email, Double balance, String pin) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.balance = balance;
        this.pin = pin;
    }

    /**
     * getId is a getter method for the AccountData's id
     * @return an int that is the AccountData's id
     */
    public int getId() {
        return id;
    }

    /**
     * getName is a getter method for the AccountData's name
     * @return a String that is the AccountData's name
     */
    public String getName() {
        return name;
    }

    /**
     * getEmail is a getter method for the AccountData's email
     * @return a String that is the AccountData's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * getBalance is a getter method for the AccountData's balance
     * @return a Double that is the AccountData's balance
     */
    public Double getBalance() {
        return balance;
    }

    /**
     * getPin is a getter method for the AccountData's PIN
     * @return a String that is the AccountData's PIN
     */
    public String getPin() {
        return pin;
    }

    /**
     * toString is a method that returns a string representation of the AccountData
     * @return a String of the AccountData
     */
    @Override
    public String toString() {
        String balanceString = String.format("%1$,.2f", balance);
        return "Account Number: " + id + '\n' +
                "Name: " + name + '\n' +
                "Email: " + email + '\n' +
                "Balance: $" + balanceString;
    }
}
