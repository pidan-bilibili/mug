package es.datastructur.synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {

    @Test
    public void someTest() {
        ArrayRingBuffer arb = new ArrayRingBuffer(1);
        assertTrue(arb.isEmpty());
        arb.enqueue(2);
        assertTrue(arb.isFull());
        arb.dequeue();
        assertTrue(arb.isEmpty());

        ArrayRingBuffer ta = new ArrayRingBuffer(3);
        ta.enqueue(3.4);
        ta.enqueue(5.5);
        ta.enqueue(9);
        assertEquals(3.4, ta.peek());
        assertEquals(3.4, ta.dequeue());
        assertFalse(ta.isFull());
        assertEquals(3, ta.capacity());
        assertEquals(2, ta.fillCount());

        ArrayRingBuffer a = new ArrayRingBuffer(2);
        ArrayRingBuffer b = new ArrayRingBuffer(2);
        a.enqueue(9);
        b.enqueue(9);
        assertTrue(a.equals(b));
    }
}
