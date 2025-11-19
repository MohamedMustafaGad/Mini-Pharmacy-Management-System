
package pharmacy_management_system;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainDashboard {
    
    private BorderPane root;
    
    public void display(User user){
        Stage stage = new Stage();
        BorderPane root = new BorderPane();
        
        Label welcomeLbl = new Label("Welcome,  " + user.getName());
        welcomeLbl.setFont(new Font("Arial",20));
        root.setCenter(welcomeLbl);
        
        Button productsBtn = new Button("Show Products");
        Button usersBtn = new Button("Show Users");
        productsBtn.setPrefSize(300,40);
        usersBtn.setPrefSize(300,40);
        
        VBox leftBox = new VBox(productsBtn,usersBtn);
        leftBox.setSpacing(20);
        root.setAlignment(leftBox,Pos.CENTER);
        
        root.setLeft(leftBox);
        root.setPadding(new Insets(20));
                
        Scene scene = new Scene(root,500,500);
        stage.setTitle("Pharmacy Management System");
        stage.setScene(scene);
        stage.show();
        
        productsBtn.setOnAction(e->{
            ProductsManagementWindow pw = new ProductsManagementWindow();
            root.setCenter(pw.getView());
        });
        
        usersBtn.setOnAction(e->{
            UserManagementWindow uw = new UserManagementWindow();
            root.setCenter(uw.getView());
        });
    }
}
