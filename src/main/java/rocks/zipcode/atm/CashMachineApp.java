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

/**
 * @author ZipCodeWilmington
 */
public class CashMachineApp extends Application {

    private Text title = new Text("Cara's Cash Machine");
    private TextField depositField = new TextField("");
    private TextField withdrawField = new TextField("");
    private CashMachine cashMachine = new CashMachine(new Bank());
    private Integer currentAccount;

    // Menu
    private Menu menu = new Menu("Select an Account");
    MenuBar menuBar = new MenuBar();

    private Parent createContent() {

        // Vbox
        VBox vbox = new VBox(10);
        vbox.setPrefSize(300, 500);
        vbox.setBackground(new Background(new BackgroundFill(Color.LIGHTSEAGREEN, CornerRadii.EMPTY, Insets.EMPTY)));

        // Text Area
        TextArea areaInfo = new TextArea();

        // Text
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

        // Menu
        menuBar.setBackground(buttonBackground);
        menuBar.setBorder(buttonBorder);
        menuBar.setMinWidth(110);
        ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();
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

        // Deposit Button Action
        btnDeposit.setOnAction(e -> {
            int amount = Integer.parseInt(depositField.getText());
            cashMachine.deposit(amount);
            depositField.setText("");
            areaInfo.setText("Deposited $" + amount + "\n\n" +cashMachine.toString());
        });


        // Withdraw Button Action
        btnWithdraw.setOnAction(e -> {
            int amount = Integer.parseInt(withdrawField.getText());
            cashMachine.withdraw(amount);
            withdrawField.setText("");
            if (cashMachine.getWithdrawSuccess()) {
                areaInfo.setText("Withdrew $" + amount + "\n\n" + cashMachine.toString());
            } else {
                areaInfo.setText(cashMachine.toString());
            }
            cashMachine.setWithdrawSuccess(false);
        });

        // Exit Button Action
        btnExit.setOnAction(e -> {
            cashMachine.exit();
            btnDeposit.setDisable(true);
            btnWithdraw.setDisable(true);

            areaInfo.setText("Thank you for using this Cash Machine!\nUse the menu to select another account.");
        });

        // GridPane
        GridPane gridpane = new GridPane();
        gridpane.setAlignment(Pos.TOP_LEFT);

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
        gridpane.add(title,7,0);

        // Menu Bar
        GridPane.setColumnSpan(menuBar, 10);
        gridpane.add(menuBar, 10,1);

        // Deposit Field
        GridPane.setColumnSpan(depositField, 30);
        gridpane.add(depositField, 0, 3);

        // Deposit Button
        GridPane.setColumnSpan(btnDeposit, 6);
        gridpane.add(btnDeposit, 12, 4);

        // Withdraw Field
        GridPane.setColumnSpan(withdrawField, 30);
        gridpane.add(withdrawField, 0, 6);

        // Withdraw Button
        GridPane.setColumnSpan(btnWithdraw, 6);
        gridpane.add(btnWithdraw, 12, 7);

        // Display Text Area
        GridPane.setColumnSpan(areaInfo, 30);
        GridPane.setRowSpan(areaInfo, 5);
        gridpane.add(areaInfo, 0 ,9);

        // Exit Button
        GridPane.setColumnSpan(btnExit, 6);
        gridpane.add(btnExit, 12, 15);


        gridpane.getChildren().addAll();
        vbox.getChildren().addAll(gridpane);
        //vbox.setAlignment(Pos.CENTER);
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
