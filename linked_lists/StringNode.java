

import java.io.PrintWriter;


/**
 * Text editor application for project 3 Linked Lists. Uses StringNode and 
 * LineEditor classes. I added a few additional methods to assist in some tasks
 * that were not on the original spec. Those methods include listHeadInsert, 
 * and listHeadRemove.
 * @author JohnMezzanotte
 *  Adapted by John Mezzanotte to add several new methods to the class 
 *  including: listTailRemove, listTailInsert, listHeadInsert, listHeadRemove, 
 *  getLast, outNode, and outList.
 * Contact: jmezzan1@my.smccd.edu
 * StringNode.java
 * CIS 256 JAVA
 * Date: 3-8-2016
 */
public class StringNode
{
   // Invariant of the StringNode class:
   //   1. The node's String data is in the instance variable data.
   //   2. For the final node of a list, the link part is null.
   //      Otherwise, the link part is a reference to the
   //      next node of the list.
   private String data;
   private StringNode link;  

   /**
   * Initialize a node with a specified initial data and link to the next
   * node. Note that the initialLink may be the null reference, 
   * which indicates that the new node has nothing after it.
   * @param initialData
   *   the initial data of this new node
   * @param initialLink
   *   a reference to the node after this new node--this reference may be null
   *   to indicate that there is no node after this new node.
   * @postcondition
   *   This node contains the specified data and link to the next node.
   **/   
   public StringNode(String initialData, StringNode initialLink)
   {
      data = initialData;
      link = initialLink;
   }

 
   /**
   * Modification method to add a new node after this node.   
   * @param item
   *   the data to place in the new node
   * @postcondition
   *   A new node has been created and placed after this node.
   *   The data for the new node is item. Any other nodes
   *   that used to be after this node are now after the new node.
   * @exception OutOfMemoryError
   *   Indicates that there is insufficient memory for a new 
   *   StringNode. 
   **/
   public void addNodeAfter(String item)   
   {
      link = new StringNode(item, link);
   }          
   
   
   /**
   * Accessor method to get the data from this node.   
   * @param - none
   * @return
   *   the data from this node
   **/
   public String getData( )   
   {
      return data;
   }
   
   
   /**
   * Accessor method to get a reference to the next node after this node. 
   * @param - none
   * @return
   *   a reference to the node after this node (or the null reference if there
   *   is nothing after this node)
   **/
   public StringNode getLink( )
   {
      return link;                                               
   } 
    
    
   /**
   * Copy a list.
   * @param source
   *   the head of a linked list that will be copied (which may be
   *   an empty list in where source is null)
   * @return
   *   The method has made a copy of the linked list starting at 
   *   source. The return value is the head reference for the
   *   copy. 
   * @exception OutOfMemoryError
   *   Indicates that there is insufficient memory for the new list.   
   **/ 
   public static StringNode listCopy(StringNode source)
   {
      StringNode copyHead;
      StringNode copyTail;
      
      // Handle the special case of the empty list.
      if (source == null)
         return null;
         
      // Make the first node for the newly created list.
      copyHead = new StringNode(source.data, null);
      copyTail = copyHead;
      
      // Make the rest of the nodes for the newly created list.
      while (source.link != null)
      {
         source = source.link;
         copyTail.addNodeAfter(source.data);
         copyTail = copyTail.link;
      }
 
      // Return the head reference for the new list.
      return copyHead;
   }
   
   
   /**
   * Copy a list, returning both a head and tail reference for the copy.
   * @param source
   *   the head of a linked list that will be copied (which may be
   *   an empty list in where source is null)
   * @return
   *   The method has made a copy of the linked list starting at 
   *   source.  The return value is an
   *   array where the [0] element is a head reference for the copy and the [1]
   *   element is a tail reference for the copy.
   * @exception OutOfMemoryError
   *   Indicates that there is insufficient memory for the new list.   
   **/
   public static StringNode[ ] listCopyWithTail(StringNode source)
   {
      StringNode copyHead;
      StringNode copyTail;
      StringNode[ ] answer = new StringNode[2];
     
      // Handle the special case of the empty list.   
      if (source == null)
         return answer; // The answer has two null references .
      
      // Make the first node for the newly created list.
      copyHead = new StringNode(source.data, null);
      copyTail = copyHead;
      
      // Make the rest of the nodes for the newly created list.
      while (source.link != null)
      {
         source = source.link;
         copyTail.addNodeAfter(source.data);
         copyTail = copyTail.link;
      }
      
      // Return the head and tail references.
      answer[0] = copyHead;
      answer[1] = copyTail;
      return answer;
   }
   
   
   /**
   * Compute the number of nodes in a linked list.
   * @param head
   *   the head reference for a linked list (which may be an empty list
   *   with a null head)
   * @return
   *   the number of nodes in the list with the given head 
   * @note
   *   A wrong answer occurs for lists longer than Int.MAX_VALUE.     
   **/   
   public static int listLength(StringNode head)
   {
      StringNode cursor;
      int answer;
      
      answer = 0;
      for (cursor = head; cursor != null; cursor = cursor.link)
         answer++;
        
      return answer;
   }
   

   /**
   * Copy part of a list, providing a head and tail reference for the new copy. 
   * @param start/end
   *   references to two nodes of a linked list
   * @param copyHead/copyTail
   *   the method sets these to refer to the head and tail node of the new
   *   list that is created
   * @precondition
   *   start and end are non-null references to nodes
   *   on the same linked list,
   *   with the start node at or before the end node. 
   * @return
   *   The method has made a copy of the part of a linked list, from the
   *   specified start node to the specified end node. The return value is an
   *   array where the [0] component is a head reference for the copy and the
   *   [1] component is a tail reference for the copy.
   * @exception IllegalArgumentException
   *   Indicates that start and end are not references
   *   to nodes on the same list.
   * @exception NullPointerException
   *   Indicates that start is null.
   * @exception OutOfMemoryError
   *   Indicates that there is insufficient memory for the new list.    
   **/   
   public static StringNode[ ] listPart(StringNode start, StringNode end)
   {
      StringNode copyHead;
      StringNode copyTail;
      StringNode cursor;
      StringNode[ ] answer = new StringNode[2];
      
      // Make the first node for the newly created list. Notice that this will
      // cause a NullPointerException if start is null.
      copyHead = new StringNode(start.data, null);
      copyTail = copyHead;
      cursor = start;
      
      // Make the rest of the nodes for the newly created list.
      while (cursor != end)
      {
         cursor = cursor.link;
         if (cursor == null)
            throw new IllegalArgumentException
            ("end node was not found on the list");
         copyTail.addNodeAfter(cursor.data);
         copyTail = copyTail.link;
      }
      
      // Return the head and tail references
      answer[0] = copyHead;
      answer[1] = copyTail;
      return answer;
   }        
   
   
   /**
   * Find a node at a specified position in a linked list.
   * @param head
   *   the head reference for a linked list (which may be an empty list in
   *   which case the head is null)
   * @param position
   *   a node number
   * @precondition
   *   position > 0.
   * @return
   *   The return value is a reference to the node at the specified position in
   *   the list. (The head node is position 1, the next node is position 2, and
   *   so on.) If there is no such position (because the list is too short),
   *   then the null reference is returned.
   * @exception IllegalArgumentException
   *   Indicates that position is not positive.    
   **/   
   public static StringNode listPosition(StringNode head, int position)
   {
      StringNode cursor;
      int i;
      
      if (position <= 0)
           throw new IllegalArgumentException("position is not positive");
      
      cursor = head;
      for (i = 1; (i < position) && (cursor != null); i++)
         cursor = cursor.link;

      return cursor;
   }


   /**
   * Search for a particular piece of data in a linked list.
   * @param head
   *   the head reference for a linked list (which may be an empty list in
   *   which case the head is null)
   * @param target
   *   a piece of data to search for
   * @return
   *   The return value is a reference to the first node that contains the
   *   specified target. If there is no such node, the null reference is 
   *   returned.     
   **/   
   public static StringNode listSearch(StringNode head, String target)
   {
      StringNode cursor;
      
      for (cursor = head; cursor != null; cursor = cursor.link)
         if (target.equals(cursor.data))
            return cursor;
        
      return null;
   }
   
   /**
    * Adds a new node to the front of the linked list. I decided to add this 
    * method into the StringNode class as additional functionality. The 
    * LineEditor class requires this operation and it seemed to make sense 
    * to build this functionality as a standard part of this class. 
    * @param headptr
    * @param entry
    * @return 
    *   Reference to the head of the linked list. 
    */
   public static StringNode listHeadInsert(StringNode headptr, String entry){
        
        if(headptr == null){
            headptr = new StringNode(entry, null); 
        }else{
            StringNode temp = new StringNode(entry, headptr); 
            headptr = temp; 
        }
        
        return headptr;
       
   }
   
   /**
    * A new node containing the given entry has been added at the tail of the 
    * linked list; headptr points to the head of the new longer linked list.
    * @param headptr
    * @param entry
    * @precondition
    *   headptr is a reference to the head of a linked list (may be empty)
    * @return 
    *   A reference to the head of the updated list
    */
   public static StringNode listTailInsert(StringNode headptr, String entry){
       
       // We need to remember the headptr before we loose it when 
       // traversing the linked list. I'll preserver the pointer and use 
       // a temp variable to do the work 
       
       StringNode cursor = headptr; 
       
       // we also need to make sure we are pointing to the head of the list 
       // and not somewhere else
       if(cursor == null){
           return new StringNode(entry, null); 
       }
       
       while(cursor.getLink() != null){
           cursor = cursor.getLink();
       }
       
       // Now we have arrived at the end of the linked list
       cursor.addNodeAfter(entry);
       
       return headptr;
       
   }
   
   /**
    * The tail(last node) has been removed and returned to the heap.
    * @param headptr
    *   Reference to the head of the new, shorter linked list
    * @precondition
    *   headptr is the head reference of a linked list (list has at least 
    *   one node. That is, there is one node after the head.
    * @return
    *   Reference to the head of the updated list.
    */
   public static StringNode listTailRemove(StringNode headptr){
       
       // preserve our reference to the headptr
       StringNode cursor = headptr; 
       
       if(cursor == null){ // we have an empty list; need at leas one node
           return cursor;
       }
       
       if(cursor.getLink() == null){
           // We only have one ned 
           cursor = null; 
           return cursor;
       }
       
       // Walk through the list until we reach the last node
       while(cursor.getLink().getLink() != null){
           //System.out.printf("Traveling past link %d. data: %s\n", i, cursor.getData());
           cursor = cursor.getLink(); 
       }
       
       // I'm assuming that once we loose the reference to this object, this 
       // floating node will be garbage collected by the JVM
       cursor.setLink(null);
       
       return headptr;
   }
   
   /**
    * This method removes the first node from the list
    * @param headptr
    * @return 
    */
   public static StringNode listHeadRemove(StringNode headptr){
       
       if(headptr == null){
           return headptr;
       }
       
       headptr =  headptr.getLink(); 
       return headptr; 
       
   }
   
   
   
   
   /**
    * Finds the last node in the list
    * @precondition
    *   headptr is the reference to the head of a linked list with at least
    *   one node.
    * @param headptr
    * @return 
    *   A reference to the last node in a linked list.
    */
   public static StringNode getLast(StringNode headptr){
       
       StringNode cursor = headptr;
       
       if(cursor == null){
           return cursor;
       }
       
       while(cursor.getLink() != null){
           cursor = cursor.getLink();
       }
       
       return cursor;

   }
   
   /**
    * This method prints the data contained in this node to the output 
    * object out
    * @precondition
    *   headptr is a reference to a StringNode in a linked list.
    * @param out
    * @param headptr 
    */
   public static void outNode(PrintWriter out, StringNode headptr){
       if(headptr == null){
           return; 
       }
       
       out.println(headptr.getData());
   }
   
   /**
    * Prints the data contained in a linked list to the screen.
    * @precondition
    *   List may be empty and headptr refers to the first node in the list.
    * @param headptr 
    */
   public static void outList(StringNode headptr){
       
       for(StringNode cursor = headptr; cursor != null; cursor = cursor.getLink()){
           System.out.println(cursor.getData());
           
       }
       
   }

   /**
   * Modification method to remove the node after this node.   
   * @param - none
   * @precondition
   *   This node must not be the tail node of the list.
   * @postcondition
   *   The node after this node has been removed from the linked list.
   *   If there were further nodes after that one, they are still
   *   present on the list.
   * @exception NullPointerException
   *   Indicates that this was the tail node of the list, so there is nothing
   *   after it to remove.
   **/
   public void removeNodeAfter( )   
   {
      link = link.link;
   }          
  
   /**
   * Modification method to set the data in this node.   
   * @param newData
   *   the new data to place in this node
   * @postcondition
   *   The data of this node has been set to newData.
   **/
   public void setData(String newData)   
   {
      data = newData;
   }                                                               
   
   /**
   * Modification method to set the link to the next node after this node.
   * @param newLink
   *   a reference to the node that should appear after this node in the linked
   *   list (or the null reference if there is no node after this node)
   * @postcondition
   *   The link to the node after this node has been set to newLink.
   *   Any other node (that used to be in this link) is no longer connected to
   *   this node.
   **/
   public void setLink(StringNode newLink)
   {                    
      link = newLink;
   }
   
}
           
