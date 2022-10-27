package com.example.supplychain;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {

    //hashing

    public static byte[] getSHA(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }
    static String getEncriptedPassword(String passwordText) throws NoSuchAlgorithmException {
        try{

            BigInteger number = new BigInteger(1,getSHA(passwordText));

            StringBuilder hexString = new StringBuilder(number.toString(16));

            return hexString.toString();

        }catch (Exception e){
            e.printStackTrace();
        }
      return "";
    }
    public static boolean customerLogin(String email,String password)  {
        try{
            DatabaseConnection dbcon = new DatabaseConnection();
            String encryptedPassword = getEncriptedPassword(password);
            String query = String.format("SELECT * FROM customer WHERE email = '%s' AND password = '%s' ",email,encryptedPassword);
            ResultSet rs = dbcon.getQueryTable(query);
            if(rs.next()){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        return false;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String pass = "angad123";
        System.out.println(pass);
        System.out.println(Login.getEncriptedPassword(pass));
        System.out.println();

    }

}
