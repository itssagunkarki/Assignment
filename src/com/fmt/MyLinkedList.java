package com.fmt;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * LinkedList
 * 
 * can be used to store any type of data
 * 
 * @param <T>
 */
public class MyLinkedList<T> implements Iterable<T> {

	private LinkedListNode<T> head;
	private final Comparator<T> comparator;
	private int size;

	/**
	 * Creates a new LinkedList with a {@link Comparator} to determine how to sort
	 * the list
	 * 
	 * @param comparator
	 */
	public MyLinkedList(Comparator<T> comparator) {
		this.comparator = (Comparator<T>) comparator;
		this.size = 0;
	}

	/**
	 * add i to the list in order using {@link Comparator}
	 * 
	 * @param i
	 */
	public void add(T i) {

		if (head == null) {
			head = new LinkedListNode<T>(i);
		} else {
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
	 * search for <code>i</code> in the list and return the node that contains
	 * 
	 * @return <code>LinkedListNode</code> that contains <code>i</code>
	 */
	private LinkedListNode<T> find(T i) {
		if (head == null) {
			return null;
		} else {
			LinkedListNode<T> current = head;
			while (current != null) {
				if (current.getData().equals(i)) {
					return current;
				}
				current = current.getNext();
			}
			return null;
		}
	}

	/**
	 * returns true if <code>i</code> is in the list
	 * 
	 * @param i
	 * @return
	 */
	public boolean contains(T i) {
		return find(i) != null;
	}

	/**
	 * remove <code>i</code> from the list if it is in the list
	 * 
	 * @param i
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

	/**
	 * returns the size of the list
	 * 
	 * @return <code>int </code> size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * returns the data in the linked list as a string
	 * 
	 * @return <code>String</code> representation of the list
	 */
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
