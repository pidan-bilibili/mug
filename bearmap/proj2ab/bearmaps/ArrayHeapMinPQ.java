package bearmaps;

import java.util.*;

/** 1) the ArrayHeapMinPQ is a heap.
 *  2) Principle: the smallest priority will always be on the top of the tree
 *  3) Principle: the children's priority will always bigger than it's children.
 *  4) if two Node have the same priority, then it could be at any position.
 *  as long as it keep the the Principle.
 *  So swimUp and swimDown will not include the situation of same priority */

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {

    /** the set the map method will do the same thing as array does */
    private final ArrayList<Node> array; // use ArrayList to create a heap.
    private int size; // keep track the size of the heap.
    private final HashSet<T> set; // use set data structure to optimize contain method.
    private final HashMap<T, Integer> map; // use map data structure to optimize the ChangePriority method.

    /** ArrayHeapMinPQ constructor  */
    public ArrayHeapMinPQ() {
        size = 0;
        array = new ArrayList<>(); // contain Node which contain item and it's priority.
        set = new HashSet<>(); // contain only item.
        map = new HashMap<>(); // contain item as the key and priority as it's item.
    }



    /** create a Node class which contain the item and it's priority */
    private class Node {
        private final T item;
        private double priority;

       // Node constructor.
        private Node(T item, double priority) {
            this.item = item;
            this.priority = priority;
        }
    }






    /** Adds an item of type T with the given priority. If the item already exists,
     * throw an IllegalArgumentException. You may assume that item is never null.
     * the first item added in the ArrayList will have index 0
     * Since the new item will be at the index of size - 1, so it need to check if its
     * priority is bigger than it's parents' */
    @Override
    public void add(T item, double priority) {
        if (this.contains(item)) {
            throw new IllegalArgumentException();
        }

        Node n = new Node(item, priority);
        array.add(n);
        set.add(item);
        map.put(item, size);
        size = size + 1;

        // make sure that the tree maintain heap's principle.
        if (this.size > 0) {
            swimUp(size() - 1);
        }
    }




    /** helper method return k's parent */
    private int parent(int k) {
        return (k - 1) / 2;
    }


    /** helper method if k's parent priority is greater than k then swim this two elements */
    private void swimUp(int k) {
        if (array.get(parent(k)).priority > array.get(k).priority) {
            map.put(array.get(k).item, parent(k)); // swap two elements in map.
            map.put(array.get(parent(k)).item, k); // swap two elements in map.
            Collections.swap(array, k, parent(k)); // swap two elements in ArrayList.
            swimUp(parent(k)); // swimUP until the tree maintain the heap principle.
        }
    }


    /** Returns true if the PQ contains the given item. */
    @Override
    public boolean contains(T item) {
        return set.contains(item);
    }




    /** Returns the item with smallest priority.
     * If no items exist, throw a NoSuchElementException. */
    @Override
    public T getSmallest() {
        if (size == 0) {
            throw new NoSuchElementException();
        }

        return array.get(0).item;
    }

    /** helper method for remove. */
    private void swimDown(int k) {
        int min;
        if (k >= array.size()) {
            return;
        }

        // get the smallest priority of the three Node.
        // make sure that if it's two children is null it will return something.
        if (getLeft(k) > size && getRight(k) > size) {
            min = k;
        } else {
            min = min(k, min(getLeft(k), getRight(k)));
        }

        // if k is not at the minimum's position, then swap two position.
        // NOTE: the map will also do the same replacement.
        if (k != min) {
            map.put(array.get(k).item, min);
            map.put(array.get(min).item, k);
            Collections.swap(array, k, min);
            swimDown(min);
        }
    }

    /** return the smaller priority
     * NOTE: this method does not contain what if the node1 and node2 are both null
     * so it should add a 'both null' condition when this method is called. */
    private int min(int i, int j) {
        Node node1 = getNode(i);
        Node node2 = getNode(j);

        if (node1 == null) {
            return j;
        } else if (node2 == null) {
            return i;
        } else if (node1.priority > node2.priority) {
            return j;
        }  else {
            return i;
        }
    }


    /** get the left child of k */
    private int getLeft(int k) {
        if (k == 0) {
            return 1;
        } else {
            return (k + 1) * 2 - 1;
        }
    }

    /** get the right child of k */
    private int getRight(int k) {
        if (k == 0) {
            return 2;
        } else {
            return (k + 1) * 2;
        }
    }

    /** get Node of index, if not exist return null */
    private Node getNode(int k) {
        if (k >= size) {
            return null;
        } else {
            return array.get(k);
        }
    }



    /** Removes and returns the item with smallest priority.
     * If no items exist, throw a NoSuchElementException
     * This will put the last Node replace the position of index 0
     * then remove the position of last Node
     * NOTE: set and map will also do the same */
    @Override
    public T removeSmallest() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        } else if (this.size == 1) { // when there is only one item in the ArrayList, no swimDown
            size = 0;
            set.remove(array.get(0).item);
            map.remove(array.get(0).item);
            T x = array.get(0).item;
            array.remove(0);
            return x;
        } else {
            size = size - 1;
            set.remove(array.get(0).item);
            map.remove(array.get(0).item);
            T x = array.get(0).item;
            array.set(0, array.get(size)); // replace smallest priority to the last item.
            map.replace(array.get(size).item, 0); // change the value of the last key.
            array.remove(size);
            swimDown(0); // swimDown until the tree keep the heap's principle.
            return x;
        }
    }




    /** Returns the number of items. */
    @Override
    public int size() {
        return size;
    }




    /** Sets the priority of the given item to the given value.
     * If the item does not exist, throw a NoSuchElementException.
     * use HashMap to keep get the index of a corresponding item.
     * if the new priority is greater than the old priority then check if its greater than it's children
     * since the parent's priority is always greater than the Children's. Vice-versa */
    @Override
    public void changePriority(T item, double priority) {
        if (!this.contains(item)) {
            throw new NoSuchElementException();
        }

        int i = map.get(item); // get the index.
        double old = array.get(i).priority;
        array.get(i).priority = priority; // set the new priority.

        if (old < priority) {
            swimDown(i);
        } else {
            swimUp(i);
        }
    }


    /** check some simple points */
    public static void main(String args[]) {
        ArrayHeapMinPQ nt = new ArrayHeapMinPQ();
        nt.add("a", 0); // changed into 90
        nt.add("b", 0.1);
        nt.add("c", 2);
        nt.add("d", 0.3); // changed into 5.5
        nt.add("e", 0.4);
        nt.add("f", 5);
        nt.add("g", 6);
        nt.add("n", 7);
        nt.changePriority("a", 90);
        System.out.println(nt.getSmallest()); // it should return b
        System.out.println(nt.removeSmallest()); // it should return b
        nt.changePriority("d", 5.5);
        System.out.println(nt.getSmallest()); // it should return e
        System.out.println(nt.removeSmallest()); // it should return e
        System.out.println(nt.getSmallest()); // it should return c
        System.out.println(nt.removeSmallest()); // it should return c
        System.out.println(nt.getSmallest()); // it should return f
        System.out.println(nt.removeSmallest()); // it should return f
        System.out.println(nt.getSmallest()); // it should return d
        System.out.println(nt.removeSmallest()); // it should return d
        System.out.println(nt.getSmallest()); // it should return g
        System.out.println(nt.removeSmallest()); // it should return g
        System.out.println(nt.getSmallest()); // it should return n
        System.out.println(nt.removeSmallest()); // it should return n
        System.out.println(nt.getSmallest()); // it should return a
        System.out.println(nt.removeSmallest()); // it should return a

        // comment this two. "add '//' at front.
        System.out.println(nt.getSmallest()); // return NoSuchElementException.
        System.out.println(nt.removeSmallest()); // return NoSuchElementException.


        // This will not print any thing unless comment the last two call in nt
        System.out.println();
        System.out.println();
        ArrayHeapMinPQ sb = new ArrayHeapMinPQ();
        sb.add("a", 10);
        sb.add("b", 100);
        sb.add("c", 1000);
        sb.add("d", 10000);
        System.out.println(sb.removeSmallest()); // return a
        System.out.println(sb.removeSmallest()); // return b
        sb.changePriority("c", -100);
        sb.changePriority("d", -10000);
        System.out.println(sb.getSmallest()); // return d
        System.out.println(sb.getSmallest()); // return d
    }

}
