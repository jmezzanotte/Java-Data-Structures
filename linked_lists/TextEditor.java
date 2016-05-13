
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter; 
import java.io.BufferedReader; 
import java.io.IOException;
import java.util.Scanner;

/**
 * Text editor application for project 3 Linked Lists. Uses StringNode and 
 * LineEditor classes.
 * @author JohnMezzanotte
 * Contact: jmezzan1@my.smccd.edu
 * TextEditor.java
 * CIS 256 JAVA
 * Date: 3-8-2016
 */
public class TextEditor {
    
    public static boolean activeSession = true; 
    public static final String PROMPT = ">> ";
    public static String fileName; 
    
    public static void main(String[] args) {
        
        Scanner stdin = new Scanner(System.in); 
        System.out.print("Please enter a file name with the extention " + 
                ".txt included:");
        
        fileName = stdin.nextLine(); 
        
        BufferedReader inputStream = null; 
        PrintWriter out = null;

        try{
            inputStream = new BufferedReader(new FileReader(fileName));
            
            LineEditor doc = new LineEditor(); 
            
            doc.fill(inputStream);
            inputStream.close();
            System.out.println("File loaded successfully"); 
            
            PrintWriter outputStream = new PrintWriter(new FileWriter(fileName));
            
            while(activeSession){
                switchBoard(doc, outputStream);
            }
            
        }catch(IOException e){
            System.out.println("File not found, exiting program");
        } 
    }
    
    public static void switchBoard(LineEditor editor, PrintWriter outFile){
        
        Scanner stdin = new Scanner(System.in); 
        
        String menu = 
                "--------------------------------\n" + 
                "-      CHOOSE AND ACTION       -\n" + 
                "--------------------------------\n" +
                "'p'  - PRINT FILE TO SCREEN\n" + 
                "'c'  - PRINT CURRENT LINE\n" + 
                "'af' - ADD NEW FIRST LINE\n" + 
                "'al' - ADD NEW LAST LINE\n" + 
                "'ic' - INSERT NEW LINE AS CURRENT LINE\n" + 
                "'df' - DELETE FIRST LINE\n" + 
                "'dl' - DELETE LAST LINE\n" + 
                "'dc' - DELETE CURRENT LINE\n" + 
                "'f'  - FIND WORD\n" + 
                "'fr' - FIND AND REPLACE\n" + 
                "'lc' - LINE COUNT\n" + 
                "--------------------------------\n" + 
                "- SELECT ANY OTHER KEY TO EXIT -\n" + 
                "--------------------------------\n";
        
        System.out.println(menu); 
        System.out.print(PROMPT);
        String action = stdin.nextLine(); 
        
        String lineText; 
        
        switch(action){
            case "p" :
                System.out.println(editor);
                break;
            case "c" :
                System.out.printf("line %d >> %s\n", editor.currentLine(), 
                        editor.readCurrentLine());
                break; 
            case "af":
                System.out.print(PROMPT);
                lineText = stdin.nextLine();
                editor.insertFirst(lineText);
                showCurrent(editor);
                break;
            case "al":
                System.out.print(PROMPT);
                lineText = stdin.nextLine(); 
                editor.insertLast(lineText);
                showCurrent(editor); 
                break;
            case "ic":
                System.out.print(PROMPT);
                lineText = stdin.nextLine();
                editor.insertCur(lineText);
                showCurrent(editor);
                break; 
            case "df":
                editor.delFirst();
                showCurrent(editor);
                break;
            case "dl":
                editor.delLast(); 
                showCurrent(editor);
                break;
            case "dc":
                editor.delCur();
                showCurrent(editor);
                break;
            case "f":
                System.out.print("FIND: ");
                lineText = stdin.nextLine(); 
                if(editor.findStr(lineText)){
                    System.out.printf("%s FOUND AT LINE %d\n", lineText, 
                            editor.currentLine() );
                }else{
                    System.out.printf("%s NOT FOUND IN DOCUMENT\n", lineText);
                }; 
                showCurrent(editor);
                break;
            case "fr":
                System.out.print("ENTER WORD TO FIND: "); 
                String find = stdin.nextLine(); 
                System.out.print("REPLACE WITH: "); 
                String replace = stdin.nextLine();
                editor.replaceStr(find, replace);
                showCurrent(editor);
                break;
            case "lc":
                System.out.printf("LINE COUNT: %d\n", editor.numLines());
                showCurrent(editor);
                break;
            default:
                activeSession = false; 
                // traverse the linkedList and write contents to file. 
                editor.printLines(outFile);
                outFile.close();
                System.out.println("Saving contents of file to " + fileName);
                System.out.println("Goodbye!");
                
                break;
        }
    }
    
    private static void showCurrent(LineEditor editor){
        System.out.printf("CURRENT LINE: %d >> %s\n", editor.currentLine(),
            editor.readCurrentLine());
    }
}
