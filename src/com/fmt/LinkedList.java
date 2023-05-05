package com.fmt;


import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;



/**
 * LinkedList
 * 
 * can be used to store any type of data
 * @param <T>
 */
public class LinkedList<T> implements Iterable<T>{
    
    private LinkedListNode<T> head;
    private final Comparator<T> comparator;
    private int size;

	public LinkedList(Comparator<T> comparator) {
        this.comparator = (Comparator<T>) comparator;
        this.size = 0;
    }



    public void add(T i) {
        
        if (head == null) {
            head = new LinkedListNode<T>(i);
        } else {
            // use comparator to determine where to insert
            LinkedListNode<T> current = head;
            LinkedListNode<T> previous = null;
            LinkedListNode<T> newNode = new LinkedListNode<T>(i);
    
            while (current != null) {
                if (comparator.compare(current.getData(), i) >= 0) {
                    newNode.setNext(current);
                    if (previous != null) {
                        previous.setNext(newNode);
                    } else {
                        head = newNode;
                    }
                    break;
                }
                previous = current;
                current = current.getNext();
                if (current == null) {
                    previous.setNext(newNode);
                }
            }
        }
        size++;
    }

    /**
     * search for i in the list
     * if it is found return the previous node
     * @return
     */

     private LinkedListNode<T> find(T i) {
        if (head == null) {
            return null;
        } else {
            LinkedListNode<T> current = head;
            LinkedListNode<T> previous = null;
            while (current != null) {
                if (current.getData().equals(i)) {
                    return previous;
                }
                previous = current;
                current = current.getNext();
            }
            return null;
        }
    }

    


    public boolean contains(T i) {
        return find(i) != null;
    }

    /**
     * we can get the prevous node from find
     * if previous is null, then we are not doing anything
     *  if previous is not null, then we set the next node to the next node of the previous node
     * and I dont think we need to implement this still :: manage the list with comparator
     */
    
    public void remove(T i) {
        LinkedListNode<T> previous = find(i);
        if (previous == null) {
            return;
        } else {
            LinkedListNode<T> current = previous.getNext();
            previous.setNext(current.getNext());
        }
        size--;
    }

    public int getSize(){
        return size;
    }

    public String toString() {
        String result = "";
        LinkedListNode<T> current = head;
        while (current != null) {
            result += current.getData() + " ";
            current = current.getNext();
        }
        return result;
    }

 
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private LinkedListNode<T> current = head;
            private LinkedListNode<T> previous = null;
            
            @Override
            public boolean hasNext() {
                return current != null;
            }
    
            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                T data = current.getData();
                previous = current;
                current = current.getNext();
                return data;
            }
    
            @Override
            public void remove() {
                if (previous == null) {
                    throw new IllegalStateException();
                }
                previous.setNext(current.getNext());
                current = previous;
                size--;
            }
        };
    }
    


}
