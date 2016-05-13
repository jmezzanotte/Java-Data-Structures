

import java.util.Iterator;
import java.util.Scanner;

/**
 * IntTreeBag application for project 5 on Binary Tress. Uses IntTreeBag and 
 * IntBTNode classes.
 * @author JohnMezzanotte
 * Contact: jmezzan1@my.smccd.edu
 * TextEditor.java
 * CIS 256 JAVA
 * Date: 4-12-2016
 * @Notes I chose created an iterator for extra credit. I output the bag 
 * in two ways in this application: once using the iterator which prints 
 * the contents of the bag with no indentation and once using indentation. 
 * The user has a choice to print using either method.
 */
public class TreeBagApp {
    
    private static Scanner stdin = new Scanner(System.in);
    private static boolean activeSession = true;
    private static IntTreeBag userBag = new IntTreeBag();
    private static IntTreeBag combinedBag = new IntTreeBag();
    private static final String INT_ERROR = "Please enter a valid integer.";
    
    public static void main(String[] args){
        
        final String CONTINUE_PROMPT = "Enter y for yes or n for no. ";
        
        String answer = "";
        
        while(activeSession){
            try{
                instruct(); 
                answer = query(">> ");
                switchboard(answer);
            }catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
                System.out.println("Do you wish to continue playing?");
                answer = query(CONTINUE_PROMPT);
                
                while(!answer.startsWith("y") && !answer.startsWith("n") ){
                    System.out.println(answer);
                    System.out.println("Invalid response. Type y or n: ");
                    answer = query(CONTINUE_PROMPT);
                                   
                }
                
                if(answer.equals("n")){
                    activeSession = false; 
                }
            }
        }
        
        System.out.println("Thanks for playing. Goodbye!");
    }
    
    /**
     * Private helper method to display a set of instructions to the user
     * @precondition none
     * @postcondition No objects are modified. A set of application 
     * instructions are printed to the screen.
     */
    private static void instruct(){
       
        final String INSTRUCTIONS = 
                "--------------------------------------------------------\n" + 
                "Choose any one of the following actions:\n" + 
                "--------------------------------------------------------\n" + 
                "'a'  -- Add a value to the bag\n" + 
                "'r'  -- Remove a value from the bag\n" + 
                "'n'  -- Output the number of values stored in the bag\n" + 
                "'am' -- Add contents of one bag to another\n" + 
                "'c'  -- Combine the bag with another bag\n" + 
                "          enter 'c' infront of the 'p', 'pi', 'f', and 'n'\n" + 
                "          commands to access this information for the\n" + 
                "          new combined bag.\n" +
                "'f'  -- Find number of occurrences\n" + 
                "'p'  -- Print bag contents to screen\n" + 
                "'pi' -- Print bag contents with indendation\n" +
                "--------------------------------------------------------\n" + 
                "Enter 'q' to quit\n" +
                "--------------------------------------------------------\n";
        
        System.out.println(INSTRUCTIONS);
                     
    }
    
    /**
     * Processes and returns the user's response to the menu items. 
     * @param prompt A prompt to printed to the screen. 
     * @return returns user's response controlled for case
     */
    private static String query(String prompt){
        
        String response; 
        System.out.print(prompt);
        response = stdin.nextLine().toLowerCase();
        
        return response;
        
    }
    
    private static void switchboard(String cmd) {
        
        
        final String N_ELEMENTS_MSG = "Number of elements stored in bag :";
        String response = "";
        switch(cmd){
            case "a" : 
                
                addToBag(userBag);
    
                break; 
                
            case "r" :
                
                response = query( "Enter an integer to remove from " + 
                        "the bag: " );
                try{
                    int numericResponse = Integer.parseInt( response ); 
                    userBag.remove( numericResponse ); 
                }catch ( IllegalArgumentException e ){
                    throw new IllegalArgumentException( "Nothing has been " + 
                            "removed from this bag.\n" + INT_ERROR );
                }
                
                break;
                
            case "n" :
                System.out.println( N_ELEMENTS_MSG + " " + userBag.size() );
                break;
            case "cn" : 
                System.out.println(N_ELEMENTS_MSG + " " + combinedBag.size());
                break;
            case "f" : 
                try{
                    findOccurrences(userBag);
                }catch ( IllegalArgumentException e ){
                    throw new IllegalArgumentException(INT_ERROR);
                }
          
                break; 
            case "cf" : 
                
                try{
                    findOccurrences( combinedBag ); 
                }catch ( IllegalArgumentException e ){
                    throw new IllegalArgumentException( INT_ERROR );
                }
                
                break;
                
            case "am": 
                
                IntTreeBag secondaryBag = new IntTreeBag(); 
                
                System.out.println("Enter integers for new bag.");
                
                addToBag( secondaryBag ); 
                
                System.out.println("Contents of new bag:\n");
                secondaryBag.print();
                
                System.out.println("Adding contents of new bag to current bag\n");
                
                userBag.addAll( secondaryBag );
                
                break;
            case "c" : 
                
                // This will be the third bag
               
                IntTreeBag temp = new IntTreeBag(); 
                
                // popupate the temporay bag 
                System.out.println("Add integers to new bag.\n" + 
                        "We will combine this bag with your original " + 
                        "bag to produce a new bag.Remember, you can\n"  + 
                        "access information from this new combined bag\n " + 
                        "by entering 'c' infront of select commands");
                
                addToBag(temp); 
                
                // combine temp and userBag into combinedBag
                combinedBag = IntTreeBag.union(userBag, temp);
                
                System.out.println("New bag successfully created\n" + 
                        "enter 'cp' at anytime to print this new bag to " + 
                        "screen.");
                
                break;
                
            case "pi" : 
                System.out.print("Current Bag:\n");
                userBag.print();
                System.out.println();
                break;
            case "p" : 
                // print contents using our iterator 
                printWithIterator(userBag);
                break;
            case "cpi" : 
                System.out.println( "Combined Bag:\n" );
                combinedBag.print();
                System.out.println(); 
                break;
            case "cp" :
                printWithIterator(combinedBag);
                break;
            case "q" : 
                activeSession = false; 
                break;
            default: 
                throw new IllegalArgumentException( "Command not recognized" );
                
               
        }    
    }
    

    private static void addToBag(IntTreeBag itb){
        
        String response = "";
        boolean keepAdding = true;
        
        while(keepAdding){
            response = query("Add integer to bag (enter 'd' when done): ");
            
            if(response.equals("d")){
                keepAdding = false;
            }else{
                try{
                    int numericResponse = Integer.parseInt(response); 
                    //userBag.remove(numericResponse);
                    itb.add(numericResponse);
                }catch (IllegalArgumentException e){
                    System.out.println("Entry was not added to the bag.\n"
                            + INT_ERROR);
                }
            }
          
        }

            
    }
    
    private static void findOccurrences(IntTreeBag itb) 
            throws IllegalArgumentException{
        String response = query( "Enter an integer to find in the bag: " );
        
        int numericResponse = Integer.parseInt(response); 
        System.out.printf( "Number of occurrences: %d\n\n", 
                itb.countOccurrences(numericResponse));
  
    }
    
    /**
     * Helper method to allow printing through use of IntTreeBag's iterator
     * @param itb 
     */
    private static void printWithIterator(IntTreeBag itb){
        Iterator it = itb.iterator();
        
        while( it.hasNext() ){
            IntBTNode printNode = ( IntBTNode ) it.next();
            System.out.println( printNode.getData() );
        }
    }
}
