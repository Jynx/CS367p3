
public class ArrayQueue<E> implements QueueADT<E> {
    private static final int INITSIZE = 10;
    private E[] items;    // items array
    private int numItems; //number of items in queue
    private int rearIndex, frontIndex = 0; // initialized front/rear index
    /**
     * Constructor for creating array
     * Pays careful attention to casting
     */
    public ArrayQueue() {
        items = (E[]) (new Object[INITSIZE]);//creates array
        numItems = 0;
    }
    /**
     * Enqueues an item into the built array. Adds to rear
     * Creates circular Array based off of moving of both front
     * and rear index.
     * Increases array size if needed.
     */
    public void enqueue(E item) {
        if (items.length == numItems) {
        	// resizes if necessary
            E[] tmp = (E[]) (new Object[items.length*2]); 
            System.arraycopy(items, frontIndex, tmp, frontIndex,
                    items.length-frontIndex);
            if (frontIndex != 0) {
                System.arraycopy(items, 0, tmp, items.length, frontIndex);
            }
            items = tmp;
            // handles proper position of rearIndex when resizing
            rearIndex = frontIndex + numItems - 1;
        }
        items[rearIndex] = item;
        rearIndex = incrementIndex(rearIndex);
        numItems++;
    }
    /**
     * Dequeues an item from front of array.
     * Will downsize array if too large.
     * @return item removed
     */
    public E dequeue() {
        if (numItems == 0) {
            return null;
        }
        E item = items[frontIndex];
        items[frontIndex] = null;
        frontIndex = incrementIndex(frontIndex);
        numItems --;
        if (numItems == (int) Math.ceil(items.length/3) + 2) {
        	// variable for storing calculated smaller size to avoid
        	// repeating calculation.
            int downSize = (int) Math.ceil(items.length/3) + 2;
            E[] tmp = (E[]) (new Object[downSize + 3]);
            System.arraycopy(items, frontIndex, tmp, 0, downSize);
            items = tmp;
            rearIndex = downSize;
            frontIndex = 0;          
        }
        return item;
    }
    
    /**
     * Clears the array of all items
     * Will downsize array to initial size
     */
    public void clear() {
        items = (E[]) (new Object[INITSIZE]);//creates array
        numItems = 0;
        rearIndex = 0;
        frontIndex = 0;
    }
    
    /**
     * Examines if array is empty
     * @return true if empty else false.
     */
    public boolean isEmpty() {
        if (numItems == 0) {
            return true;
        }
        else {return false;}
    }
    /**
     * checks size of array
     * @return number of items in array
     */
    public int size() {
        return numItems;
    }
    /**
     * Used for changing positions of front and rear index
     * to maintain circular array.
     * @param index
     * @return 0 if index is not at end of array
     * otherwise advances index 1 position.
     */
    private int incrementIndex(int index) {
        if (index == items.length - 1) {
            return 0;
        }
        else {
            return index + 1;   
        }
    }
}
