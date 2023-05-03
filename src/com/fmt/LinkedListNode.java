package com.fmt;


/**
 * LinkedListNode
 * 
 * can be used to store any type of data
 */
public class LinkedListNode<T> {
   
    private LinkedListNode<T> next;
    private T data;

    public LinkedListNode(T data) {
        this.data = data;
        this.next = null;
    }

    public T getData() {
        return data;
    }

    public LinkedListNode<T> getNext() {
        return next;
    }

    public void setNext(LinkedListNode<T> next) {
        this.next = next;
    }


}
