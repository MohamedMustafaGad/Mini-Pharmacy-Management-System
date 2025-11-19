package pharmacy_management_system;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EditMedicineWindow {
    
    // نمرر كائن الدواء الذي نريد تعديله
    public void display(Medicine medicineToEdit) { 
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Edit Medicine: " + medicineToEdit.getName());
        
        // ... (نفس تصميم AddMedicineWindow)
        Label nameLbl = new Label("Name : ");
        TextField nameTxt = new TextField(medicineToEdit.getName()); // ملء البيانات
        
        Label priceLbl = new Label("Price : ");
        TextField priceTxt = new TextField(String.valueOf(medicineToEdit.getPrice())); // ملء البيانات
        
        Label quantityLbl = new Label("Quantity : ");
        TextField quantityTxt = new TextField(String.valueOf(medicineToEdit.getQuantity())); // ملء البيانات
        
        Button confirmBtn = new Button("Save Changes");
        Button cancelBtn = new Button("Cancel");
        
        // ... (نفس الـ Layout)
        HBox hBox1 = new HBox(20,nameLbl,nameTxt);
        HBox hBox2 = new HBox(20,quantityLbl,quantityTxt);
        HBox hBox3 = new HBox(20,priceLbl,priceTxt);
        HBox buttonsBox = new HBox(20, confirmBtn, cancelBtn);
        buttonsBox.setAlignment(Pos.CENTER);
        
        VBox root = new VBox(30, new Label("Editing ID: " + medicineToEdit.getId()), hBox1, hBox2, hBox3, buttonsBox);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        cancelBtn.setOnAction(e -> stage.close());
        
        confirmBtn.setOnAction(e -> {
            try {
                String newName = nameTxt.getText();
                int newPrice = Integer.parseInt(priceTxt.getText());
                int newQuantity = Integer.parseInt(quantityTxt.getText());
                
                // *** هنا نحتاج دالة التحديث في كلاس Database ***
                // Database.updateMedicine(medicineToEdit.getId(), newName, newPrice, newQuantity);
                
                stage.close();
            } catch (NumberFormatException ex) {
                System.out.println("Error: Invalid number format.");
            }
        });
        
        Scene scene = new Scene(root, 400, 400);
        stage.setScene(scene);
        stage.showAndWait();
        
        // في كلاس EditMedicineWindow.java (داخل confirmBtn.setOnAction)

        confirmBtn.setOnAction(e -> {
            try {
                String newName = nameTxt.getText();
                int newPrice = Integer.parseInt(priceTxt.getText());
                int newQuantity = Integer.parseInt(quantityTxt.getText());
        
                // 🚨 استدعاء دالة التحديث الجديدة:
                Database.updateMedicine(medicineToEdit.getId(), newName, newQuantity, newPrice); // ملاحظة: ترتيب price و quantity يتبع Database.updateMedicine
        
                stage.close();
            } catch (NumberFormatException ex) {
                System.out.println("Error: Invalid number format.");
            }
         });

    }
}