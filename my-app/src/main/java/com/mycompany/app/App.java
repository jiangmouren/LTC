package com.mycompany.app;

/**
 * Hello world!
 *
 */
public class App 
{
    public String concatAndUpperString(String str1, String str2){
        String concatedString=str1.concat(str2);
        return concatedString.toUpperCase();
    }
    //public static void main( String[] args )
    //{
    //    System.out.println( "Hello World!" );
    //}

    public String concatAndLowerString(String str1, String str2){
        String concatedString=str1.concat(str2);
        return concatedString.toLowerCase();
    }
}