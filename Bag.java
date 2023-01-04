package com.company;

import java.util.Iterator;
import java.util.NoSuchElementException;

class Bag<Item> implements Iterable<Item> {
    private Node<Item> first;
    private int n;

    // linked list as a flexible data structure
    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }

    // iterator of generic items
    private class LinkedIterator implements Iterator<Item> {
        private Node<Item> current;

        public LinkedIterator(Node<Item> first) { current = first; }

        public boolean hasNext() { return current != null; }

        public void remove() { throw new UnsupportedOperationException(); }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }

        public int size() { return n; }
    }

    public Bag() {
        first = null;
        n = 0;
    }

    // adds a generic item in the bag
    @SuppressWarnings("unchecked")
    public void add(Item item) {
        Node<Item> oldFirst = first;
        first = new Node<Item>();
        first.item = item;
        first.next = oldFirst;
        n++;
    }
    public void removeAllOccurrence(Item item){
        Node<Item> currentNode = first, previous = new Node();
        Bag<Item> b = new Bag(), reverseB = new Bag();

        if (item.equals(first.item)) {
            first = currentNode.next;
            n--;
            currentNode = currentNode.next;
        }
        previous.item = currentNode.item;
        b.add(previous.item);
        currentNode = currentNode.next;
        while (currentNode != null) {
            if (item == currentNode.item) {
                previous.item = previous.item;
                n--;
            } else{
                previous.item = currentNode.item;
                b.add(previous.item);
            }
            currentNode = currentNode.next;
        }
        for (Item i: b) reverseB.add(i);
        this.first = reverseB.first;
    }

    public Iterator<Item> iterator() { return new LinkedIterator(first); }

    public boolean isEmpty() { return first == null; }
    public int size() { return n; }
    public String toString() {
        StringBuilder s = new StringBuilder();
        Node<Item> x = null;
        for (Item item : this)
            s.append(item + " ");
        
        return s + "";
    }
}