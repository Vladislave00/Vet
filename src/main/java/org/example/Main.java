package org.example;

import java.sql.*;
public class Main {
    public static void main(String[] args){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://std-mysql.ist.mospolytech.ru:3306/std_2294_type",
                    "std_2294_type" , "vladikkk848" );
            Statement statement = connection.createStatement();
            String query = " SELECT brand FROM Car WHERE id = 2" ;
            ResultSet result = statement.executeQuery(query);
            while(result.next()){
                String brand = result.getString("brand");
                System.out.println(brand);
//                int id = result.getInt("id" );
//                String name = result.getString("name" );
////                String short_name = result.getString(" short_name " );
////                System.out.print(" Vacant post: " );
//                System.out.print(" id = " + id);
//                System.out.print(" , name = \" " + name + " \" " );
//                System.out.println(" , short name = \" " + short_name + " \" . " );
            }
            connection.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}