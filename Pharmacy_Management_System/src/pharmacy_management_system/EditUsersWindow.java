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

public class EditUsersWindow {
    
    public void display(User userToEdit) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Edit User: " + userToEdit.getUsername());
        
        // الحقول المطلوبة (مليئة بالبيانات القديمة)
        Label nameLbl = new Label("Full Name:");
        TextField nameTxt = new TextField(userToEdit.getName());
        Label usernameLbl = new Label("Username:");
        TextField usernameTxt = new TextField(userToEdit.getUsername());
        Label passwordLbl = new Label("Password:");
        PasswordField passwordTxt = new PasswordField();
        passwordTxt.setPromptText("Enter new password (optional)");
        Label phoneLbl = new Label("Phone:");
        TextField phoneTxt = new TextField(String.valueOf(userToEdit.getPhone()));
        Label emailLbl = new Label("Email:");
        TextField emailTxt = new TextField(userToEdit.getEmail());

        Button confirmBtn = new Button("Save Changes");
        Button cancelBtn = new Button("Cancel");
        
        // ... (الـ Layouts كما في AddUserWindow)
        HBox hBox1 = new HBox(10, nameLbl, nameTxt);
        HBox hBox2 = new HBox(10, usernameLbl, usernameTxt);
        HBox hBox3 = new HBox(10, passwordLbl, passwordTxt);
        HBox hBox4 = new HBox(10, phoneLbl, phoneTxt);
        HBox hBox5 = new HBox(10, emailLbl, emailTxt);
        HBox buttonsBox = new HBox(20, confirmBtn, cancelBtn);
        buttonsBox.setAlignment(Pos.CENTER);

        VBox root = new VBox(15, new Label("Editing User ID: " + userToEdit.getId()), hBox1, hBox2, hBox3, hBox4, hBox5, buttonsBox);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        cancelBtn.setOnAction(e -> stage.close());
        
        confirmBtn.setOnAction(e -> {
            // منطق التعديل
            try {
                String newName = nameTxt.getText();
                String newUsername = usernameTxt.getText();
                // نأخذ كلمة المرور الجديدة أو نحافظ على القديمة
                String newPassword = passwordTxt.getText().isEmpty() ? userToEdit.getPassword() : passwordTxt.getText(); 
                int newPhone = Integer.parseInt(phoneTxt.getText());
                String newEmail = emailTxt.getText();
                
                // *** هنا نحتاج دالة التحديث في كلاس Database ***
                // Database.updateUser(userToEdit.getId(), newName, newUsername, newPassword, newPhone, newEmail);
                
                stage.close();
            } catch (NumberFormatException ex) {
                System.out.println("Error: Please enter a valid number for Phone.");
            }
        });
        
        Scene scene = new Scene(root, 450, 450);
        stage.setScene(scene);
        stage.showAndWait();
        
        // في كلاس EditUsersWindow.java (داخل confirmBtn.setOnAction)

        confirmBtn.setOnAction(e -> {
            try {
                String newName = nameTxt.getText();
                String newUsername = usernameTxt.getText();
                // نحافظ على كلمة المرور القديمة لو لم يتم إدخال شيء جديد
                String newPassword = passwordTxt.getText().isEmpty() ? userToEdit.getPassword() : passwordTxt.getText(); 
                int newPhone = Integer.parseInt(phoneTxt.getText());
                String newEmail = emailTxt.getText();
        
                // 🚨 استدعاء دالة التحديث الجديدة:
                Database.updateUser(userToEdit.getId(), newName, newUsername, newPassword, newPhone, newEmail);
        
                stage.close();
            } catch (NumberFormatException ex) {
                System.out.println("Error: Please enter a valid number for Phone.");
            }
        });
    }
}