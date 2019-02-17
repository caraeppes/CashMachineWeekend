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
    private Menu menu = new Menu("Select Account");
    MenuBar menuBar = new MenuBar();
    private  Menu newAccountMenu = new Menu("Select an Account Type");
    MenuBar newAccountMenuBar = new MenuBar();

    // createContent sets up vbox and all of its components
    private Parent createContent() {

        // Vbox
        VBox vbox = new VBox(10);
        vbox.setPrefSize(250, 350);
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
        Text title = new Text("CARA'S CA$H MACHINE");
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

        // Withdraw Button
        Button btnWithdraw = new Button("Withdraw");
        btnWithdraw.setDisable(true);
        btnWithdraw.setBorder(buttonBorder);
        btnWithdraw.setBackground(buttonBackground);

        // Exit Button
        Button btnExit = new Button("Exit");
        btnExit.setBorder(buttonBorder);
        btnExit.setBackground(buttonBackground);

        // New Account Button
        Button btnNewAcct = new Button("New Account");
        btnNewAcct.setBorder(buttonBorder);
        btnNewAcct.setBackground(buttonBackground);

        // Create Account Button
        Button btnCreateAcct = new Button("Create Account");
        btnCreateAcct.setBorder(buttonBorder);
        btnCreateAcct.setBackground(buttonBackground);

        // Back Button
        Button btnBack = new Button("Back");
        btnBack.setBorder(buttonBorder);
        btnBack.setBackground(buttonBackground);

        // Select Account  Menu
        menuBar.setBackground(buttonBackground);
        menuBar.setBorder(buttonBorder);
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
        gridpane.setAlignment(Pos.TOP_CENTER);
        final int columns = 54;
        final int rows = 16;

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
        GridPane.setColumnSpan(title, 36);
        GridPane.setRowSpan(title, 2);
        gridpane.add(title,12,0);

        // Menu Bar
        GridPane.setColumnSpan(menuBar, 18);
        gridpane.add(menuBar, 24,2);

        // New Account Button
        GridPane.setColumnSpan(btnNewAcct, 22);
        gridpane.add(btnNewAcct,12, 2);

        // Deposit Field
        depositField.setPromptText("Enter amount to deposit");
        GridPane.setColumnSpan(depositField, 26);
        gridpane.add(depositField, 11, 4);

        // Deposit Button
         GridPane.setColumnSpan(btnDeposit, 20);
        gridpane.add(btnDeposit, 37, 4);

        // Withdraw Field
        withdrawField.setPromptText("Enter amount to withdraw");
        GridPane.setColumnSpan(withdrawField, 25);
        gridpane.add(withdrawField, 11, 6);

        // Withdraw Button
        GridPane.setColumnSpan(btnWithdraw, 15);
        gridpane.add(btnWithdraw, 36, 6);

        // Display Text Area
        GridPane.setColumnSpan(areaInfo, 42);
        GridPane.setRowSpan(areaInfo, 4);
        gridpane.add(areaInfo, 6 ,8);
        areaInfo.requestFocus();

        // Exit Button
        GridPane.setColumnSpan(btnExit, 10);
        gridpane.add(btnExit, 25, 13);

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

            areaInfo.setText("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n" +
                    "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n" +
                    "$$$$$$$$$$  Thank you for using this Cash Machine!   $$$$$$$$$$\n" +
                    "$$$$$$$$$$  Use the menu to select another account  $$$$$$$$$$\n" +
                    "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n" +
                    "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
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

            //gridpane.setGridLinesVisible(true);
            gridpane.add(title,12,0);

            // Account Type Menu Bar
            GridPane.setColumnSpan(newAccountMenuBar, 30);
            gridpane.add(newAccountMenuBar, 12,2);

            // Name Field
            nameField.setPromptText("Full Name");
            GridPane.setColumnSpan(nameField, 30);
            gridpane.add(nameField, 12, 4);

            // Email Field
            emailField.setPromptText("Email Address");
            GridPane.setColumnSpan(emailField, 30);
            gridpane.add(emailField, 12, 6);

            // Create Account Button
            GridPane.setColumnSpan(btnCreateAcct, 19);
            gridpane.add(btnCreateAcct, 22, 8);

            // New Account Text Area
            GridPane.setColumnSpan(newAccountText, 42);
            GridPane.setRowSpan(newAccountText, 3);
            gridpane.add(newAccountText, 6 ,10);

            // Back Button
            GridPane.setColumnSpan(btnBack, 9);
            gridpane.add(btnBack, 25, 14);

            newAccountMenuBar.requestFocus();
        });

        // Back Button Action
        btnBack.setOnAction(e -> {
            nameField.clear();
            emailField.clear();
            newAccountText.clear();
            newAccountMenu.setText("Select an Account Type");
            gridpane.getChildren().clear();
            gridpane.add(title,12,0);
            gridpane.add(menuBar, 24,2);
            gridpane.add(btnNewAcct, 12, 2);
            gridpane.add(depositField, 11, 4);
            gridpane.add(btnDeposit, 37, 4);
            gridpane.add(withdrawField, 11, 6);
            gridpane.add(btnWithdraw, 36, 6);
            gridpane.add(areaInfo, 6 ,8);
            gridpane.add(btnExit, 25, 13);

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
