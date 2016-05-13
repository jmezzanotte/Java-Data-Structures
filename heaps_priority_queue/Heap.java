


/**
 * JHeap class. This is an implementation of a max heap.
 * @author JohnMezzanotte
 * Contact: jmezzan1@my.smccd.edu
 * Heap.java
 * CIS 256 JAVA
 * Date: 4-25-2016 
 */

public class Heap<E extends Comparable<E>>{
    
    private E[] elements;            // Array that holds heap elements
    private int lastIndex;          // index of last element in heap
    private int maxIndex;           // index of last position in array

    // Constructor
    public Heap(int maxSize){
        elements = (E[]) new Comparable[maxSize];
        lastIndex = -1;
        maxIndex = maxSize - 1;
    }

    /**
     * Determines whether this heap is empty
     * @return true if heap is empty, false otherwise
     */
    public boolean isEmpty(){
        return (lastIndex == -1);
    }
    
    /**
     * Determines whether this heap is full.
     * @return true if heap is full, false otherwise
     */
    public boolean isFull(){
        return (lastIndex == maxIndex);
    }


    /**
     * Add item to the heap. Assuming this is a max heap. This heap will 
     * follow the max heap property 
     * @param item item to add to the heap 
     * @throws PriQOverflowException if heap is already full
     */
    public void add(E item) throws PriQOverflowException{
      
        if( lastIndex >= maxIndex ){
            throw new PriQOverflowException(); 
        }else{
            // place the new item in the last available index of the array
            elements[ lastIndex + 1 ] = item;
            lastIndex++;
          
            E parent = elements[ ( lastIndex - 1 ) / 2 ];
          
            int cursor = lastIndex; // going to need to preserve this
          
            while( item.compareTo( parent ) > 0 ) {
              
                E temp = parent;
                elements[ ( cursor - 1 ) / 2 ] = item;
                elements[ cursor ] = temp;
                cursor = ( cursor - 1 ) / 2 ;
                parent = elements[ ( cursor - 1 ) / 2 ];

            } // end while
        } // end if 
    } // end add
  
    /**
     * Removes the root element from this heap
     * @return returns the root element
     * @throws PriQUnderflowException if the heap is empty
     */
    public E remove() throws PriQUnderflowException{
        
        if( isEmpty() ){
            throw new PriQUnderflowException();
        }

        // First step, preserve the root element for return, then move the 
        // last element to the top of the tree. Decrement last Index.
        E out = elements[0];
        elements[ 0 ] = elements[ lastIndex ];
        lastIndex--;
        
        // We have to maintain the max heap property. Bubble the root down 
        // until it is in the correct position. Start at index 0.
        int cursor = 0; 
        int left = ( 2 * cursor ) + 1; 
        while( left <= lastIndex ){
            // first, if there are children, determine whether the left or 
            // right is larget. We want to swap with the largest child.
            int max = left; // assume the left node has the max
            int right = left + 1; 
            if( right <= lastIndex ){ // we have a right child
                if( elements[ right ].compareTo( elements[ left ]) > 0 ){
                    max++; // this sets max to the right ( left + 1 )
                }
            }   
            // Swap if necessary
            if( elements[ cursor ].compareTo( elements[ max ] ) < 0 ){
                
                E temp = elements[ cursor ];
                elements[ cursor ] = elements[ max ];
                elements[ max ] = temp;
                cursor = max;
                left = ( 2 * cursor ) + 1;
            }else{
                break;
            }
        }
        
        return out;
    }
    
    /**
     * Prints a string representation of the heap
     */
    public void print(){
        
        System.out.print("[ ");
        for( int i = 0; i <= lastIndex; i++ ){
            System.out.print( elements[ i ] + " ");   
        }
        System.out.print("]");
        
        
    }

    /**
     * Produces a string of all the heap elements
     * @return returns a string of all heap elements
     */
    @Override
    public String toString(){
        
        String theHeap = new String("the heap is:\n");
        
        for (int index = 0; index <= lastIndex; index++)
            theHeap = theHeap + index + ". " + elements[index] + "\n";
    
        return theHeap;
  }
    
}
