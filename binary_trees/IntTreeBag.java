

import java.util.Iterator;
import java.util.Stack;


public class IntTreeBag implements Cloneable
{
   // Invariant of the IntTreeBag class:
   //   1. The elements in the bag are stored in a binary search tree.
   //   2. The instance variable root is a reference to the root of the
   //      binary search tree (or null for an empty tree).
   private IntBTNode root;   


   /**
   * Insert a new element into this bag.
   * @param <CODE>element</CODE>
   *   the new element that is being inserted
   * <dt><b>Postcondition:</b><dd>
   *   A new copy of the element has been added to this bag.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory a new IntBTNode.
   **/
   public void add(int element)
   {      
      boolean done = false;
      
      // Handle special case first. The tree is empty
      if(root == null){
          root = new IntBTNode(element, null, null);
      }else{ 
        IntBTNode cursor = root; 
        while(!done){
            if(element <= cursor.getData()){
                if(cursor.getLeft() != null){
                    cursor = cursor.getLeft();
                }else{
                    cursor.setLeft(new IntBTNode(element, null, null));
                    done = true;
                }
            }else{
                if(cursor.getRight() != null){
                    cursor = cursor.getRight();
                }else{
                    cursor.setRight(new IntBTNode(element, null, null)); 
                    done = true;
                }
            }
        } // end while
      }  // end outer if  
   } // end add

   /**
   * Add the contents of another bag to this bag.
   * @param <CODE>addend</CODE>
   *   a bag whose contents will be added to this bag
   * <dt><b>Precondition:</b><dd>
   *   The parameter, <CODE>addend</CODE>, is not null.
   * <dt><b>Postcondition:</b><dd>
   *   The elements from <CODE>addend</CODE> have been added to this bag.
   * @exception IllegalArgumentException
   *   Indicates that <CODE>addend</CODE> is null.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory to increase the size of the bag.
   **/
   public void addAll(IntTreeBag addend)
   {
       IntBTNode addroot; 
        
      // make sure the addend is not the same bag that activated the method
      if(root == addend.root){
         addroot = IntBTNode.treeCopy(addend.root);
         addTree(addroot);
      }else{
          addTree(addend.root);
      }
   }
   
   /**
    * Helper method for addAll
    * @precondition addroot is a reference to the root of a binary search tree
    * that is separate from the binary search tree of the bag that activated 
    * this method.
    * @postcondition All the elements from the addroot's binary search tree 
    * have been added to the binary search tree of the bag that activated this 
    * method. 
    * @param addroot 
    */
   private void addTree(IntBTNode addroot){
       if(addroot != null){
           add(addroot.getData());
           addTree(addroot.getLeft());
           addTree(addroot.getRight());
       }
   }
   
   
   /**
   * Generate a copy of this bag.
   * @param - none
   * @return
   *   The return value is a copy of this bag. Subsequent changes to the
   *   copy will not affect the original, nor vice versa. Note that the return
   *   value must be type cast to an <CODE>IntTreeBag</CODE> before it can be used.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory for creating the clone.
   **/ 
   public IntTreeBag clone( )
   {  // Clone an IntTreeBag object.
       
     IntTreeBag answer = null;
      
     try{
          // we need to clone the called object.
          answer = (IntTreeBag) super.clone();
         
      }catch(CloneNotSupportedException e){
          System.out.println("This class does not support clonable"); 
      }
      // make sure root is pointing to a new object  
      answer.root = IntBTNode.treeCopy(answer.root);
      return answer;
   }
  
   /**
   * Accessor method to count the number of occurrences of a particular element
   * in this bag.
   * @param <CODE>target</CODE>
   *   the element that needs to be counted
   * @return
   *   the number of times that <CODE>target</CODE> occurs in this bag
   **/
   public int countOccurrences(int target )
   {
      // keep track of the number of occurences 
      
      int count = 0; 
      IntBTNode cursor = root; 
      
       
      while(cursor != null){
          
          // determine where the target is in relation to the root. 
          // This will determine what side of the BST we are going to 
          // look through 
          
          if(target < cursor.getData()){
              cursor = cursor.getLeft();
          }else if(target > cursor.getData()){
              cursor = cursor.getRight();
          }else{
              count++; 
              cursor = cursor.getLeft();
          }  
      }
      
      return count;
      
   }
   
             
   /**
   * Remove one copy of a specified element from this bag.
   * @param <CODE>target</CODE>
   *   the element to remove from the bag
   * <dt><b>Postcondition:</b><dd>
   *   If <CODE>target</CODE> was found in the bag, then one copy of
   *   <CODE>target</CODE> has been removed and the method returns true. 
   *   Otherwise the bag remains unchanged and the method returns false. 
   **/
   
   public boolean remove( int target )
   {
      IntBTNode cursor = root; 
      IntBTNode parentOfCursor = null; 
      
      while( cursor != null ){
          if( target < cursor.getData()){ // Data is in the left subtree
              parentOfCursor = cursor;
              cursor = cursor.getLeft();
              
          }else if ( target  > cursor.getData()){
              parentOfCursor = cursor;
              cursor = cursor.getRight();
          }else{ // target founf
              //1.) There is only one node. It is a leaf and is the root
              if( cursor.isLeaf() && parentOfCursor == null ){
                  root = null;
              //2.) Leaf that is not the root
              }else if( cursor.isLeaf() && parentOfCursor != null ){
                   // determine which side of the parent we are on
                  if( cursor == parentOfCursor.getLeft() ){
                      // We are on the left 
                      parentOfCursor.setLeft(null);
                  }else if( cursor == parentOfCursor.getRight() ){
                      parentOfCursor.setRight(null);
                  }
              // 3.) One child is null
              }else if( cursor.getLeft() == null && parentOfCursor != null ) {
                  // move parent's left reference to the right child
                  if( cursor == parentOfCursor.getLeft() ){
                      // on the left side
                      parentOfCursor.setLeft(cursor.getRight());
                  }else if ( cursor == parentOfCursor.getRight() ){
                      parentOfCursor.setRight( cursor.getRight() );
                  }
              }else if ( cursor.getRight() == null && parentOfCursor != null ){
                 if( cursor == parentOfCursor.getLeft() ){
                     // We are on the left
                     parentOfCursor.setLeft(cursor.getLeft());
                 }else if ( cursor == parentOfCursor.getRight() ){
                     parentOfCursor.setRight( cursor.getLeft() );
                     
                 }
              // 4.) We have two children.   
              }else{
                  cursor.setData(cursor.getLeft().getRightmostData()); 
                  cursor.setLeft(cursor.getLeft().removeRightmost());
              }

                  
              return true;
          }
          
      }
      
      return false;
         
   }
      
      
   /**
   * Determine the number of elements in this bag.
   * @param - none
   * @return
   *   the number of elements in this bag
   **/                           
   public int size( )
   {
      return IntBTNode.treeSize(root);
   }
   

   /**
   * Create a new bag that contains all the elements from two other bags.
   * @param <CODE>b1</CODE>
   *   the first of two bags
   * @param <CODE>b2</CODE>
   *   the second of two bags
   * <dt><b>Precondition:</b><dd>
   *   Neither b1 nor b2 is null.
   * @return
   *   the union of b1 and b2
   * @exception IllegalArgumentException
   *   Indicates that one of the arguments is null.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory for the new bag.
   **/   
   public static IntTreeBag union(IntTreeBag b1, IntTreeBag b2)
   {
       IntTreeBag answer = new IntTreeBag();
       
       if((b1 == null) || (b2 == null)){
           throw new IllegalArgumentException("One or more bags are empty.");
       }else{
           answer.addTree(b1.root);
           answer.addTree(b2.root);
       }
       
      return answer;
   }
   
   
   public void print(){
       
       final String EMPTY_BAG = "";
       
       if(root == null){
           System.out.println(EMPTY_BAG);
       }else{
           root.print(this.size());
       }
       
   }
   
   /**
    * Accessor method for root. I chose to implement this method to help me 
    * while testing and debugging.
    * @return None
    */
   public IntBTNode getRoot(){
       return root;
   }
   
   
   /*
    * Implment internal iterable for extra credit
    */
   
   private Stack<IntBTNode> s = new Stack<IntBTNode>();
   
   public void start(){
       // clear out the stack 
       s.clear();
       if(root != null){
           inOrderTraversal(root);
       }
      
       
   }
   
   
   public IntBTNode advance(){
       // We should just have to pop a node of the stack here
       return s.pop();
   }
   
   /**
    * Peeks the Node at the top of the stack
    * @precondition stack cannot be empty.
    * @postcondition the stack is unmodified.
    * @return Returns the IntBTNode at the top of the stack
    * 
    */
   public IntBTNode getCurrent(){
       if(!s.isEmpty()){
            return s.peek();
       }else{
           return null;
       }
    }
   
   /**
    * Determines whether or not there are more elements in the stack.
    * @return true if there are more elements in the stack. False otherwise.
    */
   public boolean isCurrent(){
       if(!s.isEmpty()){
           return true;
       }else{
           return false;
       }
   }
   
   /**
    * Helper method to traverse nodes (in-order)
    * @param root 
    */
   private void inOrderTraversal(IntBTNode cursor){
       if( cursor.getLeft() != null ){
           inOrderTraversal( cursor.getLeft() );
       }
       
       s.push( cursor );
       
       if( cursor.getRight() !=null ){
           inOrderTraversal(cursor.getRight());
       }
       
   }   
   
   
   public Iterator<IntTreeBagIterator> iterator(){
       return (Iterator<IntTreeBagIterator>) new IntTreeBagIterator();
   }
   
   
   private class IntTreeBagIterator implements Iterator{
        
        public IntTreeBagIterator(){
            start();
        }
        
        
        @Override
        public boolean hasNext() {
            return isCurrent();
        }

        @Override
        public IntBTNode next() {
            return advance();
        }
       
   }
   
   
   
      
}
           
