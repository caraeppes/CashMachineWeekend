package rocks.zipcode.atm.bank;

import rocks.zipcode.atm.ActionResult;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ZipCodeWilmington
 */
public class Bank {

    private Map<Integer, Account> accounts = new HashMap<>();

    public Bank() {
        accounts.put(1000, new BasicAccount(new AccountData(
                1000, "Example 1", "example1@gmail.com", 500.0
        )));

        accounts.put(2000, new PremiumAccount(new AccountData(
                2000, "Example 2", "example2@gmail.com", 200.0
        )));
        accounts.put(3000, new BasicAccount(new AccountData(
                3000, "Example 3", "example3@gmail.com", 100000.0
        )));

        accounts.put(4000, new PremiumAccount(new AccountData(
                4000, "Example 4", "example4@gmail.com", 0.0
        )));
        accounts.put(5000, new BasicAccount(new AccountData(
                5000, "Example 5", "example5@gmail.com", 20000.0
        )));

        accounts.put(6000, new PremiumAccount(new AccountData(
                6000, "Example 6", "example6@gmail.com", 10000000.0
        )));
    }


    public ActionResult<AccountData> getAccountById(int id) {
        Account account = accounts.get(id);

        if (account != null) {
            return ActionResult.success(account.getAccountData());
        } else {
            return ActionResult.fail("Invalid Account Number");
        }
    }

    public ActionResult<AccountData> deposit(AccountData accountData, Double amount) {
        Account account = accounts.get(accountData.getId());
        account.deposit(amount);

        return ActionResult.success(account.getAccountData());
    }

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

    public Map<Integer, Account> getAccounts(){
        return this.accounts;
    }
}
