/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package createdatabasewithrandominfo;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 *
 * @author blj0011
 */
public class CreateDataBaseWithRandomInfo {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try 
        {
            //useJSoup("Random Movie Generator - Watch new movies!.html");
            useJSoup("Random Celebrities.html");
            // TODO code application logic here
            /*
                   USED TO ADD DATA TO THE DATABASE!
            */
//            DatabaseHandler databaseHandler = new DatabaseHandler();            
//            databaseHandler.batchInsertIntoOneColumnTable("Email_Address", "email", Files.readAllLines(Paths.get("FakeEmailAddresses.txt")));
//            databaseHandler.batchInsertIntoOneColumnTable("Company_Name", "name", Files.readAllLines(Paths.get("CompanyNames.txt")));
//            databaseHandler.batchInsertIntoOneColumnTable("First_Name_Boy", "name", Files.readAllLines(Paths.get("MaleNames.txt")));
//            databaseHandler.batchInsertIntoOneColumnTable("First_Name_Girl", "name", Files.readAllLines(Paths.get("FemaleNames.txt")));
//            databaseHandler.batchInsertIntoOneColumnTable("Last_Name", "name", Files.readAllLines(Paths.get("LastNames.txt")));
//            databaseHandler.batchInsertIntoOneColumnTable("Phone_Number", "phone_number", Files.readAllLines(Paths.get("FakePhoneNumbers.txt")));
//            List<Address> addresses = new ArrayList();
//            Files.readAllLines(Paths.get("FakeAddresses.txt")).forEach((line) -> {
//                String[] parts = line.split("  ");
//                Address address = new Address();
//                address.setStreet(parts[0]);
//                String[] parts2 = parts[1].split(", ");
//                address.setCity(parts2[0]);
//                String[] parts3 = parts2[1].split(" ");
//                address.setState(parts3[0]);
//                address.setZip(parts3[1]);
//                
//                addresses.add(address);
//            });                 
//            databaseHandler.batchInsertIntoAddressTable(addresses);            
//            databaseHandler.closeConnection();
            
            
//            List<String> boyNames = DatabaseHandler.getAllFromFirstNameBoy();
//            List<String> girlNames = DatabaseHandler.getAllFromFirstNameGirl();
//            List<String> emails = DatabaseHandler.getAllFromEmail();
//            List<String> phoneNumbers = DatabaseHandler.getAllFromPhoneNumber();
//            List<String> lastNames = DatabaseHandler.getAllFromLastName();
//            List<String> companyNames = DatabaseHandler.getAllFromCompanyName();
//            List<Address> addresses = DatabaseHandler.getAllFromAddress();
//            
//            List<DatabaseData> databaseDatas = new ArrayList();
//            for(int i = 0; i < 1000; i++)
//            {
//                DatabaseData databaseData = new DatabaseData(addresses.get(i), companyNames.get(i), boyNames.get(i), girlNames.get(i), lastNames.get(i), phoneNumbers.get(i), emails.get(i));
//                databaseDatas.add(databaseData);
//            }
//            
//            DatabaseHandler.createMainDatabase();
//            DatabaseHandler.populateMainDatabase(databaseDatas);
        } 
        catch (Exception ex)
        {
            Logger.getLogger(CreateDataBaseWithRandomInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    static public void useJSoup(String filePath)
    {
        try 
        {
            List<CelebritiesNames> celebritiesNames = new ArrayList();
            
            Document document = Jsoup.parse(new File(filePath), "UTF-8");
            Elements list = document.select("li");
            list.forEach((item) -> {
                Elements spans = item.select("span");
                if(spans.size() > 0)
                {
                    System.out.println(spans.get(0).child(0).attr("src"));
                    System.out.println(spans.get(1).text());
                    
                    String fileName = spans.get(0).child(0).attr("src");
                    fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
                    String name = spans.get(1).text();
                    celebritiesNames.add(new CelebritiesNames(name, fileName));
                }
            });
            
            Gson gson = new Gson();
            try (FileWriter fileWriter = new FileWriter("CelebritiesNames.json")) {
                gson.toJson(celebritiesNames, fileWriter);
            }
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(CreateDataBaseWithRandomInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
