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


    /**
     * CashMachine is a Constructor for a CashMachine object
     * @param bank - a Bank that stores accounts to be accessed by CashMachine
     */
    public CashMachine(Bank bank) {
        this.bank = bank;
    }

    /**
     * update is lambda function used to reset accountData in the tryCall method
     */
    private Consumer<AccountData> update = data -> {
        accountData = data;
    };


    /**
     * login is a method that looks for an account in the bank.  If the account is found, it updates accountData.  If is
     * fails, it gives an error message.
     * @param id - an int that is the id for the account being logged into
     */
    public void login(int id) {
        tryCall(
                () -> bank.getAccountById(id),
                update
        );
    }

    /**
     * checkPin is a method that checks if the pin is correct for an account. If it is correct, it updates
     * the accountData.  If it is incorrect, it gives an error message.
     * @param id - an int that is the id of the account trying to be accessed
     * @param pin - a String that is the pin being checked
     */
    public void checkPin(int id, String pin){
        tryCall(
                () -> bank.checkPin(id, pin),
                update
        );
    }

    /**
     * resetPin is a method that changes the pin of an account's data
     * @param id - an int that is the id of the account to be changed
     * @param pin - a String that is the new pin for the account
     */
    public void resetPin(int id, String pin){
        tryCall(
                () -> bank.resetPin(id, pin),
                update
        );
    }

    /**
     * deposit is a method that deposits money into an account and updates the cashMachine's accountData
     * @param amount - a Double that is the amount of money to be added to the account's balance
     */
    public void deposit(Double amount) {
        if (accountData != null) {
            tryCall(
                    () -> bank.deposit(accountData, amount),
                    update
            );
        }
    }


    /**
     * withdraw is a method that checks if an account can withdraw the amount.  If it can, it withdraws the
     * amount and updates the cashMachines accountData.  If it cannot, it gives an error.
     * @param amount - a Double that is the amount of money to be withdrawn
     */
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

    /**
     * addNewAccount is a method used to add a new account to the bank and update the cashMachine's accountData
     * to that of the new account
     * @param id - an int that is the id of the new account
     * @param name - a String that is the name of the new account holder
     * @param email - a String that is the email of the new account holder
     * @param balance - a Double that is the balance of the new account
     * @param accountType - a String that is either "basic" or "premium" to specify the new account type
     * @param pin - a String that is the PIN of the new account
     */
    public void addNewAccount(int id, String name, String email, Double balance, String accountType, String pin){
        tryCall(
                () -> bank.addAccount(id,name, email, balance, accountType, pin),
                update
        );
    }


    /**
     * exit is a method that sets the cashMachine's accountData to null
     */
    public void exit() {
        if (accountData != null) {
            accountData = null;
        }
    }

    /**
     * toString is a method that returns a String representation of the CashMachine for the machine's display
     * @return a String containing accountData and relevant errors
     */
    @Override
    public String toString() {
        if (accountData == null){
            return "Invalid account number";
        }
        if (error.equals("Error: Invalid PIN")){
            String invalidPinError = error;
            error = "";
            return invalidPinError;
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

    /**
     * tryCall is a method that checks if an ActionResult is a success or a failure.  If it is a success, the result's
     * data is passed to the postAction.  If it fails, an Exception is thrown and an error is printed.
     * @param action - the action whose result is being checked
     * @param postAction - the action that takes places if the original action is a success
     * @param <T> - the ActionResult's data
     */
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


    /**
     * getAccountNumbers is a method that gets all of the bank's account numbers
     * @return an ArrayList of Integers containing all of the bank's account numbers
     */
    public ArrayList<Integer> getAccountNumbers(){
        ArrayList<Integer> accountNumbers = new ArrayList<Integer>();
        for(Integer i : bank.getAccounts().keySet()){
            accountNumbers.add(i);
        }
        return accountNumbers;
    }

    /**
     * getWithdrawSuccess is a getter for the withdrawSuccess field
     * @return a boolean that is the cashMachine's withdrawSuccess field
     */
    public Boolean getWithdrawSuccess() {
        return withdrawSuccess;
    }

    /**
     * setWithdrawSuccess is a setter fot the withdrawSuccess field
     * @param withdrawSuccess - a Boolean representing if a withdraw was successful
     */
    public void setWithdrawSuccess(Boolean withdrawSuccess) {
        this.withdrawSuccess = withdrawSuccess;
    }

    /**
     * getError is a getter for the error field
     * @return a String that is the cashMachine's current error state
     */
    public String getError(){
        return error;
    }
}
