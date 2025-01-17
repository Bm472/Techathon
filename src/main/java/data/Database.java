package data;

import uk.co.robertwalters.techathon.HelloApplication;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.*;
import java.util.Random;
import java.util.Scanner;

public class Database {

    private static final String url = "jdbc:sqlserver://localhost:1433;databaseName=Techathon;trustServerCertificate=true";
    private static final String username = "SA";
    private static final String password = "<YourStrong@Passw0rd>";
    private static Connection connection;
    private static final Random rand = new Random();

    public static void main(String[] args) throws SQLException {
        initialise();
    }

    /*
        Initialise a connection to the database
     */
    public static void initialise() {
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {

        }
    }

    /*
        Verify if a username and password combination exists in the database
     */
    public static ResultSet verifyLogin(String username, String password)  {
        try {
            initialise();
            //Create statement
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Login JOIN Customer ON Login.customerID = Customer.customerID WHERE username = ? AND password = ?");

            //Add variables
            statement.setString(1, username);
            statement.setString(2, hashPassword(password));

            //Execute query
            ResultSet resultSet = statement.executeQuery();

            //If a result exists, return true for verification, false if not
            return resultSet;
        }
        catch (SQLException e) {
            return null;
        }
    }

    /*
        Set a customers progress to 0 on all courses upon registration
     */
    public static boolean initialiseProgress(int customerID)  {
        try {
            initialise();
            //Create statement
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Progress (customerID,courseID,value) VALUES (?, 1,0)");

            //Add variables
            statement.setInt(1, customerID);

            //Execute query
            statement.execute();

            //Create statement
            statement = connection.prepareStatement("INSERT INTO Progress (customerID,courseID,value) VALUES (?, 2,0)");

            //Add variables
            statement.setInt(1, customerID);

            //Execute query
            statement.execute();

            //Create statement
            statement = connection.prepareStatement("INSERT INTO Progress (customerID,courseID,value) VALUES (?, 3,0)");

            //Add variables
            statement.setInt(1, customerID);

            //Execute query
            statement.execute();

            //Create statement
            statement = connection.prepareStatement("INSERT INTO Progress (customerID,courseID,value) VALUES (?, 4,0)");

            //Add variables
            statement.setInt(1, customerID);

            //Execute query
            statement.execute();

            //If a result exists, return true for verification, false if not
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /*
        Verify if a username exists in the database
     */
    public static boolean usernameExists(String username)  {
        try {
            initialise();
            //Create statement
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Login WHERE username = ?");

            //Add variables
            statement.setString(1, username);

            //Execute query
            ResultSet resultSet = statement.executeQuery();

            //If a result exists, return true for verification, false if not
            return resultSet.next();
        }
        catch (SQLException e) {
            return false;
        }
    }

    /*
        Add a customer to the Customer table (dob is in format 'YYYY/MM/DD')
     */
    public static ResultSet addCustomer(String firstname, String lastname, Date dob, String username, String password)  {
        try {
            initialise();
            //Create statement
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Customer (firstName, lastName, dob) VALUES (?,?,?); SELECT SCOPE_IDENTITY() as ID;");

            //Add variables
            statement.setString(1, firstname);
            statement.setString(2, lastname);
            statement.setDate(3, dob);

            //Execute query
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            statement = connection.prepareStatement("INSERT INTO Login (customerID, username, password) VALUES (?,?,?);");
            statement.setInt(1,resultSet.getInt("ID"));
            statement.setString(2, username);
            statement.setString(3, hashPassword(password));
            statement.executeUpdate();

            initialiseProgress(resultSet.getInt("ID"));

            return resultSet;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
      Retrieve Course Details
   */
    public static ResultSet viewCourse(int courseID)  {
        try {
            initialise();
            //Create statement
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Course WHERE courseID = ?");

            //Add variables
            statement.setInt(1, courseID);

            //Execute query
            ResultSet resultSet = statement.executeQuery();

            //If successful, return true
            return resultSet;
        }
        catch (SQLException e) {
            return null;
        }
    }

    /*
      Retrieve Progress Details
   */
    public static ResultSet viewProgress(int customerID)  {
        try {
            initialise();
            //Create statement
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Progress p JOIN Customer c ON p.CustomerID = c.customerID JOIN Course co ON p.courseID = co.courseID WHERE p.customerID = ?");

            //Add variables
            statement.setInt(1, customerID);

            //Execute query
            ResultSet resultSet = statement.executeQuery();

            //If successful, return true
            return resultSet;
        }
        catch (SQLException e) {
            return null;
        }
    }

    /*
     Retrieve Progress Details
  */
    public static boolean updateProgress(int customerID, int courseID,int newValue)  {
        try {
            initialise();
            //Create statement
            PreparedStatement statement = connection.prepareStatement("UPDATE Progress SET value = ? WHERE customerID = ? AND courseID = ?");

            //Add variables
            statement.setInt(1, newValue);
            statement.setInt(2, customerID);
            statement.setInt(3, courseID);


            //Execute query
            ResultSet resultSet = statement.executeQuery();

            //If successful, return true
            return true;
        }
        catch (SQLException e) {
            return false;
        }
    }

    /*
      Retrieve Progress Details
   */
    public static ResultSet viewCourses(int customerID)  {
        try {
            initialise();
            //Create statement
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Progress WHERE customerID = ?");

            //Add variables
            statement.setInt(1, customerID);

            //Execute query
            ResultSet resultSet = statement.executeQuery();

            //If successful, return true
            return resultSet;
        }
        catch (SQLException e) {
            return null;
        }
    }

    /*
      View a customers details
   */
    public static ResultSet viewCustomer(int customerID)  {
        try {
            //Create statement
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Customer WHERE customerID = ?");

            //Add variables
            statement.setInt(1, customerID);

            //Execute query
            ResultSet resultSet = statement.executeQuery();

            //If successful, return true
            return resultSet;
        }
        catch (SQLException e) {
            return null;
        }
    }

    /*
        Delete a customer from the database
     */
    public static boolean deleteCustomer(int customerID)  {
        try {
            Scanner input = new Scanner(System.in);
            //Create statement
            PreparedStatement statement = connection.prepareStatement("DELETE FROM Customer WHERE customerID = ?");

            //Add variables
            statement.setInt(1, customerID);

            System.out.println("Are you sure you want to delete customer with customerID of " + customerID + "? (y/n)");
            String userInput = input.nextLine();
            if (userInput.equals("y")) {
                return statement.execute();
            }
            else {
                statement.close();
                System.out.println("Deletion aborted");
                return false;
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /*
        Hash a string input using SHA-256
     */
    private static String hashPassword(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encodedhash);
        }
        catch (Exception e) {
            return null;
        }
    }

    /*
        Turn a byte array into a hex string - support for hash method
     */
    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static boolean customerExists(int customerID) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM Customer WHERE customerID = ?");
            statement.setInt(1, customerID);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            System.out.println("SQL Exception in customerExists: " + e.getMessage());
            return false;
        }
    }


}