package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<Integer> x = new ArrayRingBuffer<Integer>(4);
        x.enqueue(33); // 33.1 null null  null
        x.enqueue(44); // 33.1 44.8 null  null
        x.enqueue(62); // 33.1 44.8 62.3  null
        x.enqueue(-3); // 33.1 44.8 62.3 -3.4
        ArrayRingBuffer<Integer> y = new ArrayRingBuffer<Integer>(4);
        y.enqueue(33); // 33.1 null null  null
        y.enqueue(44); // 33.1 44.8 null  null
        y.enqueue(62); // 33.1 44.8 62.3  null
        y.enqueue(-3); // 33.1 44.8 62.3 -3.4
        assertTrue(x.isFull());
        assertTrue(y.equals(x));
        int result = x.dequeue();
        assertEquals(33, result);     // 44.8 62.3 -3.4  null (returns 33.1)
    }
}
