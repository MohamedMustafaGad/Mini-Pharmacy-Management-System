/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package pharmacy_management_system;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author hp
 */
public class Pharmacy_Management_System extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        Database.initDB();
        
        Label welcomeLbl = new Label("Welcome");
        Label usernameLbl = new Label("Username: ");
        Label passwordLbl = new Label("Password : ");
        TextField usernameTxt = new TextField();
        PasswordField passwordTxt = new PasswordField();
        Button LoginBtn = new Button("Login");
        usernameTxt.setPromptText("Username");
        passwordTxt.setPromptText("Password");
        welcomeLbl.setFont(new Font("Arial",20));
        
        GridPane root = new GridPane();
        root.add(welcomeLbl,0,0);
        root.add(usernameLbl,0,1);
        root.add(passwordLbl,0,2);
        root.add(usernameTxt,1,1);
        root.add(passwordTxt, 1, 2);
        root.add(LoginBtn,2,3);
        root.setPadding(new Insets(50));
        root.setHgap(20);
        root.setVgap(10);
        root.setAlignment(Pos.CENTER);
        
        Scene scene = new Scene(root);
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        LoginBtn.setOnAction(e->{
            User authenticatedUser = Database.authenticateUser(usernameTxt.getText(),passwordTxt.getText());
            if(authenticatedUser != null){
                System.out.println("Success");
                MainDashboard md = new MainDashboard();
                md.display(authenticatedUser);
                primaryStage.close();
                
            }else{
                System.out.println("Failed");
                System.out.println("Check Username & Password");
            }
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
