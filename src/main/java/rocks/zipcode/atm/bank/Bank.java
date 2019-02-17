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
        accounts.put(545353774, new BasicAccount(new AccountData(
                545353774, "Cara Eppes", "bigD0ggggy@gmail.com", 5000000000.0
        )));

        accounts.put(246801357, new PremiumAccount(new AccountData(
                246801357, "Sean Rowan", "seanyb0ii@yahoo.com", 200.0
        )));
        accounts.put(836592742, new BasicAccount(new AccountData(
                836592742, "Zaina Cruz-King", "Zmoney$$$@gmail.com", 100000.0
        )));

        accounts.put(947252432, new PremiumAccount(new AccountData(
                947252432, "Ashley Smith", "igottalottacASH@gmail.com", 0.0
        )));
        accounts.put(683294753, new BasicAccount(new AccountData(
                683294753, "Michael Krohn", "mimis4lyfe@gmail.com", 20000.0
        )));

        accounts.put(174493326, new PremiumAccount(new AccountData(
                174493326, "Brian Wong", "BriBriDaFlyGuy@gmail.com", 10000000.0
        )));
        accounts.put(632819323, new BasicAccount(new AccountData(
                632819323, "Jim Coates", "pEaCeLuVnBeArDzZz@aol.com", 3434343.43
        )));
        accounts.put(295464425, new PremiumAccount(new AccountData(
                295464425, "Charles Wilmer", "GnarShr3dderDude@comcast.net", 999.99
        )));
    }


    public ActionResult<AccountData> addAccount (int id, String name, String email, Double balance, String accountType) {
        if (accountType.equals("basic")){
            accounts.put(id, new BasicAccount(new AccountData(
                    id, name, email, balance)));
        }
        if (accountType.equals("premium")) {
            accounts.put(id, new PremiumAccount(new AccountData(
                    id, name, email, balance)));
        }

        Account newAccount = accounts.get(id);

        return ActionResult.success(newAccount.getAccountData());

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
