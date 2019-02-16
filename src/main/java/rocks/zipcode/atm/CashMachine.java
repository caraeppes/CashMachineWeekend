package rocks.zipcode.atm;

import rocks.zipcode.atm.bank.AccountData;
import rocks.zipcode.atm.bank.Bank;
import rocks.zipcode.atm.bank.PremiumAccount;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author ZipCodeWilmington
 */
public class CashMachine {

    private final Bank bank;
    private AccountData accountData = null;
    private String error = "";
    private Boolean withdrawSuccess = false;

    public CashMachine(Bank bank) {
        this.bank = bank;
    }

    private Consumer<AccountData> update = data -> {
        accountData = data;
    };

    public void login(int id) {
        tryCall(
                () -> bank.getAccountById(id),
                update
        );
    }

    public void deposit(Double amount) {
        if (accountData != null) {
            tryCall(
                    () -> bank.deposit(accountData, amount),
                    update
            );
        }
    }

    public void withdraw(Double amount) {
        if (accountData != null) {
            if ((accountData.getBalance() - amount) >= 0){
                withdrawSuccess = true;
            }
            else if (bank.getAccounts().get(accountData.getId()) instanceof PremiumAccount) {
                if ((accountData.getBalance() - amount) >= 100){
                    withdrawSuccess = true;
                }
            }
            tryCall(
                    () -> bank.withdraw(accountData, amount),
                    update
            );
        }
    }

    public void exit() {
        if (accountData != null) {
            accountData = null;
        }
    }

    @Override
    public String toString() {
        if (accountData == null){
            return "Invalid account number";
        }
        if (!error.equals("")){
            String errorMessage = error;
            error = "";
            return errorMessage + "\n" + accountData.toString();
        }
        if (accountData.getBalance() < 0){
            return "Warning: Account has been overdrafted.\n\n" + accountData.toString();
        }

        else {
            return accountData.toString();
        }
    }

    private <T> void tryCall(Supplier<ActionResult<T> > action, Consumer<T> postAction) {
        try {
            ActionResult<T> result = action.get();
            if (result.isSuccess()) {
                T data = result.getData();
                postAction.accept(data);
            } else {
                String errorMessage = result.getErrorMessage();
                throw new RuntimeException(errorMessage);
            }
        } catch (Exception e) {

            System.out.println("Error: " + e.getMessage());
            error = "Error: " + e.getMessage();

        }
    }

    public ArrayList<Integer> getAccountNumbers(){
        ArrayList<Integer> accountNumbers = new ArrayList<Integer>();
        for(Integer i : bank.getAccounts().keySet()){
            accountNumbers.add(i);
        }
        return accountNumbers;
    }

    public Bank getBank() {
        return bank;
    }

    public Boolean getWithdrawSuccess() {
        return withdrawSuccess;
    }

    public void setWithdrawSuccess(Boolean withdrawSuccess) {
        this.withdrawSuccess = withdrawSuccess;
    }
}
