
package project8_256;
import java.util.Scanner;
import java.util.Arrays;

/**
 * Graph Application for project 8.
 * @author JohnMezzanotte
 * Contact: jmezzan1@my.smccd.edu
 * PrerequisiteApp.java
 * CIS 256 JAVA
 * Date: 5-25-2016
 * @Notes 
 */
 
public class PrerequisiteApp {
    
    private static final String APP_OPTIONS =  
                "-----------------------------------------\n" + 
                "              SYSTEM OPTIONS             \n" + 
                "-----------------------------------------\n" +
                "'a' - ADD A NEW COURSE\n" + 
                "'p' - ADD A NEW PREREQUISITE RELATIONSHIP \n" + 
                "'r' - REMOVE A PREREQUISITE RELATIONSHIP  \n" + 
                "'i' - IS COURSE IMMEDIATE PREREQUISITE    \n" +
                "'pp'- IS THERE A PREREQUISITE PATH        \n" + 
                "'aa'- ALL AVAILABLE COURSES AFTER COURSE  \n" + 
                "'o' - OUTPUT ALL COURSES                  \n" + 
                "------------------------------------------\n" + 
                "        SELECT ANY OTHER KEY TO EXIT      \n" + 
                "------------------------------------------\n";
    public static final String HEADER =   
                "------------------------------------------\n" + 
                "              %s                          \n" + 
                "------------------------------------------\n" ;     
    private static final String INVALID_COURSE_SELECTION = 
               "----------------------------\n" +
               "  INVALID COURSE SELECTION  \n" +
               "----------------------------\n";
    private static final String PROMPT = ">>";
    private static final String ADD_PROMPT = "Please enter the name of the " + 
            "course you would like to add to the cataloge.";
    private static final String EXISTS_MESSAGE = "Course already exists.";
    private static final String EXIT_MESSAGE = "Goodbye";
    private static final String COURSE_1_ENTRY = "Enter the requisite course: ";
    private static final String COURSE_2_ENTRY = "Enter the prerequisite course: ";
    private static Scanner stdin = new Scanner(System.in);
    private static boolean activeSession = true;
    private static Graph<String> courses = new Graph(25);
    private static int manyItems = 0;
    
    
    public static void main(String[] args){

        while(activeSession){
            System.out.println(APP_OPTIONS);
            System.out.print(PROMPT);
            
            switchboard(stdin.nextLine().trim());
       
        }
        
        System.out.println(EXIT_MESSAGE);
        
    }// End main
    
    /**
     * Main router of the application. This function takes input from the 
     * user and routes it to the appropriate processing funcition.
     * @param input 
     */
    public static void switchboard(String input) {
        
        switch(input){
            
            case "a" : // add a new course
                
                String course = validateInput(ADD_PROMPT);
               
                if(!isCourse(course)){
                    courses.setLabel(manyItems, course);
                    manyItems++;
                }else{
                    System.out.println(EXISTS_MESSAGE);
                }
            
                break;
                
            case "p" : // Add new prerequisite relationship  
                
                String course1 = validateInput(COURSE_1_ENTRY).trim().toLowerCase();
                String course2 = validateInput(COURSE_2_ENTRY).trim().toLowerCase();
                
                if(coursesValid(course1, course2)){
               
                    courses.addEdge(getCourseIndex(course2),
                            getCourseIndex(course1));
                }else{
                    System.out.println(INVALID_COURSE_SELECTION);
                }
                
                break;
                
            case "r" : // remove prerequisite path 
                
                String pathSource = validateInput(COURSE_1_ENTRY).trim().toLowerCase();
                String targetSource = validateInput(COURSE_2_ENTRY).trim().toLowerCase();
                
                if(coursesValid(pathSource, targetSource)){
                    try{
                        courses.removeEdge(getCourseIndex(targetSource),
                            getCourseIndex(pathSource));
                    }catch(NullPointerException e){
                        System.out.println("Prerequiste Relationship " +
                                "does not exist");
                    }
                }else{
                    System.out.println(INVALID_COURSE_SELECTION);
                }
                
                break;
                
            case "pp" : // Is there a prerequisite path
                
                String temp1 = validateInput(COURSE_1_ENTRY).trim().toLowerCase();
                String temp2 = validateInput(COURSE_2_ENTRY).trim().toLowerCase();
                
                if(coursesValid(temp1, temp2)){
                    
                    if(courses.isPath(getCourseIndex(temp2), getCourseIndex(temp1))){
                        System.out.println("----------------------------");
                        System.out.println("    PATH AVAILABLE: TRUE    ");
                        System.out.println("----------------------------");
                    }else{
                        System.out.println("----------------------------");
                        System.out.println("    PATH AVAILABLE: FALSE    ");
                        System.out.println("----------------------------");
                    }

                }else{
                    System.out.println(INVALID_COURSE_SELECTION);
                }

                break;
                
            case "i" : // Is course immediate prerequisite
                
                String target = validateInput(COURSE_1_ENTRY).trim().toLowerCase();
                String source = validateInput(COURSE_2_ENTRY).trim().toLowerCase();
                
                // Make sure we have two valid course 
                if(coursesValid(source, target)){
                    if(courses.isEdge(getCourseIndex(source),
                            getCourseIndex(target))){
                        System.out.println("----------------------------");
                        System.out.println("IMMEDIATE PREREQUISITE: TRUE");
                        System.out.println("----------------------------");
                    }else{
                        System.out.println("----------------------------");
                        System.out.println("IMMEDIATE PREREQUISITE: FALSE");
                        System.out.println("----------------------------");
                    }
                }else{
                     System.out.println(INVALID_COURSE_SELECTION);
                }
                
                break;
                
            case "aa" :
                
                String var1 = validateInput(COURSE_1_ENTRY).trim().toLowerCase();
                String var2 = validateInput(COURSE_2_ENTRY).trim().toLowerCase();
                
                if(coursesValid(var2, var1)){
                    System.out.println("------------------------------");
                    System.out.println("ALL COURSES AFTER PREREQUISITE");
                    System.out.println("------------------------------");
                    courses.printPath(getCourseIndex(var2), getCourseIndex(var1));
                }else{
                    System.out.println(INVALID_COURSE_SELECTION);
                }
                break;
                
            case "o" : // Output all courses
                
                System.out.printf(HEADER, "COURSE CATALOG");
                for(int i = 0; i < manyItems; i++){            
                    
                    System.out.printf("%d.)%s\n", i + 1, courses.getLabel(i));
                    
                }
         
                break;
            
            default : // Exit case
                boolean exit = false;
                while(!exit){
                    String confirm = validateInput(
                        "Are you sure you want to quit?(y/n)").trim().toLowerCase();
                    if(confirm.equals("y")){
                        activeSession = false;
                        exit = true;
                    }else if(confirm.equals("n")){
                        activeSession = true;
                        exit = true;
                    }else{
                        System.out.println("Please enter 'y' for yes or " + 
                                "'n' for no");
                    }
                }   
                    
                
                break;
                
        } // End switch   
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
     * Helper function to validate string input. Checks to see if input is 
     * blank.
     * @param prompt
     * @return returns validated input from user as string.
     */
    private static String validateInput( String prompt ){
        
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
     * Helper method to determine if the course entered by the user already 
     * exists in the catalog.
     * @param courses
     * @param input
     * @return True if course exists, false otherwise.
     */
    private static boolean isCourse(String course){
        
        // First control the string, before checking for course equality
        String temp1 = course.trim().toLowerCase();
        
        for(int i = 0; i < manyItems; i++){            
            if(courses.getLabel(i) != null){
                String temp2 = courses.getLabel(i).trim().toLowerCase();
                if(temp2.equals(temp1)){
                    return true;
                }
            }
        }
        
        return false;
        
    }
    
    /**
     * 
     * @param course
     * @return index of the course in the vertex array, else returns -1  
     */
    private static int getCourseIndex(String course){
        String temp1 = course.trim().toLowerCase();
        
        for(int i = 0; i < manyItems; i++){            
            if(courses.getLabel(i) != null){
                String temp2 = courses.getLabel(i).trim().toLowerCase();
                if(temp2.equals(temp1)){
                    
                    return i;
                }// End inner If
            }// End Outer If
        } // End for
        
        return -1; 
    }  
        
    
    private static boolean coursesValid(String course1, String course2){
              
        // make sure the course are not the same
        if(course1.equals(course2)){
            return false; 
        }
                
        if( !isCourse(course1) || !isCourse(course2) ){
            return false;
        }
        
        return true;
    }
    
    
}
