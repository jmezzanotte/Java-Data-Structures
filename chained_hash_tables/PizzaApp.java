

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Chained Hash Table Application for project 7 on Searching implementation of a 
 * Chained Hash Table. 
 * @author JohnMezzanotte
 * Contact: jmezzan1@my.smccd.edu
 * PizzaApp.java
 * CIS 256 JAVA
 * Date: 5-12-2016
 * @Notes Chose to create a custom exception for invalid phone numbers.The 
 * exception is called InvalidPhoneNumberException
 */
public class PizzaApp {
    
    private static final String MENU =
                "--------------------------------\n" + 
                "       CHOOSE AND ACTION        \n" + 
                "--------------------------------\n" +
                "'a' - ADD A CUSTOMER RECORD\n" + 
                "'r' - RETRIEVE A CUSTOMER RECORD\n" + 
                "'d' - DELETE A CUSTOMER RECORD\n" + 
                "--------------------------------\n" + 
                "  SELECT ANY OTHER KEY TO EXIT  \n" + 
                "--------------------------------\n";
 
    public static Scanner stdin = new Scanner( System.in );
    public static boolean activeSession = true;
    public static final String OUTRO_MESSAGE = "Goodbye!";
    public static final String INVALID_PHONE = "Phone number is invalid." + 
            "Phone number must by 10 digits and contain no letters.";
    public static final String NO_RECORD = "NO RECORD FOUND";
    public static final String RECORD_FOUND = "RECORD FOUND";
    public static final String RECORD_DELETED ="RECORD DELETED";
    public static final String RECORD_ADDED = "CUSTOMER ADDED";
    public static final String HEADER =   
                "--------------------------------\n" + 
                "         %s                     \n" + 
                "--------------------------------\n" ;
    public static final String LOOKUP_PROMPT = "Enter the customer's " + 
            "phone number>> ";
    
    public static ChainedTable<String, Customer> storage = 
            new ChainedTable<>( 53 ); // chose a prime number
   
    public static void main( String[] args ){
           
        while( activeSession ){
            
            System.out.print( MENU );
            System.out.print(">> ");
            switchBoard( stdin.nextLine() );
            
        }
        
        System.out.println( OUTRO_MESSAGE );
        
    }
    
    /**
     * Main router of the application. This function takes input from the 
     * user and routes it to the appropriate processing funcition.
     * @param input 
     */
    private static void switchBoard( String input ){
        
        switch( input ){
            case "a" : 
               
                addCustomer();
              
                break;
            
            case "r" :
                
                Customer record = getRecord();
                
                if( record == null ){
                    System.out.printf(HEADER, NO_RECORD );
                }else{
                    System.out.printf(HEADER, RECORD_FOUND );
                    System.out.println( record );
                }
             
                break;
                
            case "d" :
                
                Customer droppedRecord = deleteRecord();
                
                if( droppedRecord == null ){
                    System.out.printf( HEADER, NO_RECORD );
                }else{
                    System.out.printf( HEADER, RECORD_DELETED );
                    System.out.println( droppedRecord );  
                }
                
                break;
                
            default :
                activeSession = false;
                break;
        }
        
    }
    
    /**
     * Helper function to validate phone numbers input by user. Ensures 
     * phone numbers only contain digits, strips out all special characters.
     * @param prompt
     * @return returns String representation of phone number.
     */
    private static String validatePhoneNumber( String prompt ){
        
        boolean correct = false;
        String num = null;
        String pattern = "^(?>\\(\\d{3}\\) ?|\\d{3}[.-]?)?\\d{3}[.-]?\\d{4}$";
        String replacePattern = "[^\\d]"; // replace if not a digit
        
        while( !correct ){
            try{
                System.out.println( prompt );
                num = stdin.nextLine().trim();
                
                // Check for valid phone number
                if( !( num.matches( pattern ) ) ){
                    throw new InvalidPhoneNumberException( INVALID_PHONE );
                }
                
                //Strip out the special characters
                num = num.replaceAll( replacePattern, "" );
               
                correct = true;
                
            }catch ( InvalidPhoneNumberException e ){
                System.out.println( e.getMessage() );
            }// end try
        }// end while
        
        return num;
    }
    
    /**
     * Helper function to validate String input. Determines whether or not 
     * a string has been entered with all blank spaces.
     * @param str
     * @return returns true if string is blank, false otherwise.
     */
    private static boolean isBlank( String str ){
        
        return str.length() == 0;
      
    }
    
    /**
     * Helper function to validate string input. Checks to see if input is 
     * blank.
     * @param prompt
     * @return returns validated input from user as string.
     */
    private static String validateString( String prompt ){
        
        boolean correct = false;
        
        String input = null;
        
        while( !correct ){
            try{
                System.out.println( prompt );
                
                input = stdin.nextLine().trim();
                
                if( isBlank( input ) || ( onlySpaces( input ))){
                    throw new IllegalArgumentException();
                }
                
                correct = true;
         
            }catch ( IllegalArgumentException e ){
                System.out.println( "Invalid Input." );
            }// end try
        }// end while
        
        return input;
    }

    /**
     * Helper function to determine if String input has all spaces.
     * @param str
     * @return returns true if input contains all spaces, false otherwise.
     */
    private static boolean onlySpaces( String str ){
        
        boolean answer = true; 
        
        for( char c : str.toCharArray() ){
            if( !Character.isWhitespace( c ) ){
                answer = false; 
            }
        }
        
        return answer;
    }

    /**
     * Method to add customer to the hash table.
     */
    private static void addCustomer(  ){
        
        String phone = validatePhoneNumber( "Customer phone number >> " );
        String name = validateString( "Customer name >> " );
        String address = validateString( "Customer address" );
        
        try{
            Customer newCustomer = new Customer( name, address, phone );
            storage.put(phone, newCustomer );
            System.out.printf( HEADER, RECORD_ADDED);
            System.out.println( newCustomer ); 
        }catch( NullPointerException e ){
            System.out.println( e.getMessage() );
        }
        
    }
    
    /**
     * Method to retrieve customer from hash table.
     * @return returns Customer record.
     */
    private static Customer getRecord(){
        String key = validatePhoneNumber( LOOKUP_PROMPT );
        return storage.get( key );
        
    }
    
    /**
     * Method to delete customer record from hash table.
     * @return returns deleted customer record.
     */
    private static Customer deleteRecord(){
        
        String key = validatePhoneNumber( LOOKUP_PROMPT );
        
        return storage.remove( key );

    }
    
   
    
}
