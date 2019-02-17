package rocks.zipcode.atm.bank;

/**
 * @author ZipCodeWilmington
 */
public class PremiumAccount extends Account {

    private static final int OVERDRAFT_LIMIT = 100;

    public PremiumAccount(AccountData accountData) {
        super(accountData);
    }

    /**
     * canWithdraw is a method that checks if the amount can be withdrawn from the account
     * @param amount - a Double that is the amount to be compared to the account balance
     * @return a boolean that is true if the amount to be withdrawn is less than or equal to the
     * account's balance plus the overdraft limit
     */
    @Override
    protected boolean canWithdraw(Double amount) {
        return getBalance() + OVERDRAFT_LIMIT >= amount;
    }
}
