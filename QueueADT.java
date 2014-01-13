/**
 * Abstract data type to represent a Queue. A queue is a fifo (first-in, 
 * first-out) data structure, thus the item that was inserted into the queue
 * first is the first one to be removed.
 * @author Sarah Gilliland, for CS367 Spring 2013
 *
 * @param <E>
 */
public interface QueueADT<E> {

	/**
	 * Enqueues the given item into the queue (i.e., adds it at the back of 
	 * the queue).
	 * @param item The item to be added
	 */
	void enqueue(E item);
	
	/**
	 * Dequeues the first item in the queue (i.e., removes the item from the 
	 * front of the queue and returns it). If the queue is empty, returns null.
	 * @return the item that was dequeued, or null if the queue is empty.
	 */
	E dequeue();
	
	/**
	 * Returns whether or not this queue is empty.
	 * @return True if this queue is empty, otherwise false.
	 */
	boolean isEmpty();
	
	/**
	 * Returns how many items are currently in the queue.
	 * @return how many items are currently in the queue.
	 */
	int size();
}
