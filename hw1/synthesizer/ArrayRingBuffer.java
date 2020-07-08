package synthesizer;
import java.util.Iterator;


public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        first = 0;
        last = 0;
        fillCount = 0;
        rb = (T[]) new Object[capacity];
        this.capacity = capacity;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */
    public void enqueue(T x) {
        if (isFull()) {
            throw new RuntimeException("Ring buffer overflow");
        }
        rb[last] = x;
        last = (last + 1 + capacity) % capacity;
        fillCount = fillCount + 1;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        T item = rb[first];
        first = (first + 1 + capacity) % capacity;
        fillCount = fillCount - 1;
        return item;
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        T item = rb[first];
        return item;
    }

    @Override
    public int capacity() {
        return capacity;
    }

    @Override
    public int fillCount() {
        return fillCount;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<T> {

        private int curr = first;
        @Override
        public boolean hasNext() {
            return curr != last;
        }
        @Override
        public T next() {
            int re = curr;
            curr = (curr + 1 + capacity) % capacity;
            return rb[re];
        }
    }

    public boolean equals(Object o) {
        ArrayRingBuffer<T> p = (ArrayRingBuffer<T>) o;
        if (this.capacity() != p.capacity()) {
            return false;
        }
        if (this.isEmpty()) {
            return true;
        }
        int curr = first;
        for (T sub: p) {
            if (p != rb[curr]) {
                return false;
            }
            curr = (first + 1 + capacity()) % capacity();
        }
        return true;
    }
}

