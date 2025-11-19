
package pharmacy_management_system;
import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Database {
    public static final String DB_URL = "jdbc:sqlite:Medicine.db";
    
    private static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(DB_URL);
    }
    
    public static void initDB(){
        String medicineTable = "create table if not exists medicine("+
                    "medicineId integer primary key autoincrement,"+
                    "name text not null,"+
                    "quantity integer not null,"+
                    "price integer not null"+
                    ");";
        
        String userTable = "create table if not exists user("+
                    "userId integer primary key autoincrement,"+
                    "name text not null,"+
                    "username text not null,"+
                    "password text not null,"+
                    "phone integer,"+
                    "email text"+
                    ");";
    
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(medicineTable);
            stmt.execute(userTable);

        }catch(SQLException e){
            e.printStackTrace();
        }
        
        if(isTableEmpty("user")){
            AddAdmin();
        }
    }
    
    public static boolean isTableEmpty(String tableName) {
        // نستخدم COUNT(1) للعد، وهو أسرع من COUNT(*)
        String sql = "SELECT COUNT(1) FROM " + tableName; 

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                int rowCount = rs.getInt(1); // نحصل على قيمة العمود الأول (العد)
                return rowCount == 0; // نرجع true إذا كان العدد يساوي صفر
            }
        
        } catch (SQLException e) {
            System.err.println("Error checking table emptiness for " + tableName + ": " + e.getMessage());
            e.printStackTrace();
        }
        // إذا حدث خطأ، نفترض أنه ليس فارغاً كإجراء أمان
        return false;
    }
    
    public static void AddAdmin(){
        insertNewUser("admin", "admin", "admin1234", 0, null);
    }
    
    public static ObservableList<User> getAllUsers(){
        ObservableList<User>  list = FXCollections.observableArrayList();
        String sql = "select userId,name,username,phone,email from user order by userId ";
        try(Connection conn = getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)){
            while(rs.next()){
                int id = rs.getInt("userId");
                String name = rs.getString("name");
                String username = rs.getString("username");
                int phone = rs.getInt("phone");
                String email = rs.getString("email");
                list.add(new User(id,name,username,phone,email));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return list;
    }
    
    public static ObservableList<Medicine> getAllMedicines(){
        ObservableList<Medicine> list = FXCollections.observableArrayList();
        String sql = "Select medicineId,name,quantity,price From medicine order by medicineId";
        
        try(Connection conn = getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)){
            
            while(rs.next()){
                int id = rs.getInt("medicineId");
                String name = rs.getString("name");
                int quantity = rs.getInt("quantity");
                int price = rs.getInt("price");
                list.add(new Medicine(id,name,price,quantity));
            }
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        return list;
    }
    
    public static void updateUser(int id, String name, String username, String password, int phone, String email) {
        String sql = "UPDATE user SET name = ?, username = ?, password = ?, phone = ?, email = ? WHERE userId = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, username);
            ps.setString(3, password);
            ps.setInt(4, phone);
            ps.setString(5, email);
            ps.setInt(6, id); // نستخدم الـ ID في جملة WHERE

            ps.executeUpdate();
        
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void updateMedicine(int id, String name, int quantity, int price) {
        String sql = "UPDATE medicine SET name = ?, quantity = ?, price = ? WHERE medicineId = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setInt(2, quantity);
            ps.setInt(3, price);
            ps.setInt(4, id); // نستخدم الـ ID في جملة WHERE
        
            ps.executeUpdate();
        
        } catch (SQLException e) {
            e.printStackTrace();
        }
}
    
    public static ObservableList<Medicine> searchMedicine(String txt){
        
        ObservableList<Medicine> list = FXCollections.observableArrayList();
        String sql = "Select medicineId,name,quantity,price From medicine where name like ? order by medicineId";
        
        try(Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
                
            ps.setString(1,"%" + txt + "%");
            
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    int id = rs.getInt("medicineId");
                    String name = rs.getString("name");
                    int quantity = rs.getInt("quantity");
                    int price = rs.getInt("price");
                    list.add(new Medicine(id,name,price,quantity));
            }
            }    
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        return list;
        
    }
    
    public static void insertNewUser(String name,String username,String password,int phone,String email){
        String sql = "insert into user(name,username,password,phone,email) values(?,?,?,?,?)";
        
        try(Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)){
                    ps.setString(1,name);
                    ps.setString(2,username);
                    ps.setString(3,password);
                    ps.setInt(4, phone);
                    ps.setString(5,email);
                    ps.executeUpdate();
                
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public static void insertNewMedicine(String name,int quantity,int price){
        String sql = "insert into medicine(name,quantity,price) values(?,?,?)";
        
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1,name);
            ps.setInt(2, quantity);
            ps.setInt(3,price);
            ps.executeUpdate();
            
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public static void DeleteUser(int id){
        String sql = "delete from user where userId = ?";
        try(Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1,id);
            ps.executeUpdate();
            
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public static void DeleteMedicine(int id){
        String sql = "Delete from medicine where medicineId = ?";
        
        try(Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1,id);
            ps.executeUpdate();
            
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public static User authenticateUser(String username,String password){
        
        User user = null;
        
        String sql = "select userId,name,username,phone,email,password from user where username = ?";
        
        try(Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)){
            
            ps.setString(1,username);
            
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    int id = rs.getInt("userId");
                    String name = rs.getString("name");
                    String dbUsername = rs.getString("username");
                    String dbPassword = rs.getString("password");
                    int phone = rs.getInt("phone");
                    String email = rs.getString("email");
                    
                    if(dbPassword.equals(password)){
                        user = new User(id,name,dbUsername,phone,email);
                    }
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        return user;
    }
}
