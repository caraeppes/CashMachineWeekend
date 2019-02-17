package rocks.zipcode.atm.bank;

/**
 * @author ZipCodeWilmington
 */
public abstract class Account {

    private AccountData accountData;

    /**
     * Account is a constructor for an Account object
     * @param accountData - the accountData to be initialized in the Account
     */
    public Account(AccountData accountData) {
        this.accountData = accountData;
    }

    /**
     * getAccountData is a getter for an Account's accountData
     * @return the Account's accountData
     */
    public AccountData getAccountData() {
        return accountData;
    }

    /**
     * deposit is a method that update's the Account's balance with the amount deposited
     * @param amount - a Double that is the amount to be added to the balance
     */
    public void deposit(Double amount) {
        updateBalance(getBalance() + amount);
    }

    /**
     * withdraw is a method that checks if the Account can withdraw the amount.  If it can, it updates the accounts balance
     * @param amount - a Double that is the amount ot be withdrawn from the account
     * @return a boolean that is true if the amount can be withdrawn and false if it cannot
     */
    public boolean withdraw(Double amount) {
        if (canWithdraw(amount)) {
            updateBalance(getBalance() - amount);
            return true;
        } else {
            return false;
        }
    }

    /**
     * canWithdraw is a method that checks if the amount to be withdrawn is less than or equal to the account balance
     * @param amount - a Double that is the amount to be compared to the account balance
     * @return a boolean that is true if the balance is greater than or equal to the amount to be withdrawn
     */
    protected boolean canWithdraw(Double amount) {
        return getBalance() >= amount;
    }

    /**
     * getBalance is a getter for the account's balance
     * @return a Double that is the account's balance
     */
    public Double getBalance() {
        return accountData.getBalance();
    }

    /**
     * updateBalance is a method that changes the balance of the accountData
     * @param newBalance - a Double that is the new balance of the accountData
     */
    private void updateBalance(Double newBalance) {
        accountData = new AccountData(accountData.getId(), accountData.getName(), accountData.getEmail(),
                newBalance, accountData.getPin());
    }

    /**
     * setAccountData is a setter for an Account's accountData
     * @param accountData - the accountData to be assigned to the Account
     */
    public void setAccountData(AccountData accountData){
        this.accountData = accountData;
    }
}
