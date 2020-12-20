public class LinkedListDeque<T> {

    private static class Deque<T> {
        private T item;
        private Deque<T> prev;
        private Deque<T> next;

        Deque(T i, Deque<T> n, Deque<T> m) {
            item = i;
            prev = n;
            next = m;
        }
    }


    /** the first item (if exist) is at sentinel.next */
    private Deque<T> sentinel;
    private int size;


    /** check if the list is empty */
    public boolean isEmpty() {
        return (sentinel.next == null);
    }


    /** return the size of the list */
    public int size() {
        return size;
    }


    /** create an empty deque */
    public LinkedListDeque() {
        sentinel = new Deque(64, null, null);
        size = 0;
    }


    /** add an item to the front of the list */
    public void addFirst(T T) {
        size = size + 1;
        if (isEmpty()) {
            sentinel.next = new Deque<>(T, sentinel, sentinel);
            sentinel.prev = sentinel.next;
        } else {
            sentinel.next = new Deque<>(T, sentinel, sentinel.next);
            sentinel.next.next.prev = sentinel.next;
        }
    }


    /** add an item to the back of the list */
    public void addLast(T T) {
        size = size + 1;
        if (isEmpty()) {
            sentinel.next = new Deque<>(T, sentinel, sentinel);
            sentinel.prev = sentinel.next;
        } else {
            sentinel.prev = new Deque<>(T, sentinel.prev, sentinel);
            sentinel.prev.prev.next = sentinel.prev;
        }
    }


    /** Removes and returns the item at the front of the deque.
     If no such item exists, returns null */
    public T removeFirst() {
        size = size - 1;
        if (size >= 0) {
            if (this.isEmpty()) {
                return null;
            } else if (size == 0) {
                T i = sentinel.next.item;
                sentinel.next = null;
                sentinel.prev.next = null;
                sentinel.prev.prev = null;
                sentinel.prev = null;
                return i;
            } else {
                T i = sentinel.next.item;
                sentinel.next = sentinel.next.next;
                sentinel.next.prev.next = null;
                sentinel.next.prev.prev = null;
                sentinel.next.prev = sentinel;
                return i;
            }
        } else {
            size = 0;
            return null;
        }
    }


    /** Removes and returns the item at the back of the deque.
     If no such item exists, returns null. */
    public T removeLast() {
        size = size - 1;
        if (size >= 0) {
            if (this.isEmpty()) {
                return null;
            } else if (size == 0) {
                T j = sentinel.prev.item;
                sentinel.prev = null;
                sentinel.next.prev = null;
                sentinel.next.next = null;
                sentinel.next = null;
                return j;
            } else {
                T j = sentinel.prev.item;
                sentinel.prev = sentinel.prev.prev;
                sentinel.prev.next.prev = null;
                sentinel.prev.next.next = null;
                sentinel.prev.next = sentinel;
                return j;
            }
        } else {
            size = 0;
            return null;
        }
    }


    /**  Prints the items in the deque from first to last, separated by a space.
     Once all the items have been printed, print out a new line. */
    public void printDeque() {
        Deque<T> printdeque = sentinel;
        if (sentinel.next != null) {
            for (int i = 0; i < size; i++) {
                if (printdeque.next != printdeque) {
                    System.out.print(printdeque.next.item + " ");
                    printdeque = printdeque.next;
                }
            }
            System.out.println();
            System.out.println();
        } else {
            System.out.println();
        }
    }


    /**  Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     If no such item exists, returns null. Must not alter the deque! */
    public T get(int index) {
        Deque<T> getdeque = sentinel;
        if (index < size) {
            int i = 0;
            while (i < index) {
                getdeque = getdeque.next;
                i = i + 1;
            }
            return getdeque.next.item;
        } else {
            return null;
        }
    }


    /** Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     If no such item exists, returns null. Must not alter the deque! (recursive) */
    public T getRecursive(int index) {
        Deque<T> getdeque = sentinel;
        if (index < size) {
            for (int i = 0; i < index; i++) {
                getdeque = getdeque.next;
            }
            return getdeque.next.item;
        } else {
            return null;
        }
    }


}

