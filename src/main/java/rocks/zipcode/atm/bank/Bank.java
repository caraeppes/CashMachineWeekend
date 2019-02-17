package rocks.zipcode.atm.bank;

import rocks.zipcode.atm.ActionResult;
import rocks.zipcode.atm.CashMachine;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ZipCodeWilmington
 */
public class Bank {

    private Map<Integer, Account> accounts = new HashMap<>();

    /**
     * Bank is a constructor for a Bank object that adds some initial Accounts to the account Map
     */
    public Bank() {
        accounts.put(545353774, new BasicAccount(new AccountData(
                545353774, "Cara Eppes", "bigD0ggggy@gmail.com", 5000000000.0, "1234"
        )));

        accounts.put(246801357, new PremiumAccount(new AccountData(
                246801357, "Sean Rowan", "seanyb0ii@yahoo.com", 200.0, "1234"
        )));
        accounts.put(836592742, new BasicAccount(new AccountData(
                836592742, "Zaina Cruz-King", "Zmoney$$$@gmail.com", 100000.0, "1234"
        )));

        accounts.put(947252432, new PremiumAccount(new AccountData(
                947252432, "Ashley Smith", "igottalottacASH@gmail.com", 0.0, "1234"
        )));
        accounts.put(683294753, new BasicAccount(new AccountData(
                683294753, "Michael Krohn", "mimis4lyfe@gmail.com", 20000.0, "1234"
        )));

        accounts.put(174493326, new PremiumAccount(new AccountData(
                174493326, "Brian Wong", "BriBriDaFlyGuy@gmail.com", 10000000.0, "1234"
        )));
        accounts.put(632819323, new BasicAccount(new AccountData(
                632819323, "Jim Coates", "pEaCeLuVnBeArDzZz@aol.com", 3434343.43, "1234"
        )));
        accounts.put(295464425, new PremiumAccount(new AccountData(
                295464425, "Charles Wilmer", "GnarShr3dderDude@comcast.net", 999.99, "1234"
        )));
    }


    /**
     * addAccount is a method that creates a new Account and adds it to the accounts Map.
     * @param id - an int that is the Account id
     * @param name - a String that is the name of the account holder
     * @param email - an String that is the email of the account holder
     * @param balance - a Double that is the account balance
     * @param accountType - a String that is either "basic" or "premium" indicating the account type
     * @param pin - a String that is the account PIN
     * @return a new ActionResult with data set to the new account's data
     */
    public ActionResult<AccountData> addAccount (int id, String name, String email, Double balance, String accountType, String pin) {
        if (accountType.equals("basic")){
            accounts.put(id, new BasicAccount(new AccountData(
                    id, name, email, balance, pin)));
        }
        if (accountType.equals("premium")) {
            accounts.put(id, new PremiumAccount(new AccountData(
                    id, name, email, balance, pin)));
        }

        Account newAccount = accounts.get(id);

        return ActionResult.success(newAccount.getAccountData());

    }


    /**
     * getAccountById is a method that gets an account from its id and returns a successful ActionResult if the account is found
     * and a failing ActionResult if the account is not found
     * @param id - an int that is the id being used to find an account
     * @return an ActionResult with the accountData if an account is found and an error message if the account is not found
     */
    public ActionResult<AccountData> getAccountById(int id) {
        Account account = accounts.get(id);
        if (account != null) {
            return ActionResult.success(account.getAccountData());
        } else {
            return ActionResult.fail("Invalid Account Number");
        }
    }

    /**
     * checkPin is a method that checks if the pin argument is the same as the account's pin
     * @param id - an int that is the id of the Account to be accessed
     * @param pin - a String that is the pin to be compared to the account's pin
     * @return an ActionResult that is the accountData if the pin is the same and an error message if it is not
     */
    public ActionResult<AccountData> checkPin(int id, String pin){
        Account account = accounts.get(id);
        if (account.getAccountData().getPin().equals(pin)){
            return ActionResult.success(account.getAccountData());
        }
        else {
            return  ActionResult.fail("Invalid PIN");
        }
    }

    /**
     * resetPin is a method that changes an account's PIN data
     * @param id - an int that is the id of the account to be changed
     * @param pin - a String that is the new PIN for the account
     * @return an ActionResult that is the new AccountData
     */
    public ActionResult<AccountData> resetPin(int id, String pin){
        Account account = accounts.get(id);
        AccountData newAccountData = new AccountData(id, account.getAccountData().getName(), account.getAccountData().getEmail(), account.getBalance(), pin);
        account.setAccountData(newAccountData);
        return ActionResult.success(newAccountData);
    }

    /**
     * deposit is a method that adds money to an account
     * @param accountData - AccountData that is used to access the account
     * @param amount - a Double that is the amount to be deposited
     * @return an ActionResult with the new AccountData
     */
    public ActionResult<AccountData> deposit(AccountData accountData, Double amount) {
        Account account = accounts.get(accountData.getId());
        account.deposit(amount);

        return ActionResult.success(account.getAccountData());
    }

    /**
     * withdraw is a method that checks if the amount can be withdrawn from the account.
     * @param accountData - the AccountData that is being withdrawn from
     * @param amount - a Double that is the amount to be withdrawn
     * @return an ActionResult with the accountData is the amount can be withdrawn and an error message if it cannot
     */
    public ActionResult<AccountData> withdraw(AccountData accountData, Double amount) {
        Account account = accounts.get(accountData.getId());
        boolean ok = account.withdraw(amount);

        if (ok) {
            return ActionResult.success(account.getAccountData());
        } else {
            String amountString = String.format("%1$,.2f", amount);
            String balanceString = String.format("%1$,.2f", account.getBalance());
            return ActionResult.fail("Withdraw failed: $" + amountString + ". Account has: $" + balanceString + ".\n");
        }
    }


    /**
     * getAccounts is a getter method got a bank's Map of Accounts
     * @return the bank's Map of accounts
     */
    public Map<Integer, Account> getAccounts(){
        return this.accounts;
    }
}
