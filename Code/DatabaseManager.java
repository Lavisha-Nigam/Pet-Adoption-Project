
/**
 * Driver Class 
 *
 * @author Lavisha Nigam
 * @version Project Work
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class DatabaseManager
{
    private static final String DB_URL = "jdbc:sqlite:PetConnect.db" ;
    
    //Crreating tables if the file is empty
    public static void createTables() {
        String sql = "CREATE TABLE IF NOT EXISTS Pets (" + "Pet_ID INTEGER PRIMARY KEY, " +
        "Name TEXT, Species TEXT, Breed TEXT, Age INTEGER, Status TEXT);";
        try (Connection conn = DriverManager.getConnection(DB_URL);
        Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println ("Tables checked/created." );
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void showAllPets() {
        String sql = "SELECT * FROM Pets";
        
        try(Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()){
           
                 ResultSet rs = stmt.executeQuery(sql);
                 
                 while (rs.next()) {
                     System.out.println(rs.getString("Name"));
                    } 
                } catch (SQLException e) {
                     e.printStackTrace();
                 }
                }          
    
    // Method to Add a new pet (Insert)
    public static void addPet(String name, String species, String breed, int age, String status){
        String sql = "INSERT INTO Pets( Name, Species, Breed, Age, Status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, species);
            pstmt.setString(3, breed);
            pstmt.setInt(4, age);
            pstmt.setString(5, status);
            pstmt.executeUpdate();
            System.out.println(name + " added successfully!");
            
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        
    }  
    //Method to update a pet's status (Update)
    public static void updatePetStatus(int id, String newStatus) {
        String sql = "UPDATE Pets SET Status = ? WHERE Pet_ID = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
             pstmt.setString(1, newStatus);
              pstmt.setInt(2, id);
               pstmt.executeUpdate();
               System.out.println("Pet_ID " + id + " updated.");
        
    } catch (SQLException e ) {
        e.printStackTrace();
    }
}
    public static void deletePet(int id) {
        String sql = "DELETE FROM Pets WHERE Pet_ID = ?";
         try (Connection conn = DriverManager.getConnection(DB_URL);
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
             pstmt.setInt(1, id); //This targets the specific id number 
             pstmt.executeUpdate();
             System.out.println("Pet_ID " + id + " deleted successfully!");
             
            }catch (SQLException e) {
                e.printStackTrace();
            }
    }
    public static void main(String[]args) {
        createTables(); //checking the schema
        deletePet(2);
        addPet("Lily", "Cat", "Persian", 2, "Available"); //Inserting a new pet
        showAllPets(); // Show the schema results (Select)
    }
}
    
   