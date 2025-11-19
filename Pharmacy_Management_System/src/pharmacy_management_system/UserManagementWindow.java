
package pharmacy_management_system;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author hp
 */
public class UserManagementWindow {
    
    private ObservableList<User> userList;
    private TableView<User> tableView;
    
    public VBox getView(){
        
        tableView=new TableView<>();
        Button addBtn = new Button ("add User");
        Button editBtn = new Button("Edit User"); // 🚨 زر جديد
        Button deleteBtn = new Button ("delete User");
        Button searchBtn = new Button ("Search");
//        Button toMedicineWindowBtn = new Button("MedicineDashBoard");
        TextField searchTxt = new TextField();
        searchTxt.setPromptText("Type here");
        
        
        
        TableColumn<User,Integer> idCol = new TableColumn<>("ID");
        TableColumn<User,String> nameCol = new TableColumn<>("Name");
        TableColumn<User,String> usernameCol = new TableColumn<>("Username");
        TableColumn<User,Integer> phoneCol = new TableColumn<>("Phone");
        TableColumn<User,String> emailCol = new TableColumn<>("Email");
        
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        phoneCol.setCellValueFactory(cellDate -> new SimpleObjectProperty<>(cellDate.getValue().getPhone()));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        
        tableView.getColumns().addAll(idCol,nameCol,usernameCol,phoneCol,emailCol);
        userList=FXCollections.observableArrayList();
        refreshList();
        
        
        HBox searchBox = new HBox(15,searchTxt,searchBtn);
        HBox btnBox = new HBox(15,addBtn,editBtn,deleteBtn);
        VBox root = new VBox(20,searchBox,tableView,btnBox/*,toMedicineWindowBtn*/);
        
        root.setPadding(new Insets(30));
        
//        Scene scene = new Scene(root,600,600);
//        stage.setTitle("User Management");
//        stage.setScene(scene);
//        stage.show();
        
        // داخل دالة display في UserManagementWindow:

//        toMedicineWindowBtn.setOnAction(e -> {
//            Test mainApp = new Test();
//            try {
//            // نفتح النافذة الرئيسية ونغلق الحالية
//                mainApp.start(new Stage()); 
//                stage.close();
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        });
        
        addBtn.setOnAction(e -> {
            AddUsersWindow add = new AddUsersWindow();
            boolean success = add.display(); // يفتح النافذة
            if (success) {
                refreshList(); // تحديث القائمة بعد إضافة المستخدم
            }
        });
        
        editBtn.setOnAction(e -> {
            User selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                EditUsersWindow edit = new EditUsersWindow();
                edit.display(selected); // نمرر كائن المستخدم المحدد
                refreshList(); // تحديث القائمة بعد العودة من نافذة التعديل
            } else {
                System.out.println("Error: Please select a user to edit.");
            }
        });

        deleteBtn.setOnAction(e -> {
            User selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                Database.DeleteUser(selected.getId()); 
                refreshList();
            } else {
               System.out.println("Error: Please select a user to delete.");
            }
        });
        
//        toMedicineWindowBtn.setOnAction(e->{
//            Test t = new Test();
//            t.start(stage);
//        });

        return root;
        
    }
    
    public void refreshList(){
        userList.setAll(Database.getAllUsers());
        tableView.setItems(userList);
    }
}
