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

    private VBox vbox = new VBox(10);
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

    private Text title = new Text("CARA-CA$H MACHINE");
    private Text title2 = new Text("NEW CARA-CA$H ACCOUNT");

    private Integer currentAccount;


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


    private void mainGridPaneSetUp() {

        setGridPaneConstraints(mainGridPane);

        // Title Text
        title.setFill(Color.GOLD);
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 27));
        GridPane.setColumnSpan(title, 36);
        GridPane.setRowSpan(title, 2);
        mainGridPane.add(title, 10, 0);

        // New Account Button
        btnNewAcct.setBorder(buttonBorder);
        btnNewAcct.setBackground(buttonBackground);GridPane.setColumnSpan(btnNewAcct, 22);
        btnNewAcct.setPrefWidth(127.0);
        mainGridPane.add(btnNewAcct, 13, 2);


        // Menu Bar
        menuBar.setBackground(buttonBackground);
        menuBar.setBorder(buttonBorder);
        menuBar.getMenus().add(menu);
        accountMenuSetUp();
        menuBar.setPadding(new Insets(0, 0, 0, 0));
        GridPane.setColumnSpan(menuBar, 13);
        mainGridPane.add(menuBar, 28, 2);


        // Deposit Field
        depositField.setPromptText("Enter amount to deposit");
        GridPane.setColumnSpan(depositField, 25);
        mainGridPane.add(depositField, 10, 4);


        // Deposit Button
        btnDeposit.setDisable(true);
        btnDeposit.setBorder(buttonBorder);
        btnDeposit.setBackground(buttonBackground);
        GridPane.setColumnSpan(btnDeposit, 12);
        btnDeposit.setPrefWidth(88);
        mainGridPane.add(btnDeposit, 35, 4);


        // Withdraw Field
        withdrawField.setPromptText("Enter amount to withdraw");
        GridPane.setColumnSpan(withdrawField, 25);
        mainGridPane.add(withdrawField, 10, 6);


        // Withdraw Button
        btnWithdraw.setDisable(true);
        btnWithdraw.setBorder(buttonBorder);
        btnWithdraw.setBackground(buttonBackground);
        GridPane.setColumnSpan(btnWithdraw, 12);
        btnWithdraw.setPrefWidth(88);
        mainGridPane.add(btnWithdraw, 35, 6);


        // Text Display Area
        areaInfo.setEditable(false);
        GridPane.setColumnSpan(areaInfo, 40);
        GridPane.setRowSpan(areaInfo, 4);
        mainGridPane.add(areaInfo, 7, 8);
        areaInfo.requestFocus();


        // Exit Button
        btnExit.setBorder(buttonBorder);
        btnExit.setBackground(buttonBackground);
        GridPane.setColumnSpan(btnExit, 10);
        btnExit.setPrefWidth(97);
        mainGridPane.add(btnExit, 22, 13);



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
            areaInfo.setText("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n" +
                    "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n" +
                    "$$$$$$  Thank you for using this Cash Machine!   $$$$$$\n" +
                    "$$$$$$  Use the menu to select another account  $$$$$$\n" +
                    "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n" +
                    "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        });


        // New Account Button Action
        btnNewAcct.setOnAction(e -> {
            depositField.clear();
            withdrawField.clear();
            areaInfo.clear();
            switchPanes();
        });
    }



    private void newAccountGridPaneSetUp() {

        setGridPaneConstraints(newAccountsGridPane);


        // Title Text
        title2.setFill(Color.GOLD);
        title2.setFont(Font.font("Verdana", FontWeight.BOLD, 27));
        GridPane.setColumnSpan(title2, 60);
        GridPane.setRowSpan(title2, 2);
        newAccountsGridPane.add(title2, 7, 0);


        // Name Field
        nameField.setPromptText("Full Name");
        GridPane.setColumnSpan(nameField, 24);
        newAccountsGridPane.add(nameField, 15, 4);


        // Email Field
        emailField.setPromptText("Email Address");
        GridPane.setColumnSpan(emailField, 24);
        newAccountsGridPane.add(emailField, 15, 6);


        // Create Account Button
        btnCreateAcct.setBorder(buttonBorder);
        btnCreateAcct.setBackground(buttonBackground);
        GridPane.setColumnSpan(btnCreateAcct, 19);
        btnCreateAcct.setPrefWidth(170);
        newAccountsGridPane.add(btnCreateAcct, 19, 8);


        // New Account Text Area
        newAccountText.setEditable(false);
        GridPane.setColumnSpan(newAccountText, 30);
        GridPane.setRowSpan(newAccountText, 4);
        newAccountText.setPrefColumnCount(40);
        newAccountsGridPane.add(newAccountText, 12, 10);


        // Back Button
        btnBack.setBorder(buttonBorder);
        btnBack.setBackground(buttonBackground);
        GridPane.setColumnSpan(btnBack, 14);
        btnBack.setPrefWidth(150);
        newAccountsGridPane.add(btnBack, 20, 15);
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
        GridPane.setColumnSpan(newAccountMenuBar, 16);
        newAccountsGridPane.add(newAccountMenuBar, 19, 2);



        // Create Account Button Action
        btnCreateAcct.setOnAction(e -> {
            if (nameField.getText().equals("")) {
                newAccountText.setText("Please enter your name to create a new account.");
            } else if (emailField.getText().equals("")) {
                newAccountText.setText("Please enter your email address to create a new account.");
            } else if ((!basicAccount.isSelected()) && (!premiumAccount.isSelected())) {
                newAccountText.setText("Please select a basic or premium account.");
            } else {
                Random r = new Random();
                int newAccountNumber = r.nextInt(999999999 - 111111111) + 111111111;
                if (basicAccount.isSelected()) {
                    cashMachine.addNewAccount(newAccountNumber, nameField.getText(), emailField.getText(), 0.0, "basic");
                }
                if (premiumAccount.isSelected()) {
                    cashMachine.addNewAccount(newAccountNumber, nameField.getText(), emailField.getText(), 0.0, "premium");
                }
                accountMenuSetUp();
                newAccountText.setText("Thank you " + nameField.getText() + "!\nYou have successfully created a new account.\n" +
                        "Your account number is " + newAccountNumber + ".\nClick 'back' to deposit money.");
            }
        });


        // Back Button Action
        btnBack.setOnAction(e -> {
            nameField.clear();
            emailField.clear();
            newAccountText.clear();
            newAccountMenu.setText("Select an Account Type");
            switchPanes();

        });

    }


    private void vboxSetUp() {
        vbox.setPrefSize(250, 350);
        vbox.setBackground(new Background(new BackgroundFill(Color.LIGHTSEAGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().add(currentGridPane);
        currentGridPane.requestFocus();
    }


    private void switchPanes() {
        if (currentGridPane == mainGridPane) {
            currentGridPane = newAccountsGridPane;
        } else if (currentGridPane == newAccountsGridPane) {
            currentGridPane = mainGridPane;
        }
        vbox.getChildren().clear();
        vboxSetUp();
    }


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
                areaInfo.setText(cashMachine.toString());
                btnDeposit.setDisable(false);
                btnWithdraw.setDisable(false);
            });
            menu.getItems().add(m);
            menu.getItems().add(new SeparatorMenuItem());
        }
    }


    // createContent sets up vbox and all of its components
    private Parent createContent() {
        mainGridPaneSetUp();
        newAccountGridPaneSetUp();
        vboxSetUp();
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
