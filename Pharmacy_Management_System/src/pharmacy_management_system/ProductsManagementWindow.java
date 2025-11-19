/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pharmacy_management_system;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author hp
 */
public class ProductsManagementWindow {
    
    private ObservableList<Medicine> medicineList;
    private TableView<Medicine> tableView;
    
    
    public VBox getView() {
        Database.initDB();
        
        Button addBtn = new Button ("add Product");
        Button editBtn = new Button("Edit Product"); // 🚨 زر جديد
        Button deleteBtn = new Button ("delete Product");
        Button searchBtn = new Button ("Search");
        Button toUserWindowBtn = new Button("UserDashBoard");
        TextField searchTxt = new TextField();
        searchTxt.setPromptText("Type here");
        
        
        tableView = new TableView<>();
        // 3. إنشاء الأعمدة (الاسم الذي يظهر في رأس العمود)
        TableColumn<Medicine, Integer> idCol = new TableColumn<>("ID");
        TableColumn<Medicine, String> nameCol = new TableColumn<>("Name");
        TableColumn<Medicine, Integer> quantityCol = new TableColumn<>("Quantity");
        TableColumn<Medicine, Integer> priceCol = new TableColumn<>("Price");
        
        // 4. ربط الأعمدة بالمتغيرات في كلاس Medicine
        // ملاحظة: "name" تعني أنه سيبحث عن دالة getName()
        idCol.setCellValueFactory(cellDate -> new SimpleObjectProperty<>(cellDate.getValue().getId()));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        // إضافة الأعمدة للجدول
        tableView.getColumns().addAll(idCol, nameCol, quantityCol, priceCol);
        medicineList = FXCollections.observableArrayList();
        refreshList();
        HBox searchBox = new HBox(15,searchTxt,searchBtn);
        HBox btnBox = new HBox(15,addBtn,editBtn,deleteBtn);
        VBox root = new VBox(20,searchBox,tableView,btnBox,toUserWindowBtn);
        
        root.setPadding(new Insets(30));
        
//        Scene scene = new Scene(root,900,900);
//        primaryStage.setScene(scene);
//        primaryStage.setTitle("Pharmacy Management System");
//        primaryStage.show();
        
        addBtn.setOnAction(e -> {
            AddMedicineWindow add = new AddMedicineWindow();
            add.display();
            refreshList();
        });
        
        editBtn.setOnAction(e -> {
            Medicine selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                EditMedicineWindow edit = new EditMedicineWindow();
                edit.display(selected); // نمرر كائن الدواء المحدد
                refreshList(); // تحديث القائمة بعد العودة من نافذة التعديل
            } else {
                System.out.println("Error: Please select a product to edit.");
            }
        });
        
        deleteBtn.setOnAction(e->{
            Medicine selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                Database.DeleteMedicine(selected.getId());
                refreshList();
            }
        });
        
        searchBtn.setOnAction(e->{
            ObservableList<Medicine> searchResult = Database.searchMedicine(searchTxt.getText());
            tableView.setItems(searchResult);
        });
        
//        toUserWindowBtn.setOnAction(e->{
//            UserWindow uw = new UserWindow();
//            uw.display();
//            primaryStage.close();
//        });
        
        return root;
    }
    
    
    private void refreshList() {
        medicineList.setAll(Database.getAllMedicines());
        tableView.setItems(medicineList);
    }
}
