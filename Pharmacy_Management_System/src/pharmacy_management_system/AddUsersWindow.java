package pharmacy_management_system;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddUsersWindow {
    
    // لإضافة صلاحية تمرير بيانات إلى النافذة الأم
    private boolean isConfirmed = false; 

    public boolean display() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Add a New User");
        
        // الحقول المطلوبة
        Label nameLbl = new Label("Full Name:");
        TextField nameTxt = new TextField();
        Label usernameLbl = new Label("Username:");
        TextField usernameTxt = new TextField();
        Label passwordLbl = new Label("Password:");
        PasswordField passwordTxt = new PasswordField(); // استخدام PasswordField لإخفاء النص
        Label phoneLbl = new Label("Phone:");
        TextField phoneTxt = new TextField();
        Label emailLbl = new Label("Email:");
        TextField emailTxt = new TextField();
        
        Button confirmBtn = new Button("Confirm");
        Button cancelBtn = new Button("Cancel");
        
        // التنسيق: HBox لكل سطر
        HBox hBox1 = new HBox(10, nameLbl, nameTxt);
        HBox hBox2 = new HBox(10, usernameLbl, usernameTxt);
        HBox hBox3 = new HBox(10, passwordLbl, passwordTxt);
        HBox hBox4 = new HBox(10, phoneLbl, phoneTxt);
        HBox hBox5 = new HBox(10, emailLbl, emailTxt);
        HBox buttonsBox = new HBox(20, confirmBtn, cancelBtn);
        buttonsBox.setAlignment(Pos.CENTER);

        // تنسيق: VBox للحاوية الرئيسية
        VBox root = new VBox(15, new Label("Enter User Details:"), hBox1, hBox2, hBox3, hBox4, hBox5, buttonsBox);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        // منطق الأزرار
        cancelBtn.setOnAction(e -> stage.close());
        
        confirmBtn.setOnAction(e -> {
            try {
                String name = nameTxt.getText();
                String username = usernameTxt.getText();
                String password = passwordTxt.getText();
                int phone = Integer.parseInt(phoneTxt.getText());
                String email = emailTxt.getText();
                
                // التأكد من أن الحقول الأساسية ليست فارغة
                if (name.isEmpty() || username.isEmpty() || password.isEmpty()) {
                    System.out.println("Error: Name, Username, and Password are required.");
                    return;
                }

                Database.insertNewUser(name, username, password, phone, email);
                this.isConfirmed = true; // نؤكد أن العملية تمت بنجاح
                stage.close();
            } catch (NumberFormatException ex) {
                System.out.println("Error: Please enter a valid number for Phone.");
            }
        });
        
        Scene scene = new Scene(root, 400, 450);
        stage.setScene(scene);
        stage.showAndWait();
        
        return isConfirmed;
    }
}