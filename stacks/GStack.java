
import java.util.ArrayList;

/**
 * GStack Class for Project 4. Uses StringNode and 
 * @author JohnMezzanotte
 * Contact: jmezzan1@my.smccd.edu
 * GStack.java
 * CIS 256 JAVA
 * Date: 3-22-2016
 */
public class GStack<T> {
    
    private ArrayList<T> data;
    private final String EMPTY_STACK = "Stack is empty.";
    
    public GStack(){
        data = new ArrayList<T>();
    }
    
    /**
     * Determines whether the stack is empty. 
     * @precondition None.
     * @postcondition Returns true if the stack is empty; otherwise returns false.
     * @return 
     */
    public boolean isEmpty(){
        return (data.size() == 0);
    }
    
    /**
     * Adds an item to the top of the stack
     * @precondition newItem is the item to be added.
     * @postcondition If insertion is successful, newItem is on the top of the stack.
     * @throws StackException
     * @param newItem 
     */
    public void push(T newItem) throws StackException{
        data.add(newItem);
    }
    
    /**
     * Retrieves the top of the stack.
     * @precondition None
     * @postcondition If the stack is not empty, then item that was added most recently is 
     *  returned. The stack is unchanged.
     * @return
     * @throws StackException 
     */
    public T peek() throws StackException{
        if(isEmpty()){
            throw new StackException(EMPTY_STACK);
        }
        return data.get(data.size() -1);
    }
    
    /**
     * Removes the top of a stack.
     * @precondtion None
     * @postcondition If the stack is not empty, the item that was added most
     * recently is removed from the stack and returned.
     * @return
     * @throws StackException 
     */
    public T pop() throws StackException{
        if(isEmpty()){
            throw new StackException(EMPTY_STACK);
        }
        return data.remove(data.size() - 1);
    }
   
    /**
     * Removes all the items from the stack.
     * @precondition None 
     * @postcondition Stack is empty
     */
    public void popAll(){
        
        // The size of the ArrayList will change after each 
        // iteration. We have to account for this in the loop.  
        int count = data.size() - 1;
        
        while(count >= 0){
            System.out.print(data.remove(count) + " ");
            count--;
            
        }
    }
    
    public int getSize(){
        return data.size();
    }
    
    @Override
    public String toString(){
        
        if(isEmpty()){
            return "{}";
        }
        
        int count = data.size() - 1;
        String desc = "{ "; 
     
        while(count >= 0){
            if(count == 0){
                desc += data.get(count) + " }";
            }else{
                desc += data.get(count) + ", ";
            }
            count--; 
        }
        
        return desc;
    }
    
    
}
