/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pharmacy_management_system;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author hp
 */
public class AddMedicineWindow {
    public void display(){
        Stage stage = new Stage();
        
        stage.setTitle("Add a new Medicine");
        Label lbl = new Label("New Medicine");
        Label nameLbl = new Label("Name : ");
        Label priceLbl = new Label("Price : ");
        Label quantityLbl = new Label("Quantity : ");
        TextField nameTxt = new TextField();        
        TextField priceTxt = new TextField();
        TextField quantityTxt = new TextField();
        Button returnBtn = new Button("Return");
        Button confirmBtn = new Button ("Confirm");
        HBox hBox1 = new HBox(20,nameLbl,nameTxt);
        HBox hBox2 = new HBox(20,quantityLbl,quantityTxt);
        HBox hBox3 = new HBox(20,priceLbl,priceTxt);
        HBox hBox4 = new HBox(20,returnBtn,confirmBtn);
        VBox root = new VBox(30,lbl,hBox1,hBox2,hBox3,hBox4);
        root.setAlignment(Pos.CENTER);
        
        returnBtn.setOnAction(e->{stage.close();});
        
        confirmBtn.setOnAction(e->{
            try{
            String name = nameTxt.getText();
            int price = Integer.parseInt(priceTxt.getText());
            int quantity = Integer.parseInt(quantityTxt.getText());
            
            Database.insertNewMedicine(name, quantity, price);
            stage.close();
            }catch(NumberFormatException ex){
                System.out.println("Please enter valid numbers");
            }
        });
        
        Scene scene = new Scene(root,600,400);  
        stage.setScene(scene);
        stage.showAndWait();
    }
}
