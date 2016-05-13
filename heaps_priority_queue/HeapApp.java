

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Max Heap Application for project 6 on a heap implementation of a 
 * Priority Queue. Uses the Heap class as our data structure.
 * @author JohnMezzanotte
 * Contact: jmezzan1@my.smccd.edu
 * HeapApp.java
 * CIS 256 JAVA
 * Date: 4-25-2016
 * @Notes Pretty straight forward application. I chose to write helper 
 * functions to prohibit a user from entering any numeric digits, strings 
 * consisting of only spaces, and blank entries into the 
 * heap. Technically, the program would run with the preceeding input, but I 
 * wanted to keep things clean, and have only alpha characters. 
 */

public class HeapApp {
    
    public static Scanner stdin = new Scanner( System.in );
    
    public static void main( String[] args ){
        
        final String ENTRY_PROMPT = "Enter word %d: >> ";
        final String INVALID_PROMPT = "Invalid input. You entered a word " + 
                "that contains numeric digits or you did not input a response.";
        final String FMT_LINE = "-------------------------------------------" + 
                "-------------";
        boolean activeSession = true;
        
        while( activeSession ){

            int size = validateIntInput( "How many names do you wish to " +
                    "alphabetize?" );

            Heap<String> alphaHeap = new Heap<>( size );

            for( int i = 0; i < size; i++ ){

                System.out.printf( ENTRY_PROMPT, i + 1 );
                String nextElement = stdin.nextLine();

                while( isNumeric( nextElement ) || isBlank( nextElement ) || 
                        onlySpaceChars( nextElement ) ){
                    System.out.println( INVALID_PROMPT);
                    System.out.printf( ENTRY_PROMPT, i + 1 );
                    nextElement = stdin.nextLine();
                    
                }

                alphaHeap.add( nextElement.toLowerCase().trim() );

            }

            System.out.println( FMT_LINE );
            System.out.println( "Here is your input sorted in reverse " + 
                    "alphabetical order:");
            System.out.println( FMT_LINE );

            int countLabel = 0;

            while( !alphaHeap.isEmpty() ){
                countLabel++;
                System.out.printf("%d.)%s\n", countLabel, alphaHeap.remove() );     
            }

            activeSession = continueRunning();
        }
        
        System.out.println( "Goodbye" );
       
    }
    
    public static int validateIntInput( String prompt ){
        
        boolean correct = false;
        int num = 0;
        // ensure valid integer is given to size the heap.
        while( !correct ){
            try{
                System.out.println( prompt );
                num = stdin.nextInt();
                stdin.nextLine(); // have to clear the Scanner after enter int
                correct = true;
            }catch ( InputMismatchException  e ) {
                System.out.println( "Please enter an integer." );
                stdin.next(); // clears the Scanner
            } // end try
        }// end while
        
        return num;
    }
    

    /**
     * Helper function to ensure that string is non-numeric
     * @param str
     * @return returns true if string is numeric, false otherwise.
     */
    public static boolean isNumeric(String str){
        
        boolean answer = false;
        for ( char c : str.toCharArray() ){
            
            if ( Character.isDigit( c ) ) {
                answer =true;
            }  
        }
        
        return answer;
    }
    
    private static boolean isBlank( String str ){
        
        return str.length() == 0;
      
    }
    
    private static boolean onlySpaceChars( String str ){
        
        boolean answer = true; 
        
        for( char c : str.toCharArray() ){
            if( !Character.isWhitespace( c ) ){
                answer = false; 
            }
        }
        
        return answer;
    }
    
    private static boolean continueRunning(){
        
        boolean answer = false;
        
        System.out.println( "Would you like to alphabetize another list?");
        System.out.println( "enter 'y' for anyother charcter to quit.");
        
        String response = stdin.nextLine().toLowerCase().trim();
        
        switch( response ){
            case "y" :
                answer = true;
                break;
            default: 
                break;
        
        }
        
        return answer;
        
    }
    
} // End HeapApp
