public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int head; //always points to the head of a list
    private int back; //always points to the tail of a list


    /** create a double length of the list when head == tail, and copy it into newitems*/
    private void doubleLength() {
        assert head == back;
        int H = head;
        int L = items.length;
        int R = L - H; // num of elements to the right of H
        int newlength = 2 * L;
        T[] newitems = (T []) new Object[newlength];
        System.arraycopy(items, H, newitems, 0, R); //copy left
        System.arraycopy(items, 0, newitems, R, H);  //copy right
        items = newitems;
        head = 0;
        back = L;
    }


    /** create a half length of the list when R is less than 25% */
    private void halfLength() {
        int H = head;
        int L = back;
        int R = L - H;
        int newlength = items.length / 2;
        T[] newitems = (T []) new Object[newlength];
        System.arraycopy(items, H, newitems, 0, R);
        if (head > back) {
            System.arraycopy(items, 0, newitems, R, H);  //copy right
        }
        items = newitems;
        head = 0;
        back = size;
    }


    /** create an empty list */
    public ArrayDeque() {
        items = (T []) new Object[8];
        size = 0;
    }


    /** adds an items of type T to the front of the deque */
    public void addFirst(T item) {
        size = size + 1;

        if (head - 1 < 0) {
            head = items.length - 1;
        } else {
            head = head - 1;
        }

        items[head] = item;

        if (head == back) {
            doubleLength();
        }
    }

    /** adds an items of type T to the back of the deque */
    public void addLast(T item) {
        items[back] = item;
        size = size + 1;
        if (back + 1 >= items.length) {
            back = 0;
            if (back == head) {
                doubleLength();
            }
        } else {
            back = back + 1;
            if (back == head) {
                doubleLength();
            }
        }
    }


    /**  Returns true if deque is empty, false otherwise. */
    public boolean isEmpty() {
        return (head == back);
    }


    /** return the size of deque */
    public int size() {
        return size;
    }

    /** Prints the items in the deque from first to last, separated by a space.
    Once all the items have been printed, print out a new line. */
    public void printDeque() {
        if (head < back) {
            for (int i = head; i < back; i++) {
                System.out.print(items[i] + " ");
            }
        } else if (head == back) {
            System.out.print(" ");
        } else {
            for (int i = head; i < items.length; i++) {
                System.out.print(items[i] + " ");
            }
            for (int i = 0; i < back; i++) {
                System.out.print(items[i] + " ");
            }
        }
        System.out.println(" ");
        System.out.println(" ");
    }

    /** Removes and returns the item at the front of the deque.
    If no such item exists, returns null.*/
    public T removeFirst() {
        if (size - 1 < items.length / 4 && items.length >= 16) {
            halfLength();
        }

        if (size - 1 >= 0) {
            int H = head;
            T x = items[H];
            if (x == null) {
                return null;
            }
            items[H] = null;
            if (head + 1 >= items.length) {
                head = 0;
            } else {
                head = head + 1;
            }
            size = size - 1;
            return x;
        } else {
            size = 0;
            return null;
        }
    }

    /**Removes and returns the item at the back of the deque.
    If no such item exists, returns null. */
    public T removeLast() {
        if (size - 1 < items.length / 4 && items.length >= 16) {
            halfLength();
        }

        if (size - 1 >= 0) {
            int t = (back - 1) & (items.length - 1);
            if (back - 1 < 0) {
                back = items.length - 1;
            }
            T x = (T) items[t];
            if (x == null) {
                return null;
            }
            items[t] = null;
            back = t;
            size = size - 1;
            return x;
        } else {
            size = 0;
            return null;
        }
    }

    /** Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
    If no such item exists, returns null. Must not alter the deque! */
    public T get(int index) {
        if (head + index > items.length) {
            int x = items.length - head;
            int y = index - x;
            return items[y];
        } else if (head + index == items.length) {
            return items[0];
        } else {
            return items[head + index];
        }
    }
}
