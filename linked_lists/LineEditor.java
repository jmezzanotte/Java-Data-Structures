package linkedlists;

import java.io.BufferedReader; 
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author JohnMezzanotte
 * Contact: jmezzan1@my.smccd.edu
 * LineEditor.java
 * CIS 256 JAVA
 * Date: 3-8-2016
 */
public class LineEditor {
    
    private StringNode lines; 
    private int currentLine; 
    
    public LineEditor(){
        lines = null;
        currentLine = 0;
    }
    
    /**
     * LineEditor object initialized with data.
     * @precondition
     *  name is a ifstream object for a file that has been successfully opened.
     * @postcondition
     *  Lines from the file have been stored in the linked list
     *  (one line per node). The current line has been set to the first line
     *  (if the file is empty, current line is 0).
     * @param name
     * @throws IOException 
     */
    
    public void fill(BufferedReader name) throws IOException{
         
        String inputLine = name.readLine();
        int counter = 0;
        
        if(inputLine == null){ // file is empty
            currentLine = 0 ; 
        }else{
        
            while(inputLine != null){

                Scanner lineScanner = new Scanner(inputLine); 

                while(lineScanner.hasNext()){
                    if(counter == 0){
                        // inserting the very first node or line
                        lines = new StringNode(lineScanner.nextLine(), null);
                    }else{
                        StringNode.listTailInsert(lines, lineScanner.nextLine()); 
                    }
                }

                inputLine = name.readLine();

                counter++; 
            }
            
            currentLine = 1;
        }
    } // end fill
    
    /**
     * Determines the number of lines stored in the file
     * @precondition 
     *  None
     * @postcondition
     *  Object is unchanged.
     * @return 
     *  Number of lines in the file.
     */
    public int numLines(){
        return StringNode.listLength(lines);
    }
    
    /**
     * Accessor method for current line number
     * @precondition
     *  None
     * @postcondition
     *  object is unchanged
     * @return 
     */
    public int currentLine(){
        return currentLine; 
    }
   
    
    /**
     * Method to read and return the data from the current line.
     * @postcondition
     *  The current line remains unchanged. 
     * @return 
     *  Contents of the current line.
     */
    public String readCurrentLine(){
        
        StringNode cursor = lines; 
       
        if(currentLine == 0 | lines == null){
            return "No Lines stored."; 
        }else{
            for(int i = 1; i < currentLine  ; i++){
                cursor = cursor.getLink();
            }
            
            return cursor.getData();
        }
        
    }
    
    /**
     * Prints the current line to the object 'out'; if object is empty, 
     * prints "No Lines Stored"
     * @precondition
     *  None
     * @postcondition
     *  Object is unchanged.
     * @param out 
     */
    public void printCurrent(PrintWriter out){
       
        StringNode cursor = lines; 
        
        if(currentLine == 0){
            System.out.println("No lines stored."); 
        }else{
           for(int i = 1; i < currentLine; i++){
               cursor = cursor.getLink();
           } 
            
           //out.println(cursor.getData());
           lines.outNode(out,cursor);
          
        }
    }
    
    public void printLines(PrintWriter out){
        
        if(lines == null){
            return;
        }else{
            for(StringNode cursor = lines; cursor != null; cursor = cursor.getLink()){
                StringNode.outNode(out, cursor); 
            }
        }
        
    }
    
    /**
     * Adds new first line.
     * @precondition
     *  None
     * @postcondition
     *  A new line has been added prior to existing lines. The current line 
     *  has been reset to the first line (i.e. 1). 
     * @param line 
     */
    public void insertFirst(String line){
        // I chose to include the bulk of this method as functionality in the 
        // StringNode class. It seemed to make sense to include adding a 
        // node to the front of the list as part of the functionality of 
        // StringNode.
        lines = StringNode.listHeadInsert(lines, line);
       
        currentLine = 1; 
        
    }
    
    /**
     * Adds a new last line.
     * @precondition
     *  None
     * @postcondition
     *  A new line has been added following existing lines. The current
     *  line has been reset to the last line.
     * @param line 
     */
    public void insertLast(String line){
        StringNode.listTailInsert(lines, line);
        currentLine = StringNode.listLength(lines);
        System.out.println(currentLine);
        
    }
    
    
    /**
     * Adds a new line following the current line. 
     * @precondition
     *  list is not empty.
     * @postcondition
     *  a new line has been inserted preceding the current line. The current
     *  line number does not change.
     * @param line 
     */
    public void insertCur(String line){
        
        StringNode cursor = lines; 
        
        if(cursor == null){
            return; 
        }
        
        if(currentLine == 1){
            // if we are at the first line, insert at the head
            lines = StringNode.listHeadInsert(cursor, line); // insert the first line
            currentLine = 1; 
        }else if(currentLine > 1 ){
            for(int i = 1; i < currentLine - 1; i++){
                cursor = cursor.getLink();
            }
            cursor.addNodeAfter(line);         
        }
  
        
    }
    
    /**
     * Deletes the first line. 
     * @precondition
     *  there exists 1 or more lines in the file. 
     * @postcondition
     *  The first line has been removed. The current line is now the new first
     *  line. If there are no lines remaining, the current line is 0.
     */
    public void delFirst(){
        
        if(lines == null){
            currentLine = 0; 
        }else if(StringNode.listLength(lines) == 1){
            lines = StringNode.listHeadRemove(lines);
            currentLine = 0 ; 
        }else{
            lines = StringNode.listHeadRemove(lines);
            currentLine = 1;
            
        }  
    }
    
    
    /**
     * Deletes the last line 
     * @precondition
     *  there is at least one line 
     * @postcondition
     *  the last line has been removed. The current line has been reset to the 
     *  new last line. If there are no lines remaining the current line is set 
     *  to 0. 
     */
    public void delLast(){
        
        if(lines == null){
            currentLine = 0; 
        }else if(StringNode.listLength(lines) == 1){
            lines = StringNode.listHeadRemove(lines);
            currentLine = 0; 
        }else{
            lines = StringNode.listTailRemove(lines);
            currentLine = StringNode.listLength(lines);
        }
             
    }
    
    /**
     * Deletes the current line
     * @precondition 
     *  list is not empty. Since this method also uses removeNodeAfter, you 
     *  have to ensure that the current line is not the tail of the list. 
     * @postcondition
     *  The current line has been removed. If there are no more lines, the 
     *  current line is set to 0. 
     */
    public void delCur(){
        
        StringNode cursor = lines; 
        
        if(cursor == null){
            currentLine = 0; 
        }else if(StringNode.listLength(lines) == 1){
            lines = StringNode.listHeadRemove(cursor);
            currentLine = 0; 
        }else if(currentLine == 1){
            lines = StringNode.listHeadRemove(cursor);
            currentLine = 1;
        }else{
            // you need to navigate to the node just before the current line.
            for(int i = 1; i < currentLine - 1; i++){
                cursor = cursor.getLink();
            }
            
            if(cursor.getLink() != null){
                cursor.removeNodeAfter();
            }
            
            int listLen = StringNode.listLength(lines);
            
            if(currentLine > listLen){
                currentLine = listLen;
            }
   
        }
    }
    
    /**
     * Finds the first occurrence of a substring within the file. 
     * @precondition
     *  None
     * @postcondition
     *  If the substring was found, the current line has been changed to the 
     *  line in which that substring occurred. 
     * @param str
     * @return 
     *  True if substring was found, false otherwise. 
     */
    public boolean findStr(String str){
        
        StringNode cursor = lines;
        int temp = currentLine; 
        
        for(currentLine = 1; currentLine <= StringNode.listLength(lines); currentLine++){
            
            if(cursor.getData().contains(str)){
                return true; 
            }
            
            cursor = cursor.getLink();
            
        }
        
        currentLine = temp; 
        return false;
    }
    
    /**
     * Replaces first occurrence of substring oldStr with substring newStr in 
     * current line. This search replace function IS case-sensitive.
     * @precondition
     *  current line is not 0.
     * @postcondition
     *  current line may have been updated(if old occurred in line)
     * @param oldStr
     * @param newStr 
     */
    public void replaceStr(String oldStr, String newStr){
        
        StringNode cursor = lines; 
        int temp = currentLine; 
        
        if(currentLine != 0){

            for(currentLine = 1; currentLine <= StringNode.listLength(lines); currentLine++){
                // see if there is a match 
                if(cursor.getData().contains(oldStr)){
                    cursor.setData(cursor.getData().replaceFirst(oldStr, newStr));
                    return; 
                }
                
                cursor = cursor.getLink();
                
            }
            
            currentLine = temp;
        }  
    }
    
    /**
     * Builds string containing contents of LineEditor, each line separated
     * by \n. 
     * @precondition
     *  None. 
     * @postcondition
     *  LineEditor object is unchanged
     * @return 
     *  String containing contents of LineEditor
     */
    @Override
    public String toString(){
        
        String text = ""; 
        
        for(StringNode cursor = lines; cursor != null; cursor = cursor.getLink()){
            text += cursor.getData() + "\n";
        }
        
        return text;
        
    }
    
}
