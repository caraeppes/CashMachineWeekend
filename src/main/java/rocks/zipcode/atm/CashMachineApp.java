package rocks.zipcode.atm;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
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
    private Integer currentAccount;

    // Menus
    private Menu menu = new Menu("Select an Account");
    MenuBar menuBar = new MenuBar();
    private  Menu newAccountMenu = new Menu("Select an Account Type");
    MenuBar newAccountMenuBar = new MenuBar();

    // createContent sets up vbox and all of its components
    private Parent createContent() {

        String newAccountName = "";
        String newAccountEmail = "";
        String newAccountType = "";

        // Vbox
        VBox vbox = new VBox(10);
        vbox.setPrefSize(300, 500);
        vbox.setBackground(new Background(new BackgroundFill(Color.LIGHTSEAGREEN, CornerRadii.EMPTY, Insets.EMPTY)));

        // Text Area
        TextArea areaInfo = new TextArea();
        areaInfo.setEditable(false);
        TextArea newAccountText = new TextArea();
        newAccountText.setEditable(false);

        // Text Fields
        TextField depositField = new TextField("");
        TextField withdrawField = new TextField("");
        TextField nameField = new TextField("");
        TextField emailField = new TextField("");

        // Text
        Text title = new Text("Cara's Cash Machine");
        Font font = new Font(28 );
        title.setFill(Color.PURPLE);
        title.setFont(font);

        // Buttons
        Background buttonBackground = new Background(new BackgroundFill(Color.MEDIUMPURPLE, CornerRadii.EMPTY, Insets.EMPTY));
        Border buttonBorder = new Border(new BorderStroke(Paint.valueOf(Color.PURPLE.toString()), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));

        // Deposit Button
        Button btnDeposit = new Button("Deposit");
        btnDeposit.setDisable(true);
        btnDeposit.setBorder(buttonBorder);
        btnDeposit.setBackground(buttonBackground);
        btnDeposit.setMinWidth(90);

        // Withdraw Button
        Button btnWithdraw = new Button("Withdraw");
        btnWithdraw.setDisable(true);
        btnWithdraw.setBorder(buttonBorder);
        btnWithdraw.setBackground(buttonBackground);
        btnWithdraw.setMinWidth(90);

        // Exit Button
        Button btnExit = new Button("Exit");
        btnExit.setBorder(buttonBorder);
        btnExit.setBackground(buttonBackground);
        btnExit.setMinWidth(90);

        // New Account Button
        Button btnNewAcct = new Button("New Account");
        btnNewAcct.setBorder(buttonBorder);
        btnNewAcct.setBackground(buttonBackground);
        btnNewAcct.setMinWidth(90);

        // Create Account Button
        Button btnCreateAcct = new Button("Create Account");
        btnCreateAcct.setBorder(buttonBorder);
        btnCreateAcct.setBackground(buttonBackground);
        btnCreateAcct.setMinWidth(90);

        // Back Button
        Button btnBack = new Button("Back");
        btnBack.setBorder(buttonBorder);
        btnBack.setBackground(buttonBackground);
        btnBack.setMinWidth(90);

        // Select Account  Menu
        menuBar.setBackground(buttonBackground);
        menuBar.setBorder(buttonBorder);
        menuBar.setMinWidth(100);
        ArrayList<MenuItem> menuItems = new ArrayList<>();
        for (Integer i : cashMachine.getAccountNumbers()){
            menuItems.add(new MenuItem(i.toString()));
        }
        for (MenuItem m : menuItems){
            m.setOnAction(event -> {
                currentAccount = Integer.parseInt(m.getText());
                cashMachine.login(currentAccount);
                areaInfo.setText(cashMachine.toString());
                btnDeposit.setDisable(false);
                btnWithdraw.setDisable(false);
                    });
            menu.getItems().add(m);
            menu.getItems().add(new SeparatorMenuItem());
        }
        menuBar.getMenus().add(menu);

        // New Account Menu
        newAccountMenuBar.setBackground(buttonBackground);
        newAccountMenuBar.setBorder(buttonBorder);
        newAccountMenuBar.setMinWidth(150);
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

        // GridPane
        GridPane gridpane = new GridPane();
        final int columns = 30;
        final int rows = 17;

        // set ColumnConstraints
        for (int i = 0; i < columns; i++){
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPercentWidth(100.0 / columns);
            gridpane.getColumnConstraints().add(columnConstraints);
        }

        // set RowRestraints
        for (int i = 0; i < rows; i++){
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPercentHeight(100.0 / rows);
            gridpane.getRowConstraints().add(rowConstraints);
        }

        // Title Text
        GridPane.setColumnSpan(title, 12);
        gridpane.add(title,8,0);

        // Menu Bar
        GridPane.setColumnSpan(menuBar, 8);
        gridpane.add(menuBar, 18,2);

        // New Account Button
        GridPane.setColumnSpan(btnNewAcct, 6);
        gridpane.add(btnNewAcct, 4, 2);

        // Deposit Field
        depositField.setPromptText("Enter amount to deposit");
        GridPane.setColumnSpan(depositField, 25);
        gridpane.add(depositField, 2, 4);

        // Deposit Button
        GridPane.setColumnSpan(btnDeposit, 6);
        gridpane.add(btnDeposit, 12, 5);

        // Withdraw Field
        withdrawField.setPromptText("Enter amount to withdraw");
        GridPane.setColumnSpan(withdrawField, 25);
        gridpane.add(withdrawField, 2, 7);

        // Withdraw Button
        GridPane.setColumnSpan(btnWithdraw, 6);
        gridpane.add(btnWithdraw, 12, 8);

        // Display Text Area
        GridPane.setColumnSpan(areaInfo, 25);
        GridPane.setRowSpan(areaInfo, 5);
        gridpane.add(areaInfo, 2 ,10);
        areaInfo.requestFocus();

        // Exit Button
        GridPane.setColumnSpan(btnExit, 6);
        gridpane.add(btnExit, 12, 16);

        // Deposit Button Action
        btnDeposit.setOnAction(e -> {
            try {
                Double amount = Double.parseDouble(depositField.getText());
                cashMachine.deposit(amount);
                depositField.setText("");
                String amountString = String.format("%1$,.2f", amount);
                areaInfo.setText("Deposited $" + amountString + "\n\n" + cashMachine.toString());
            } catch (NumberFormatException depositException){
                areaInfo.setText("Invalid deposit amount.  Try again.\n\n" + cashMachine.toString());
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
            } catch (NumberFormatException withdrawException){
                areaInfo.setText("Invalid withdrawal amount.  Try again.\n\n" + cashMachine.toString());
            }
        });

        // Exit Button Action
        btnExit.setOnAction(e -> {
            cashMachine.exit();
            btnDeposit.setDisable(true);
            btnWithdraw.setDisable(true);

            areaInfo.setText("Thank you for using this Cash Machine!\nUse the menu to select another account.");
        });

        // Create Account Button
        btnCreateAcct.setOnAction(e -> {
            if (nameField.getText().equals("")){
                newAccountText.setText("Please enter your name to create a new account.");
            }
            else if (emailField.getText().equals("")){
                newAccountText.setText("Please enter your email address to create a new account.");
            }
            else if((!basicAccount.isSelected()) && (!premiumAccount.isSelected())){
                newAccountText.setText("Please select a basic or premium account.");
            }
            else {
                Random r = new Random();
                int newAccountNumber = r.nextInt(999999999 - 111111111) + 111111111;
                if (basicAccount.isSelected()) {
                    cashMachine.getBank().addAccount(newAccountNumber, nameField.getText(), emailField.getText(), 0.0, "basic");
                }
                if (premiumAccount.isSelected()) {
                    cashMachine.getBank().addAccount(newAccountNumber, nameField.getText(), emailField.getText(), 0.0, "premium");
                }
                MenuItem newMenuItem = new MenuItem(String.valueOf(newAccountNumber));
                menu.getItems().add(newMenuItem);
                menu.getItems().add(new SeparatorMenuItem());
                newMenuItem.setOnAction(event -> {
                    currentAccount = Integer.parseInt(newMenuItem.getText());
                    cashMachine.login(currentAccount);
                    areaInfo.setText(cashMachine.toString());
                    btnDeposit.setDisable(false);
                    btnWithdraw.setDisable(false);
                });
                newAccountText.setText("Thank you " + nameField.getText() + "!\nYou have successfully created a new account.\n" +
                        "Your account number is " + newAccountNumber + ".\nClick 'back' to deposit money.");
            }
        });

        // New Account Button Action
        btnNewAcct.setOnAction(e -> {
            depositField.clear();
            withdrawField.clear();
            areaInfo.clear();
            gridpane.getChildren().clear();

            gridpane.add(title,8,0);

            // Account Type Menu Bar
            GridPane.setColumnSpan(newAccountMenuBar, 10);
            gridpane.add(newAccountMenuBar, 9,2);

            // Name Field
            nameField.setPromptText("Full Name");
            GridPane.setColumnSpan(nameField, 20);
            gridpane.add(nameField, 4, 4);

            // Email Field
            emailField.setPromptText("Email Address");
            GridPane.setColumnSpan(emailField, 20);
            gridpane.add(emailField, 4, 6);

            // Create Account Button
            GridPane.setColumnSpan(btnCreateAcct, 10);
            gridpane.add(btnCreateAcct,11, 8);

            // New Account Text Area
            GridPane.setColumnSpan(newAccountText, 20);
            GridPane.setRowSpan(newAccountText, 5);
            gridpane.add(newAccountText, 4 ,10);

            // Back Button
            GridPane.setColumnSpan(btnBack, 6);
            gridpane.add(btnBack, 12, 16);

            newAccountMenuBar.requestFocus();
        });

        // Back Button Action
        btnBack.setOnAction(e -> {
            nameField.clear();
            emailField.clear();
            newAccountText.clear();
            newAccountMenu.setText("Select an Account Type");
            gridpane.getChildren().clear();
            gridpane.add(title,8,0);
            gridpane.add(menuBar, 18,2);
            gridpane.add(btnNewAcct, 4, 2);
            gridpane.add(depositField, 2, 4);
            gridpane.add(btnDeposit, 12, 5);
            gridpane.add(withdrawField, 2, 7);
            gridpane.add(btnWithdraw, 12, 8);
            gridpane.add(areaInfo, 2 ,10);
            gridpane.add(btnExit, 12, 16);

        });

        vbox.getChildren().addAll(gridpane);
        vbox.setAlignment(Pos.CENTER);
        return vbox;
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(createContent()));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
