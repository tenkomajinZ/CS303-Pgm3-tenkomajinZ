import java.util.Iterator;
import java.util.NoSuchElementException;
/*
 * Jason Nguyen
 * Date: October 11, 2026
 * Assignment: CS303 Program 3 - Dynamic Arrays / MyList
 * * Some parts of this codebase were tested through Codex (OpenAI) to ensure accuracy and functionality.
 */
public class MyArrayList<E> implements MyList<E> {
     Object[] data = new Object[5];
     int size = 0;
     
     

    /* Adds an item at the requested index.

     * PRE: index is between 0 and size, inclusive.
     *      There is enough memory available to grow the array if needed.
     * POST: item is inserted at index, later items shift right,
     *       and size increases by one.
     */
    
    public void add(int index, E item) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        if (size == data.length) {
            Object[] larger = new Object[data.length * 2];

            for (int i = 0; i < size; i++) {
                larger[i] = data[i];
            }

            data = larger;
        }

        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
        }

        data[index] = item;
        size++;
    }

    /* Returns the item at an index.

     * PRE: index is between 0 and size - 1.
     * POST: item replaces the value at index, and the old value is returned.
     */
    public boolean contains(Object item) { 
    return indexOf(item) != -1; // Returns true if the item is found, false otherwise
}
    public E get(int index) {
        checkIndex(index);
        return (E) data[index];
    }

    /* Replaces an item and returns the old item.

     * PRE: index is between 0 and size - 1.
     * POST: The value at index is replaced with item, and the old value is returned.
     */
    public E set(int index, E item) {
        checkIndex(index);
        E oldItem = (E) data[index];
        data[index] = item;
        return oldItem;
    }
     
    /* Removes an item and returns it.

     * PRE: index is between 0 and size - 1.
     * POST: The item at index is removed and returned. Later items shift left,
     *       and size decreases by one.
     */
     
    public E remove(int index) {
        checkIndex(index);
        E removedItem = (E) data[index];

        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }

        size--;
        data[size] = null;
        return removedItem;
    }
    
     /* Removes all items.

     * PRE: None.
     * POST: The list is empty and size is zero.
     */
    
    public void clear() {
        for (int i = 0; i < size; i++) {
            data[i] = null;
        }

        size = 0;
    }

    /* Returns the number of items.
    
     * PRE: None.
     * POST: The number of items in the list is returned.
     */
    public int size() {
        return size;
    }

    /* Finds the first matching item; returns -1 if it is not found.

     * PRE: None.
     * POST: The index of the first occurrence of item is returned, or -1 if not found.
     */
    public int indexOf(Object item) {
        for (int i = 0; i < size; i++) {
            if (item == null ? data[i] == null : item.equals(data[i])) {
                return i;
            }
        }

        return -1;
    }

    /* Finds the last matching item; returns -1 if it is not found.

     * PRE: None.
     * POST: The index of the last occurrence of item is returned, or -1 if not found.
     */
    public int lastIndexOf(E item) {
        for (int i = size - 1; i >= 0; i--) {
            if (item == null ? data[i] == null : item.equals(data[i])) {
                return i;
            }
        }

        return -1;
    }

    /* Checks that an index points to an existing item.

     * PRE: None.
     * POST: If index is valid, the method returns without error. Otherwise, it throws an IndexOutOfBoundsException.
     */
    public void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    /* Lets the list be used in a for-each loop.

     * PRE: None.
     * POST: An iterator over the items in the list is returned.
     */
    public Iterator<E> iterator() {
        return new Iterator<E>() {
             int index = 0;
            
            public boolean hasNext() {
                return index < size; //Returns true if another item is available; otherwise false.
            }
            /**
             * PRE: Another item is available.
             * POST: Returns the next item and advances the iterator.
             *       Throws NoSuchElementException if no item remains.
             */    
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                return (E) data[index++];
            }
        };
    }
}