
/**
 * Driver Class 
 *
 * @author Lavisha Nigam
 * @version Project Work
 */

import java.util.Scanner;
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
    // 1. Create Pets Table
    String sqlPets = "CREATE TABLE IF NOT EXISTS Pets (" +
                     "Pet_ID INTEGER PRIMARY KEY, " +
                     "Name TEXT, Species TEXT, Breed TEXT, " +
                     "Age INTEGER, Status TEXT);";

    // 2. Create Adopters Table
    String sqlAdopters = "CREATE TABLE IF NOT EXISTS Adopters (" +
                         "Adopter_ID INTEGER PRIMARY KEY, " +
                         "FirstName TEXT, LastName TEXT, " +
                         "Phone TEXT, Email TEXT);";

    // 3. Create Adoptions Table (Created last because of Foreign Keys)
    String sqlAdoptions = "CREATE TABLE IF NOT EXISTS Adoptions (" +
                          "Adoption_ID INTEGER PRIMARY KEY, " +
                          "Pet_ID INTEGER, Adopter_ID INTEGER, " +
                          "Adoption_Date TEXT, Fee REAL, " +
                          "FOREIGN KEY (Pet_ID) REFERENCES Pets(Pet_ID), " +
                          "FOREIGN KEY (Adopter_ID) REFERENCES Adopters(Adopter_ID));";

    try (Connection conn = DriverManager.getConnection(DB_URL);
         Statement stmt = conn.createStatement()) {
        
        // Execute all three commands
        stmt.execute(sqlPets);
        stmt.execute(sqlAdopters);
        stmt.execute(sqlAdoptions);
        
        System.out.println("All tables (Pets, Adopters, Adoptions) checked/created.");
        
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
    //Method to delete a pet 
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
        deletePet(9);
        addPet("Lily", "Cat", "Persian", 2, "Available"); //Inserting a new pet
        showAllPets(); // Show the schema results (Select)
    }

public static void searchAndBook() {
    Scanner input = new Scanner(System.in);
    
    System.out.println("--- Welcome to PetConnect Search ---");
    System.out.print("Enter the species you are looking for: ");
    String desiredBreed = input.nextLine();

    // Search Logic
    String searchSql = "SELECT * FROM Pets WHERE Species = ? AND Status = 'Available'";
    
    try (Connection conn = DriverManager.getConnection(DB_URL);
         PreparedStatement pstmt = conn.prepareStatement(searchSql)) {
        
        pstmt.setString(1, desiredBreed);
        ResultSet rs = pstmt.executeQuery();

        System.out.println("\nResults for " + desiredBreed + ":");
        boolean found = false;
       while (rs.next()) {
    found = true;
    int id = rs.getInt("Pet_ID");
    String name = rs.getString("Name");
    String breed = rs.getString("Breed"); // <--- Get the breed column
    
    System.out.println("ID: " + id + " | Name: " + name + " | Breed: " + breed);
    }

        if (!found) {
            System.out.println("Sorry, no " + desiredBreed + "s available right now.");
        } else {
            // Booking Logic
            System.out.print("\nEnter the ID of the pet you'd like to book a visit for: ");
            int idToBook = input.nextInt();
            
            // Re-use your updatePetStatus method here!
            updatePetStatus(idToBook, "Pending");
            System.out.println("Success! Visit booked. Pet status updated to Pending.");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
}
    
   