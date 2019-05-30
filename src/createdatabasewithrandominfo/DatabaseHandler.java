/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package createdatabasewithrandominfo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author blj0011
 */
public class DatabaseHandler {
    private Connection conn;
    
    public DatabaseHandler()
    {
        try 
        {
            conn = DriverManager.getConnection("jdbc:sqlite:RandomData.sqlite3");
            System.out.println("Connection to SQLite has been established.");
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean batchInsertIntoOneColumnTable(String tableName, String columnName, List<String> lines)
    {
        int count = 1;
        int batchSize = 10000;
            
        try (PreparedStatement pstmt = conn.prepareStatement("INSERT INTO " + tableName + "(" + columnName + ") VALUES(?)")) 
        {
            conn.setAutoCommit(false);
            for (int i = 0; i < lines.size(); i++) {
                pstmt.setString(1, lines.get(i));                    
                pstmt.addBatch();

                
                if (count % batchSize == 0 || count == lines.size()) {
                    pstmt.executeBatch();
                    conn.commit();
                }
                
                count++;
            }

            conn.setAutoCommit(true);
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        
        return true;
    }
    
    public boolean batchInsertIntoAddressTable(List<Address> addresses)
    {
        int count = 1;
        int batchSize = 10000;
            
        try (PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Address(street, city, state, zip) VALUES(?, ?, ?, ?)")) 
        {
            conn.setAutoCommit(false);
            for (int i = 0; i < addresses.size(); i++) {
                pstmt.setString(1, addresses.get(i).getStreet());   
                pstmt.setString(2, addresses.get(i).getCity());   
                pstmt.setString(3, addresses.get(i).getState());   
                pstmt.setString(4, addresses.get(i).getZip());   
                pstmt.addBatch();

                
                if (count % batchSize == 0 || count == addresses.size()) {
                    pstmt.executeBatch();
                    conn.commit();
                }
                
                count++;
            }

            conn.setAutoCommit(true);
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        
        return true;
    }
    
    public void closeConnection()
    {
        try 
        {
            conn.close();
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
            
    public static boolean createMainDatabase()
    {
        String sqlString = "CREATE TABLE Main_Data (\n" +
                            "	id	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
                            "	first_name	TEXT,\n" +
                            "	last_name	TEXT,\n" +
                            "   sex	TEXT,\n" +
                            "	phone	TEXT,\n" +
                            "	email	TEXT,\n" +
                            "	company	TEXT,\n" +
                            "	street	TEXT,\n" +
                            "	city	TEXT,\n" +
                            "	state	TEXT,\n" +
                            "	zip	TEXT\n" +
                            ");";
        
        try(Connection tempConn = DriverManager.getConnection("jdbc:sqlite:Main_Database.sqlite3");
            Statement stmt = tempConn.createStatement())
        {
            stmt.execute(sqlString);
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        return true;
    }
    
    public static boolean populateMainDatabase(List<DatabaseData> databaseDatas)
    {
        try(Connection tempConn = DriverManager.getConnection("jdbc:sqlite:Main_Database.sqlite3");
            PreparedStatement pstmt = tempConn.prepareStatement("INSERT INTO Main_Data(first_name, last_name, sex, phone, email, company, street, city, state, zip) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) 
        {
            int count = 1;
            int batchSize = 10000;
        
            tempConn.setAutoCommit(false);
            for (int i = 0; i < databaseDatas.size(); i++) {
                boolean sexControl = ThreadLocalRandom.current().nextInt() % 2 == 0 ? true : false;
                String firstName = sexControl ? databaseDatas.get(i).getBoyName() : databaseDatas.get(i).getGirlName();
                String sex = sexControl ? "Male" : "Female";
                        
                pstmt.setString(1,  firstName);   
                pstmt.setString(2, databaseDatas.get(i).getLastName());
                pstmt.setString(3, sex);
                pstmt.setString(4, databaseDatas.get(i).getPhoneNumber());   
                pstmt.setString(5, databaseDatas.get(i).getEmailAddress());   
                pstmt.setString(6, databaseDatas.get(i).getCompanyName());
                pstmt.setString(7, databaseDatas.get(i).getAddress().getState());
                pstmt.setString(8, databaseDatas.get(i).getAddress().getCity());   
                pstmt.setString(9, databaseDatas.get(i).getAddress().getState());
                pstmt.setString(10, databaseDatas.get(i).getAddress().getZip());
                pstmt.addBatch();

                
                if (count % batchSize == 0 || count == databaseDatas.size()) {
                    pstmt.executeBatch();
                    tempConn.commit();
                }
                
                count++;
            }

            tempConn.setAutoCommit(true);
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return true;
    }
    
    public static List<String> getAllFromCompanyName()
    {
        List<String> list = new ArrayList();
        
        String sqlString = "SELECT * FROM Company_Name";        
        try(Connection tempConn = DriverManager.getConnection("jdbc:sqlite:RandomData.sqlite3");
            Statement stmt = tempConn.createStatement();
            ResultSet rset = stmt.executeQuery(sqlString))
        {
            while (rset.next()) {
                list.add(rset.getString("name"));
            }
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list;
    }
    
    public static List<String> getAllFromFirstNameBoy()
    {
        List<String> list = new ArrayList();
        
        String sqlString = "SELECT * FROM First_Name_Boy";        
        try(Connection tempConn = DriverManager.getConnection("jdbc:sqlite:RandomData.sqlite3");
            Statement stmt = tempConn.createStatement();
            ResultSet rset = stmt.executeQuery(sqlString))
        {
            while (rset.next()) {
                list.add(rset.getString("name"));
            }
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list;
    }
    
    public static List<String> getAllFromFirstNameGirl()
    {
        List<String> list = new ArrayList();
        
        String sqlString = "SELECT * FROM First_Name_Girl";        
        try(Connection tempConn = DriverManager.getConnection("jdbc:sqlite:RandomData.sqlite3");
            Statement stmt = tempConn.createStatement();
            ResultSet rset = stmt.executeQuery(sqlString))
        {
            while (rset.next()) {
                list.add(rset.getString("name"));
            }
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list;
    }
   
    public static List<String> getAllFromLastName()
    {
        List<String> list = new ArrayList();
        
        String sqlString = "SELECT * FROM Last_Name";        
        try(Connection tempConn = DriverManager.getConnection("jdbc:sqlite:RandomData.sqlite3");
            Statement stmt = tempConn.createStatement();
            ResultSet rset = stmt.executeQuery(sqlString))
        {
            while (rset.next()) {
                list.add(rset.getString("name"));
            }
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list;
    }
    
    public static List<String> getAllFromEmail()
    {
        List<String> list = new ArrayList();
        
        String sqlString = "SELECT * FROM Email_Address";        
        try(Connection tempConn = DriverManager.getConnection("jdbc:sqlite:RandomData.sqlite3");
            Statement stmt = tempConn.createStatement();
            ResultSet rset = stmt.executeQuery(sqlString))
        {
            while (rset.next()) {
                list.add(rset.getString("email"));
            }
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list;
    }
    
    public static List<String> getAllFromPhoneNumber()
    {
        List<String> list = new ArrayList();
        
        String sqlString = "SELECT * FROM Phone_Number";        
        try(Connection tempConn = DriverManager.getConnection("jdbc:sqlite:RandomData.sqlite3");
            Statement stmt = tempConn.createStatement();
            ResultSet rset = stmt.executeQuery(sqlString))
        {
            while (rset.next()) {
                list.add(rset.getString("phone_number"));
            }
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list;
    }
    
    public static List<Address> getAllFromAddress()
    {
        List<Address> list = new ArrayList();
        
        String sqlString = "SELECT * FROM Address";        
        try(Connection tempConn = DriverManager.getConnection("jdbc:sqlite:RandomData.sqlite3");
            Statement stmt = tempConn.createStatement();
            ResultSet rset = stmt.executeQuery(sqlString))
        {
            while (rset.next()) {
                Address tempAddress = new Address();
                tempAddress.setStreet(rset.getString("street"));
                tempAddress.setCity(rset.getString("city"));
                tempAddress.setState(rset.getString("state"));
                tempAddress.setZip(rset.getString("zip"));
                
                list.add(tempAddress);
            }
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list;
    }
}
