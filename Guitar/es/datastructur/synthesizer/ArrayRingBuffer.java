package es.datastructur.synthesizer;
import java.util.Iterator;


public class ArrayRingBuffer<T> implements BoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    private int fillCount;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        rb = (T[]) new Object [capacity];
        first = 0;
        last = 0;
        fillCount = 0;
    }

    /* return the size of buffer */
    @Override
    public int capacity() {
        return rb.length;
    }

    /* return number of items currently in the buffer */
    @Override
    public int fillCount() {
        return fillCount;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */

    @Override
    public void enqueue(T x) {
        if (fillCount == rb.length) {
            throw new RuntimeException("Ring buffer overflow");
        }
        rb[last] = x;
        fillCount += 1;
        if (last + 1 >= rb.length) {
            last = 0;
        } else {
            last = last + 1;
        }
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T dequeue() {
        if (fillCount - 1 >= 0) {
            int H = first;
            T x = rb[H];
            if (x == null) {
                throw new RuntimeException("Ring buffer overflow");
            }
            rb[H] = null;
            if (first + 1 >= rb.length) {
                first = 0;
            } else {
                first = first + 1;
            }
            fillCount -= 1;
            return x;
        } else {
            throw new RuntimeException("Ring buffer overflow");
        }
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T peek() {
        if (fillCount == 0) {
            throw new RuntimeException("Ring buffer underflow");
        }
        return rb[first];
    }

    public Iterator<T> iterator() {
        return new ArraySetIterator();
    }

    private class ArraySetIterator implements Iterator<T> {
        private int Pos;
        public ArraySetIterator() {
            Pos = 0;
        }

        public boolean hasNext() {
            return Pos < fillCount;
        }

        public T next() {
            T returnItem = rb[Pos];
            Pos += 1;
            return returnItem;
        }
    }

    private boolean contains(T x) {
        for(int i=0; i<fillCount; i++) {
            if (x.equals(rb[i])) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        ArrayRingBuffer<T> o = (ArrayRingBuffer<T>) other;
        if (o.fillCount() != this.fillCount()) {
            return false;
        }
        for (T item : this) {
            if (!o.contains(item)) {
                return false;
            }
        }
        return true;
    }

}




