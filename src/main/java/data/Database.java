package data;

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
    public static boolean verifyLogin(String username, String password)  {
        try {
            //Create statement
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Login WHERE username = ? AND password = ?");

            //Add variables
            statement.setString(1, username);
            statement.setString(2, hashPassword(password));

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
    public static boolean addCustomer(String firstname, String lastname, String dob, String address, boolean isPhotoIDVerified, boolean isAddressIDVerified)  {
        try {
            //Create statement
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Customer (firstName, lastName, dob, address, photoIDVerified, addressIDVerified) VALUES (?,?,?,?,?,?)");

            //Add variables
            statement.setString(1, firstname);
            statement.setString(2, lastname);
            statement.setString(3, dob);
            statement.setString(4, address);
            if(isAddressIDVerified) {
                statement.setString(5, "1");
            }
            else {
                statement.setString(5, "0");
            }
            if(isAddressIDVerified) {
                statement.setString(6, "1");
            }
            else {
                statement.setString(6, "0");
            }

            //Execute query
            statement.execute();

            //If successful, return true
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
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
        Update existing customer within the Customer table
     */
    public static boolean updateCustomer(int customerID, String firstname, String lastname, String dob, String address, boolean isPhotoIDVerified, boolean isAddressIDVerified)  {
        try {
            //Create statement
            PreparedStatement statement = connection.prepareStatement("""
                    UPDATE Customer SET
                        firstName = ?,
                        lastName = ?,
                        dob = ?,
                        address = ?,
                        photoIDVerified = ?,
                        addressIDVerified = ?
                    WHERE CustomerID = ?
                    """);

            //Add variables
            statement.setString(1, firstname);
            statement.setString(2, lastname);
            statement.setString(3, dob);
            statement.setString(4, address);
            if(isAddressIDVerified) {
                statement.setString(5, "1");
            }
            else {
                statement.setString(5, "0");
            }
            if(isAddressIDVerified) {
                statement.setString(6, "1");
            }
            else {
                statement.setString(6, "0");
            }
            statement.setInt(7, customerID);

            //If successful, return true
            return statement.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
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
        Create Personal account for customer
     */
    public static boolean createPersonalAccount(int customerID,int overdraft)  {
        try {
            //Create statement
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Personal (balance,overdraft) VALUES (0,?); SELECT SCOPE_IDENTITY() as ID;");

            //Add variables
            statement.setInt(1, overdraft);

            //Execute query
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            //Execute second query in Account table
            statement = connection.prepareStatement("INSERT INTO Account (customerID,personalID) VALUES (?,?)");
            statement.setInt(1, customerID);
            statement.setInt(2, resultSet.getInt("ID"));
            statement.execute();

            //If successful return true
            return true;

        }
        catch (SQLException e) {
            return false;
        }
    }

    /*
       Create Business account for customer
    */
    public static boolean createBusinessAccount(int customerID,int overdraft, boolean requestedBook)  {
        try {
            //Create statement
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Business (balance,overdraft,requestedBook) VALUES (0,?,?); SELECT SCOPE_IDENTITY() as ID;");

            //Add variables
            statement.setInt(1, overdraft);
            if(requestedBook) {
                statement.setString(2, "1");
            }
            else {
                statement.setString(2, "0");
            }

            //Execute query
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            //Execute second query in Account table
            statement = connection.prepareStatement("INSERT INTO Account (customerID,businessID) VALUES (?,?)");
            statement.setInt(1, customerID);
            statement.setInt(2, resultSet.getInt("ID"));
            statement.execute();

            //If successful return true
            return true;

        }
        catch (SQLException e) {
            return false;
        }
    }

    /*
       Create ISA account for customer if one doesn't already exist
    */
    public static boolean createISAAccount(int customerID, int overdraft, String type) {
        try {
            //Check if customer already has ISA account
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Account WHERE customerID = ? AND isaID IS NOT NULL");
            statement.setInt(1, customerID);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                System.out.println("Customer already has ISA account");
                return false;
            }

            //Create statement
            statement = connection.prepareStatement("INSERT INTO ISA (balance,overdraft,type) VALUES (0,?,?); SELECT SCOPE_IDENTITY() as ID;");

            //Add variables
            statement.setInt(1, overdraft);
            statement.setString(2, type);

            //Execute query
            resultSet = statement.executeQuery();
            resultSet.next();

            //Execute second query in Account table
            statement = connection.prepareStatement("INSERT INTO Account (customerID,isaID) VALUES (?,?)");
            statement.setInt(1, customerID);
            statement.setInt(2, resultSet.getInt("ID"));
            statement.execute();

            //If successful return true
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /*
        Add a loan to a business account
    */
    public static boolean addLoan(int accountID, int amount) {
        try {
            //Check if account exists
            PreparedStatement statement = connection.prepareStatement("SELECT businessID FROM Account WHERE accountID = ? AND businessID IS NOT NULL");
            statement.setInt(1, accountID);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                int businessID = resultSet.getInt("businessID");

                //Create statement
                statement = connection.prepareStatement("INSERT INTO Loan(businessID,amount) VALUES (?,?);");

                //Add variables
                statement.setInt(1, businessID);
                statement.setInt(2, amount);

                //Execute query
                statement.execute();

                //If successful return true
                return true;

            }
            else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /*
        Add a card to an account
    */
    public static boolean addDebitCard(int accountID) {
        try {
            //Check if account is ISA
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Account WHERE accountID = ? AND isaID IS NOT NULL");
            statement.setInt(1, accountID);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                return false;
            }
            else {

                //Create statement
                statement = connection.prepareStatement("INSERT INTO DebitCard(accountID,cardNumber) VALUES (?,?);");

                long cardNumber = (long)(rand.nextDouble()*10000000000000000L);

                //Add variables
                statement.setInt(1, accountID);
                statement.setLong(2, cardNumber);

                //Execute query
                statement.execute();

                //If successful return true
                return true;

            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /*
        Setup a direct debit
    */
    public static boolean setupDirectDebit(int senderAccountID, int receiverAccountID, int amount, short paymentDay) {
        try {
            //Check if account is ISA
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Account WHERE accountID = ? AND isaID IS NOT NULL");
            statement.setInt(1, senderAccountID);
            ResultSet resultSet = statement.executeQuery();

            PreparedStatement statement2 = connection.prepareStatement("SELECT * FROM Account WHERE accountID = ? AND isaID IS NOT NULL");
            statement2.setInt(1, receiverAccountID);
            ResultSet resultSet2 = statement2.executeQuery();

            if(resultSet.next() || resultSet2.next() || paymentDay > 28 || paymentDay < 1) {
                return false;
            }
            else {

                //Create statement
                statement = connection.prepareStatement("INSERT INTO DirectDebit(senderID,receiverID,amount,paymentDay) VALUES (?,?,?,?);");

                //Add variables
                statement.setInt(1, senderAccountID);
                statement.setInt(2, receiverAccountID);
                statement.setInt(3, amount);
                statement.setLong(4, paymentDay);

                //Execute query
                statement.execute();

                //If successful return true
                return true;

            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    /*
        Initiate a transfer between accounts
    */
    public static boolean transfer(int senderAccountID, int receiverAccountID, int amount) {
        try {
            boolean withdrawn = false;
            boolean deposited = false;

            if(withdrawal(senderAccountID, amount)) {
                withdrawn = true;
                if(deposit(receiverAccountID, amount)) {
                    deposited = true;
                }
            }

            if(withdrawn && deposited) {

                //Record Transaction in Transact table
                PreparedStatement statement = connection.prepareStatement("INSERT INTO Transact (senderID,receiverID,type,amount,time) VALUES (?,?,?,?,CURRENT_TIMESTAMP);");

                //Add variables
                statement.setInt(1, senderAccountID);
                statement.setInt(2, receiverAccountID);
                statement.setString(3, "Transfer");
                statement.setInt(4, amount);

                //Execute query
                statement.execute();

                //If successful return true
                return true;

            }
            else {
                return false;
            }


        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /*
        Initiate a deposit
    */
    public static boolean deposit(int accountID, int amount) {
        try {
            //Check if account is personal,business or ISA
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Account WHERE accountID = ?");
            statement.setInt(1, accountID);

            ResultSet resultSet = statement.executeQuery();

            if(!resultSet.next()) {
                return false;
            }

            int businessID = resultSet.getInt("businessID");
            int personalID = resultSet.getInt("personalID");
            int isaID = resultSet.getInt("isaID");

            //Move funds into account
            if(businessID != 0) {

                statement = connection.prepareStatement("UPDATE Business SET balance = balance + ? WHERE businessID = ?");
                statement.setInt(1, amount);
                statement.setInt(2, businessID);
                statement.execute();
            }
            else if(personalID != 0) {

                statement = connection.prepareStatement("UPDATE Personal SET balance = balance + ? WHERE personalID = ?");
                statement.setInt(1, amount);
                statement.setInt(2, personalID);
                statement.execute();
            }
            else {

                statement = connection.prepareStatement("UPDATE ISA SET balance = balance + ? WHERE isaID = ?");
                statement.setInt(1, amount);
                statement.setInt(2, isaID);
                statement.execute();
            }

            //Record Transaction in Deposit table
            statement = connection.prepareStatement("INSERT INTO Deposit (accountID,amount,time) VALUES (?,?,CURRENT_TIMESTAMP);");

            //Add variables
            statement.setInt(1, accountID);
            statement.setInt(2, amount);

            //Execute query
            statement.execute();

            //If successful return true
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /*
        Initiate a withdrawal
    */
    public static boolean withdrawal(int accountID, int amount) {
        try {

            //Check if account is personal,business or ISA
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Account WHERE accountID = ?");
            statement.setInt(1, accountID);

            ResultSet resultSet = statement.executeQuery();

            if(!resultSet.next()) {
                return false;
            }

            int businessID = resultSet.getInt("businessID");
            int personalID = resultSet.getInt("personalID");
            int isaID = resultSet.getInt("isaID");



            //Move funds into account
            if(businessID != 0) {

                statement = connection.prepareStatement("SELECT COUNT(*) as count FROM Business WHERE businessID = ? AND balance >= ?");
                statement.setInt(1, businessID);
                statement.setInt(2, amount);
                resultSet = statement.executeQuery();
                resultSet.next();
                int count = resultSet.getInt("count");
                if(count == 0) {
                    return false;
                }

                statement = connection.prepareStatement("UPDATE Business SET balance = balance - ? WHERE businessID = ? AND balance >= ?");
                statement.setInt(1, amount);
                statement.setInt(2, businessID);
                statement.setInt(3,amount);
                statement.execute();
            }
            else if(personalID != 0) {

                statement = connection.prepareStatement("SELECT COUNT(*) as count FROM Personal WHERE personalID = ? AND balance >= ?");
                statement.setInt(1, personalID);
                statement.setInt(2, amount);
                resultSet = statement.executeQuery();
                resultSet.next();
                int count = resultSet.getInt("count");
                if(count == 0) {
                    return false;
                }

                statement = connection.prepareStatement("UPDATE Personal SET balance = balance - ? WHERE personalID = ? AND balance >= ?");
                statement.setInt(1, amount);
                statement.setInt(2, personalID);
                statement.setInt(3,amount);
                statement.execute();
            }
            else {

                statement = connection.prepareStatement("SELECT COUNT(*) as count FROM ISA WHERE isaID = ? AND balance >= ?");
                statement.setInt(1, isaID);
                statement.setInt(2, amount);
                resultSet = statement.executeQuery();
                resultSet.next();
                int count = resultSet.getInt("count");
                if(count == 0) {
                    return false;
                }

                statement = connection.prepareStatement("UPDATE ISA SET balance = balance - ? WHERE isaID = ? AND balance >= ?");
                statement.setInt(1, amount);
                statement.setInt(2, isaID);
                statement.setInt(3,amount);
                statement.execute();
            }

            //Record Transaction in Withdrawal table
            statement = connection.prepareStatement("INSERT INTO Withdrawal (accountID,amount,time) VALUES (?,?,CURRENT_TIMESTAMP);");

            //Add variables
            statement.setInt(1, accountID);
            statement.setInt(2, amount);

            //Execute query
            statement.execute();

            //If successful return true
            return true;

        } catch (SQLException e) {
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

    public static String getAccountDetails(int customerID) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM Account WHERE customerID = ?");
            statement.setInt(1, customerID);
            ResultSet resultSet = statement.executeQuery();

            StringBuilder details = new StringBuilder();
            while (resultSet.next()) {
                details.append("Account ID: ").append(resultSet.getInt("accountID"))
                        .append(", Balance: ").append(resultSet.getDouble("balance"))
                        .append(", Type: ").append(resultSet.getString("type"))
                        .append("\n");
            }
            return details.length() > 0 ? details.toString() : null;
        } catch (SQLException e) {
            System.out.println("SQL Exception in getAccountDetails: " + e.getMessage());
            return null;
        }
    }

    public static boolean lookupID(int customerID) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT photoID FROM Customer WHERE customerID = ?");
            statement.setInt(1, customerID);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next() && resultSet.getString("photoID") != null;
        } catch (SQLException e) {
            System.out.println("SQL Exception in lookupID: " + e.getMessage());
            return false;
        }
    }

    public static boolean lookupAddress(int customerID) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT address FROM Customer WHERE customerID = ?");
            statement.setInt(1, customerID);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next() && resultSet.getString("address") != null;
        } catch (SQLException e) {
            System.out.println("SQL Exception in lookupAddress: " + e.getMessage());
            return false;
        }
    }

    public static void updateBalance(int customerID, double openingBalance) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE Account SET balance = ? WHERE customerID = ?");
            statement.setDouble(1, openingBalance);
            statement.setInt(2, customerID);
            statement.executeUpdate();
            System.out.println("Balance updated successfully.");
        } catch (SQLException e) {
            System.out.println("SQL Exception in updateBalance: " + e.getMessage());
        }
    }

    public static boolean businessProof(int customerID) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT businessProof FROM Customer WHERE customerID = ?");
            statement.setInt(1, customerID);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next() && resultSet.getBoolean("businessProof");
        } catch (SQLException e) {
            System.out.println("SQL Exception in businessProof: " + e.getMessage());
            return false;
        }
    }

    public static void businessType(int customerID) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT businessType FROM Customer WHERE customerID = ?");
            statement.setInt(1, customerID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                System.out.println("Business Type: " + resultSet.getString("businessType"));
            } else {
                System.out.println("No business type found for Customer ID.");
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception in businessType: " + e.getMessage());
        }
    }

}