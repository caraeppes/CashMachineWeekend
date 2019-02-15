package rocks.zipcode.atm;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import rocks.zipcode.atm.bank.Bank;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.FlowPane;

import java.util.ArrayList;

/**
 * @author ZipCodeWilmington
 */
public class CashMachineApp extends Application {

    private TextField depositField = new TextField("");
    private TextField withdrawField = new TextField();
  //  private TextField setAccountIdField = new TextField("Account Number");
    private CashMachine cashMachine = new CashMachine(new Bank());
    private Menu menu = new Menu("Select an Account");
    MenuBar menuBar = new MenuBar();



    private Parent createContent() {
        VBox vbox = new VBox(10);
        vbox.setPrefSize(400, 500);

        TextArea areaInfo = new TextArea();

        ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();
        for (Integer i : cashMachine.getAccountNumbers()){
            menuItems.add(new MenuItem(i.toString()));
        }
        for (MenuItem m : menuItems){
            m.setOnAction(event -> {
                        cashMachine.login(Integer.parseInt(m.getText()));
                     //   setAccountIdField.setText(m.getText());
                        areaInfo.setText(cashMachine.toString());
                    });
            menu.getItems().add(m);
        }
        menuBar.getMenus().add(menu);


/*
        Button btnSubmit = new Button("Enter Account Number");
        btnSubmit.setOnAction(e -> {
            int id = Integer.parseInt(setAccountIdField.getText());
            cashMachine.login(id);

            areaInfo.setText(cashMachine.toString());
        });
*/
        Button btnDeposit = new Button("Deposit");
        btnDeposit.setOnAction(e -> {
            int amount = Integer.parseInt(depositField.getText());
            cashMachine.deposit(amount);

            areaInfo.setText(cashMachine.toString());
        });

        Button btnWithdraw = new Button("Withdraw");
        btnWithdraw.setOnAction(e -> {
            int amount = Integer.parseInt(withdrawField.getText());
            cashMachine.withdraw(amount);



            areaInfo.setText(cashMachine.toString());
        });

        Button btnExit = new Button("Exit");
        btnExit.setOnAction(e -> {
            cashMachine.exit();

            areaInfo.setText(cashMachine.toString());
        });

        GridPane gridpane = new GridPane();
        gridpane.setAlignment(Pos.CENTER);
        gridpane.setVgap(5.0);
        gridpane.setHgap(5.0);

/*        GridPane.setColumnSpan(setAccountIdField, 10);
        gridpane.add(setAccountIdField, 0 , 3);

        gridpane.add(btnSubmit, 0, 6);
*/
        GridPane.setColumnSpan(depositField, 10);
        gridpane.add(depositField, 0, 3);

        gridpane.add(btnDeposit ,0 ,6);

        GridPane.setColumnSpan(withdrawField, 10);
        gridpane.add(withdrawField, 0, 9);

        gridpane.add(btnWithdraw, 0, 12);
        // gridpane.setGridLinesVisible(true);

        gridpane.add(areaInfo, 0, 15, 10, 10);

        gridpane.add(btnExit, 0, 29);




        gridpane.getChildren().addAll();


        vbox.getChildren().addAll(menuBar, gridpane);
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
