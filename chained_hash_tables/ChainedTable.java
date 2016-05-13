// File: ChainedTable.java from the package edu.colorado.collections
// This is an assignment for students to complete after reading Chapter 11 of
// "Data Structures and Other Objects Using Java" by Michael Main.

// put, remove, containsKey, and get methods were coded by John Mezzanotte 


/******************************************************************************
* A <CODE>ChainedTable</CODE> is a chained hash table.
*
******************************************************************************/
public class ChainedTable<K, E>
{
   // Invariant of the ChainedTable class:
   //   For index i, the Object in table[i] is the head reference for a linked
   //   of all the elements for which hash(key) is i.
    private Object[ ] table;


   /**
   * Initialize an empty ChainedTable with a specified table size.
   * @param <CODE>tableSize</CODE>
   *   the table size for this new chained hash table
   * <dt><b>Precondition:</b><dd>
   *   <CODE>tableSize > 0</CODE>
   * <dt><b>Postcondition:</b><dd>
   *   This ChainedTable is empty and has the specified table size.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory for the specified table size.
   * @exception IllegalArgumentException
   *   Indicates that tableSize is not positive.
   **/
   public ChainedTable(int tableSize)
   {
      if (tableSize <= 0)
	   throw new IllegalArgumentException("Table size must be positive.");
      // Allocate the table which is initially all null head references.
      table = new Object[tableSize];
   }


   /**
   * Determines whether a specified key is in this ChainedTable.
   * @param <CODE>key</CODE>
   *   the non-null key to look for
   * <dt><b>Precondition:</b><dd>
   *   <CODE>key</CODE> cannot be null.
   * @return
   *   <CODE>true</CODE? (if this ChainedTable contains an object with the specified
   *   key); <CODE>false</CODE> otherwise. Note that <CODE>key.equals( )</CODE>
   *   is used to compare the <CODE>key</CODE> to the keys that are in the
   *   ChainedTable.
   * @exception NullPointerException
   *   Indicates that <CODE>key</CODE> is null.
   **/
   public boolean containsKey(K key)
   {
      
       if( key == null ){
           throw new NullPointerException("Key is null.");
       }
       
      // remember the nodes will contain the key 
      ChainedHashNode<K, E> cursor = (ChainedHashNode<K, E>)table[hash(key)];
      boolean answer = false;
      
      if( cursor == null ){ // we don't have a key
          return answer;
      }
      
      while( ( cursor != null ) && ( !answer) ){
          if( cursor.key.equals( key ) ){
               answer = true;
          }
          cursor = cursor.link;
      }
      
      return answer;
      
   }
   
   /** Retrieves an object for a specified key.
   * @param <CODE>key</CODE>
   *   the non-null key to look for
   * <dt><b>Precondition:</b><dd>
   *   <CODE>key</CODE> cannot be null.
   * @return
   *   a reference to the object with the specified <CODE>key</CODE (if this
   *   ChainedTable contains an such an object);  null otherwise. Note that
   *   <CODE>key.equals( )</CODE> is used to compare the <CODE>key</CODE>
   *   to the keys that are in the ChainedTable.
   * @exception NullPointerException
   *   Indicates that <CODE>key</CODE> is null.
   **/
   public E get(K key)
   {
       if( key == null ){
           throw new NullPointerException("Key is null.");
       }

      ChainedHashNode<K, E> cursor = (ChainedHashNode<K, E>) table[hash(key)];
      
      if( cursor == null ){
          return null;
      }
      
      // if there is a cursor, this means there's a key somewhere in the list
      while( cursor != null ){
          
          if( cursor.key.equals( key ) ){
              return cursor.element;
          }
          
          // re-evaluation cursor, check again
          cursor = cursor.link;
      }
      return null;
       
  
   }


   private int hash(K key)
   // The return value is a valid index of the ChainedTable's table. The index is
   // calculated as the remainder when the absolute value of the key's
   // hash code is divided by the size of the ChainedTable's table.
   {
       
       return Math.abs(key.hashCode( )) % table.length;
   }
   
   /**
    * Second hash function used for double hashing.
    * @param key
    * @return 
    */
   /*
   private int hash2(K key){
       
       return 1 + (Math.abs(key.hashCode( )) % (table.length -2 ));
   }
   */


   /**
   * Add a new element to this ChainedTable, using the specified key.
   * @param <CODE>key</CODE>
   *   the non-null key to use for the new element
   * @param <CODE>element</CODE>
   *   the new element that's being added to this ChainedTable
   * <dt><b>Precondition:</b><dd>
   *   Neither <CODE>key</CODE> nor </CODE>element</CODE> is null.
   * <dt><b>Postcondition:</b><dd>
   *   If this ChainedTable already has an object with the specified <CODE>key</CODE>,
   *   then that object is replaced by </CODE>element</CODE>, and the return
   *   value is a reference to the replaced object. Otherwise, the new
   *   </CODE>element</CODE> is added with the specified <CODE>key</CODE>
   *   and the return value is null.
   * @exception NullPointerException
   *   Indicates that <CODE>key</CODE> or <CODE>element</CODE> is null.
   **/
   @SuppressWarnings("unchecked")
   public E put(K key, E element)
   {
      if( key == null || element == null ){
          throw new NullPointerException( "Key or element is null" );
      }
     
      //ChainedHashNode<K, E> cursor = null; 
      ChainedHashNode<K, E> cursor = (ChainedHashNode<K, E>)table[hash(key)];
      E answer = null;
      
      // Student code should be placed here to set cursor so that it refers to
      // the node that already contains the specified key. If there is no such
      // node, then the student code should set cursor to null.
      //cursor = ( ChainedHashNode<K, E> )table[ hash( key ) ];
      
      if (cursor == null)
      {  // Add a new node at the front of the list of table[hash(key)].
	 int i = hash(key);
	 cursor = new ChainedHashNode<K, E>( );
	 cursor.element = element;
	 cursor.key = key;
	 cursor.link = (ChainedHashNode<K, E>) table[i];
	 table[i] = cursor;
      }
      else
      {  // The new element replaces an old node.
         ChainedHashNode<K, E> previous = null;

         while( cursor != null ){
             if( cursor.key.equals( key ) ){
                answer = cursor.element;
                cursor.element = element;
             }
             previous = cursor;
             cursor = cursor.link;
         }
         
         // At this point, we should be at the end of the linked list
         // Without a match, cursor is null, but previous is not
         ChainedHashNode<K, E> temp = new ChainedHashNode<K, E>();
         temp.key = key;
         temp.element = element;
         previous.link = temp;
         
      }

      return answer;
   }


   /**
   * Removes an object for a specified key.
   * @param <CODE>key</CODE>
   *   the non-null key to look for
   * <dt><b>Precondition:</b><dd>
   *   <CODE>key</CODE> cannot be null.
   * <dt><b>Postcondition:</b><dd>
   *   If an object was found with the specified </CODE>key</CODE>, then that
   *   object has been removed from this ChainedTable and a copy of the removed object
   *   is returned; otherwise, this ChainedTable is unchanged and the null reference
   *   is returned.  Note that
   *   <CODE>key.equals( )</CODE> is used to compare the <CODE>key</CODE>
   *   to the keys that are in the ChainedTable.
   * @exception NullPointerException
   *   Indicates that </CODE>key</CODE> is null.
   **/
   public E remove(K key)
   {
       if( key == null ){
           throw new NullPointerException("Key is null.");
       }
       
       int index = hash( key );
       E answer = null;
       ChainedHashNode<K, E> cursor = (ChainedHashNode<K, E>)table[hash(key)];
       ChainedHashNode<K, E> previous = cursor;

       while( cursor != null ){
          if( cursor.key.equals( key ) ){
             // if previous and cursor are equal, we are have a head node
            if( cursor == previous ){ // remove head
                table[ index ] = cursor.link;
                answer = cursor.element;
            }else{
                previous.link = cursor.link;
                answer = cursor.element;
            }
          }  
          
          previous = cursor;
          cursor = cursor.link;
        }
       
       return answer;
   }

}

class ChainedHashNode<K, E>
{
   K key;
   E element;
   ChainedHashNode<K, E> link;
   
   public ChainedHashNode<K, E> getLink(){
       return link;
   }
   
}
