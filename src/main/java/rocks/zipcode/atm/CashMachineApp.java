package rocks.zipcode.atm;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import rocks.zipcode.atm.bank.Bank;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author ZipCodeWilmington
 */
public class CashMachineApp extends Application {

    private CashMachine cashMachine = new CashMachine(new Bank());

    private VBox vbox = new VBox(1);

    private GridPane mainGridPane = new GridPane();
    private GridPane newAccountsGridPane = new GridPane();
    private GridPane currentGridPane = mainGridPane;

    private Menu menu = new Menu("Select Account");
    private Menu newAccountMenu = new Menu("Select an Account Type");

    private MenuBar menuBar = new MenuBar();
    private MenuBar newAccountMenuBar = new MenuBar();

    private TextArea areaInfo = new TextArea();
    private TextArea newAccountText = new TextArea();

    private TextField depositField = new TextField("");
    private TextField withdrawField = new TextField("");
    private TextField nameField = new TextField("");
    private TextField emailField = new TextField("");

    private Background buttonBackground = new Background(new BackgroundFill(Color.MEDIUMPURPLE, CornerRadii.EMPTY, Insets.EMPTY));

    private Border buttonBorder = new Border(new BorderStroke(Paint.valueOf(Color.PURPLE.toString()), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));


    private Button btnDeposit = new Button("Deposit");
    private Button btnWithdraw = new Button("Withdraw");
    private Button btnExit = new Button("Exit");
    private Button btnNewAcct = new Button("New Account");
    private Button btnCreateAcct = new Button("Create Account");
    private Button btnBack = new Button("Back");
    private Button btnPin = new Button("Enter");
    private Button btnResetPin = new Button("Reset PIN");
    private Button btnEnterResetPin = new Button("Reset PIN");

    private Text title = new Text("CARA-CA$H MACHINE");
    private Text title2 = new Text("NEW CARA-CA$H ACCOUNT");
    private Text pinHint = new Text("Default PIN is 1234");

    private Integer currentAccount;

    private PasswordField pinField = new PasswordField();
    private PasswordField setPinField = new PasswordField();
    private PasswordField confirmPinField = new PasswordField();
    private PasswordField resetPinField = new PasswordField();
    private PasswordField confirmResetPinField = new PasswordField();


    /**
     * setGridPaneConstraints initializes the number of columns and rows for the GridPane and adds column and row restraints
     *
     * @param gridPane
     */
    private void setGridPaneConstraints(GridPane gridPane) {
        int columns = 54;
        int rows = 20;

        for (int i = 0; i < columns; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPercentWidth(100.0 / columns);
            gridPane.getColumnConstraints().add(columnConstraints);
        }
        for (int i = 0; i < rows; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPercentHeight(100.0 / rows);
            gridPane.getRowConstraints().add(rowConstraints);
        }
    }


    /**
     * buttonSetUp is a method that sets the properties of all Button objects
     */
    public void buttonsSetUp() {
        ArrayList<Button> buttons = new ArrayList<>();
        buttons.add(btnPin);
        buttons.add(btnEnterResetPin);
        buttons.add(btnResetPin);
        buttons.add(btnBack);
        buttons.add(btnCreateAcct);
        buttons.add(btnNewAcct);
        buttons.add(btnDeposit);
        buttons.add(btnWithdraw);
        buttons.add(btnExit);

        for (Button b : buttons) {
            b.setBorder(buttonBorder);
            b.setBackground(buttonBackground);
            b.setDisable(true);
            GridPane.setColumnSpan(b, 10);
        }
    }


    /**
     * mainGridPaneSetUp is the method that adds all children to mainGridPane and sets their action responses
     */
    private void mainGridPaneSetUp() {

        setGridPaneConstraints(mainGridPane);
        //mainGridPane.setGridLinesVisible(true);

        // Title Text
        title.setFill(Color.GOLD);
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 27));
        mainGridPane.add(title, 15, 0, 5, 2);


        // New Account Button
        btnNewAcct.setPrefWidth(160.0);
        btnNewAcct.setDisable(false);
        mainGridPane.add(btnNewAcct, 12, 2);


        // Account Menu Bar
        menuBar.setBackground(buttonBackground);
        menuBar.setBorder(buttonBorder);
        menuBar.getMenus().add(menu);
        accountMenuSetUp();
        menuBar.setPadding(new Insets(0, 0, 0, 0));
        GridPane.setColumnSpan(menuBar, 13);
        mainGridPane.add(menuBar, 29, 2);


        // PIN Field
        pinField.setDisable(true);
        pinField.setPromptText("Enter PIN");
        GridPane.setColumnSpan(pinField, 10);
        mainGridPane.add(pinField, 19, 4);


        // PIN Hint Text
        pinHint.setVisible(false);
        pinHint.setFill(Color.GOLD);
        pinHint.setFont(Font.font("Verdana", FontWeight.BOLD, 11));
        mainGridPane.add(pinHint, 22, 5, 3, 1);


        // Pin Button
        btnPin.setPrefWidth(80);
        mainGridPane.add(btnPin, 29, 4);


        // Deposit Field
        depositField.setDisable(true);
        depositField.setPromptText("Enter amount to deposit");
        GridPane.setColumnSpan(depositField, 25);
        mainGridPane.add(depositField, 11, 6);


        // Deposit Button
        btnDeposit.setPrefWidth(95);
        mainGridPane.add(btnDeposit, 36, 6);


        // Withdraw Field
        withdrawField.setDisable(true);
        withdrawField.setPromptText("Enter amount to withdraw");
        GridPane.setColumnSpan(withdrawField, 25);
        mainGridPane.add(withdrawField, 11, 8);


        // Withdraw Button
        btnWithdraw.setPrefWidth(95);
        mainGridPane.add(btnWithdraw, 36, 8);


        // Text Display Area
        areaInfo.setEditable(false);
        GridPane.setColumnSpan(areaInfo, 40);
        GridPane.setRowSpan(areaInfo, 4);
        mainGridPane.add(areaInfo, 7, 10);
        areaInfo.requestFocus();


        // Reset Pin Button
        btnResetPin.setPrefWidth(120);
        mainGridPane.add(btnResetPin, 21, 15);


        // Reset Pin Field
        resetPinField.setPromptText("Enter new 4 digit PIN");
        GridPane.setColumnSpan(resetPinField, 11);


        // Confirm Reset Pin Field
        confirmResetPinField.setPromptText("Confirm new PIN");
        GridPane.setColumnSpan(confirmResetPinField, 11);


        // Reset Pin Enter Button
        btnEnterResetPin.setPrefWidth(110);


        // Exit Button
        btnExit.setDisable(false);
        btnExit.setPrefWidth(97);
        mainGridPane.add(btnExit, 22, 17);


        // Deposit Button Action
        btnDeposit.setOnAction(e -> {
            try {
                Double amount = Double.parseDouble(depositField.getText());
                cashMachine.deposit(amount);
                depositField.setText("");
                String amountString = String.format("%1$,.2f", amount);
                areaInfo.setText("Deposited $" + amountString + "\n\n" + cashMachine.toString());
            } catch (NumberFormatException depositException) {
                areaInfo.setText("Invalid deposit amount.  Try again.\n\n" + cashMachine.toString());
                depositField.setText("");
            }
        });


        // Withdraw Button Action
        btnWithdraw.setOnAction(e -> {
            try {
                Double amount = Double.parseDouble(withdrawField.getText());
                cashMachine.withdraw(amount);
                withdrawField.setText("");
                if (cashMachine.getWithdrawSuccess()) {
                    String amountString = String.format("%1$,.2f", amount);
                    areaInfo.setText("Withdrew $" + amountString + "\n\n" + cashMachine.toString());
                } else {
                    areaInfo.setText(cashMachine.toString());
                }
                cashMachine.setWithdrawSuccess(false);
            } catch (NumberFormatException withdrawException) {
                areaInfo.setText("Invalid withdrawal amount.  Try again.\n\n" + cashMachine.toString());
                withdrawField.setText("");
            }
        });


        // Exit Button Action
        btnExit.setOnAction(e -> {
            cashMachine.exit();
            btnDeposit.setDisable(true);
            btnWithdraw.setDisable(true);
            btnPin.setDisable(true);
            withdrawField.setDisable(true);
            depositField.setDisable(true);
            areaInfo.setText("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n" +
                    "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n" +
                    "$$$$$$$$$$$$$$$  Thank you for using this Cash Machine!  $$$$$$$$$$$$$$$\n" +
                    "$$$$$$$$$$$$$$$  Use the menu to select another account  $$$$$$$$$$$$$$$\n" +
                    "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n" +
                    "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
            menu.setText("Select an Account");
            btnResetPin.setDisable(true);
        });


        // New Account Button Action
        btnNewAcct.setOnAction(e -> {
            depositField.clear();
            withdrawField.clear();
            areaInfo.clear();
            switchPanes();
        });


        // Enter Pin Button Action
        pinField.setOnMouseClicked(e -> pinHint.setVisible(true));
        btnPin.setOnAction(e -> {
            cashMachine.checkPin(currentAccount, pinField.getText());
            if (cashMachine.getError().equals("Error: Invalid PIN")) {
                pinField.setText("");
                areaInfo.setText(cashMachine.toString());
            } else {
                areaInfo.setText(cashMachine.toString());
                pinField.setText("");
                pinHint.setVisible(false);
                withdrawField.setDisable(false);
                btnWithdraw.setDisable(false);
                depositField.setDisable(false);
                btnDeposit.setDisable(false);
                pinField.setDisable(true);
                btnPin.setDisable(true);
                btnResetPin.setDisable(false);
                areaInfo.requestFocus();
            }
        });


        // Reset PIN Button Action
        btnResetPin.setOnAction(event -> {
            mainGridPane.getChildren().remove(btnResetPin);
            mainGridPane.add(resetPinField, 10, 15);
            mainGridPane.add(confirmResetPinField, 23, 15);
            mainGridPane.add(btnEnterResetPin, 36, 15);
            btnEnterResetPin.setDisable(false);
        });


        // Reset PIN Enter Button Action
        btnEnterResetPin.setOnAction(event -> {
            if (resetPinField.getText().length() != 4) {
                areaInfo.setText("PIN must be 4 digits");
            } else if (!resetPinField.getText().equals(confirmResetPinField.getText())) {
                areaInfo.setText("PINs do not match.  Try again.");
            } else {
                cashMachine.resetPin(currentAccount, resetPinField.getText());
                areaInfo.setText("Pin has been reset.\n" + cashMachine.toString());
                resetPinField.setText("");
                confirmResetPinField.setText("");
                mainGridPane.getChildren().removeAll(resetPinField, confirmResetPinField, btnEnterResetPin);
                mainGridPane.getChildren().add(btnResetPin);
            }
        });
    }

    /**
     * newAccountGridPaneSetUp is the method that adds all of the children and their action responses to the newAccountGridPane
     */
    private void newAccountGridPaneSetUp() {

        setGridPaneConstraints(newAccountsGridPane);
       // newAccountsGridPane.setGridLinesVisible(true);

        // Title Text
        title2.setFill(Color.GOLD);
        title2.setFont(Font.font("Verdana", FontWeight.BOLD, 27));
        GridPane.setColumnSpan(title2, 60);
        GridPane.setRowSpan(title2, 2);
        newAccountsGridPane.add(title2, 11, 0);


        // Name Field
        nameField.setPromptText("Full Name");
        GridPane.setColumnSpan(nameField, 24);
        newAccountsGridPane.add(nameField, 15, 4);


        // Email Field
        emailField.setPromptText("Email Address");
        GridPane.setColumnSpan(emailField, 24);
        newAccountsGridPane.add(emailField, 15, 6);


        // Set Pin Field
        setPinField.setPromptText("Enter 4 Digit PIN");
        GridPane.setColumnSpan(setPinField, 10);
        newAccountsGridPane.add(setPinField, 22, 8);


        // Confirm Pin Field
        confirmPinField.setPromptText("Confirm PIN");
        GridPane.setColumnSpan(confirmPinField, 10);
        newAccountsGridPane.add(confirmPinField, 22, 10);


        // Create Account Button
        btnCreateAcct.setDisable(false);
        btnCreateAcct.setPrefWidth(180);
        newAccountsGridPane.add(btnCreateAcct, 22, 12);


        // New Account Text Area
        newAccountText.setEditable(false);
        GridPane.setColumnSpan(newAccountText, 30);
        GridPane.setRowSpan(newAccountText, 3);
        newAccountText.setPrefColumnCount(40);
        newAccountsGridPane.add(newAccountText, 12, 14);


        // Back Button
        btnBack.setDisable(false);
        btnBack.setPrefWidth(155);
        newAccountsGridPane.add(btnBack, 22, 18);
        newAccountMenuBar.requestFocus();


        // New Account Type Menu
        newAccountMenuBar.setBackground(buttonBackground);
        newAccountMenuBar.setBorder(buttonBorder);
        RadioMenuItem basicAccount = new RadioMenuItem("Basic Account");
        RadioMenuItem premiumAccount = new RadioMenuItem("Premium Account");
        ToggleGroup toggleGroup = new ToggleGroup();
        toggleGroup.getToggles().add(basicAccount);
        toggleGroup.getToggles().add(premiumAccount);
        premiumAccount.setOnAction(event -> newAccountMenu.setText("Premium Account"));
        basicAccount.setOnAction(event -> newAccountMenu.setText("Basic Account"));
        newAccountMenu.getItems().add(basicAccount);
        newAccountMenu.getItems().add(premiumAccount);
        newAccountMenuBar.getMenus().add(newAccountMenu);
        newAccountMenuBar.setPadding(new Insets(0, 0, 0, 0));
        GridPane.setColumnSpan(newAccountMenuBar, 14);
        newAccountsGridPane.add(newAccountMenuBar, 20, 2);


        // Create Account Button Action
        btnCreateAcct.setOnAction(e -> {
            if (nameField.getText().equals("")) {
                newAccountText.setText("Please enter your name to create a new account.");
            } else if (emailField.getText().equals("")) {
                newAccountText.setText("Please enter your email address to create a new account.");
            } else if ((!basicAccount.isSelected()) && (!premiumAccount.isSelected())) {
                newAccountText.setText("Please select a basic or premium account.");
            } else if (setPinField.getText().length() != 4) {
                newAccountText.setText("Please enter a 4 digit PIN");
            } else if (!setPinField.getText().equals(confirmPinField.getText())) {
                newAccountText.setText("PINs do not match.  Try again.");
            } else {
                Random r = new Random();
                int newAccountNumber = r.nextInt(999999999 - 111111111) + 111111111;
                if (basicAccount.isSelected()) {
                    cashMachine.addNewAccount(newAccountNumber, nameField.getText(), emailField.getText(), 0.0, "basic", setPinField.getText());
                }
                if (premiumAccount.isSelected()) {
                    cashMachine.addNewAccount(newAccountNumber, nameField.getText(), emailField.getText(), 0.0, "premium", setPinField.getText());
                }
                accountMenuSetUp();
                newAccountText.setText("Thank you " + nameField.getText() + "!\nYou have successfully created a new account.\n" +
                        "Your account number is " + newAccountNumber + ".\nClick 'back' to deposit money.");
                setPinField.setText("");
                confirmPinField.setText("");
            }
        });


        // Back Button Action
        btnBack.setOnAction(e -> {
            nameField.clear();
            emailField.clear();
            newAccountText.clear();
            newAccountMenu.setText("Select an Account Type");
            btnWithdraw.setDisable(true);
            btnDeposit.setDisable(true);
            menu.setText("Select an Account");
            pinField.setDisable(true);
            btnPin.setDisable(true);
            withdrawField.setDisable(true);
            depositField.setDisable(true);
            switchPanes();

        });

    }


    /**
     * vboxSetUp is a method that initializes the VBox and adds the currentGridPane
     */
    private void vboxSetUp() {
        vbox.setPrefSize(250, 350);
        vbox.setBackground(new Background(new BackgroundFill(Color.LIGHTSEAGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().add(currentGridPane);
        currentGridPane.requestFocus();
    }


    /**
     * swithPanes is a method that toggles the currentGridPane and resets the VBox with the new currentGridPane
     */
    private void switchPanes() {
        if (currentGridPane == mainGridPane) {
            currentGridPane = newAccountsGridPane;
        } else if (currentGridPane == newAccountsGridPane) {
            currentGridPane = mainGridPane;
        }
        vbox.getChildren().clear();
        vboxSetUp();
    }


    /**
     * accountMenuSetUp is a method tht adds all account numbers to the account menu and sets their action responses
     */
    private void accountMenuSetUp() {
        menu.getItems().clear();
        ArrayList<MenuItem> menuItems = new ArrayList<>();
        for (Integer i : cashMachine.getAccountNumbers()) {
            menuItems.add(new MenuItem(i.toString()));
        }
        for (MenuItem m : menuItems) {
            m.setOnAction(event -> {
                currentAccount = Integer.parseInt(m.getText());
                cashMachine.login(currentAccount);
                menu.setText(currentAccount.toString());
                pinField.setDisable(false);
                btnPin.setDisable(false);
                btnWithdraw.setDisable(true);
                btnDeposit.setDisable(true);
                withdrawField.setDisable(true);
                depositField.setDisable(true);
                btnResetPin.setDisable(true);
                areaInfo.setText("Hello!  Thank you for using this Cara-Ca$h Machine.\nPlease enter your PIN.");
            });
            menu.getItems().add(m);
            menu.getItems().add(new SeparatorMenuItem());
        }
    }


    /**
     * createContent is a method that calls the setUp methods for the VBox and GridPanes
     *
     * @return the set up VBox
     */
    private Parent createContent() {
        buttonsSetUp();
        mainGridPaneSetUp();
        newAccountGridPaneSetUp();
        vboxSetUp();
        return vbox;
    }


    /**
     * start is a method that create Scene with the VBox content and sets the Scene for the Stage
     *
     * @param stage - a Stage object for all Children objects
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(createContent()));
        stage.show();
    }


    /**
     * main is a method that launches the program
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
