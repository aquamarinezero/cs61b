import javax.swing.text.html.HTMLDocument;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MyHashMap<K extends Comparable<K>,V> implements Map61B<K, V> {
    //数据结构是，数组的每个值代表了一个入口，入口内部是由链表组成的
    private Node<K,V>[] bucket;
    private int initialSize;
    private double loadFactor;
    private int size;
    private class Node<K,V> {
        private K key;
        private V value;
        private Node next;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            next = null;
        }
    }
    public MyHashMap() {
        this.initialSize = 16;
        this.loadFactor = 0.75;
        this.size = 0;
        this.bucket = new Node[initialSize];
        for(int i = 0; i < initialSize; i++) {
            bucket[i] = null;
        }
    }
    public MyHashMap(int initialSize) {
        this.initialSize = initialSize;
        this.loadFactor = 0.75;
        this.size = 0;
        this.bucket = new Node[initialSize];
        for(int i = 0; i < initialSize; i++) {
            bucket[i] = null;
        }
    }
    public MyHashMap(int initialSize, double loadFactor) {
        this.initialSize = initialSize;
        this.loadFactor = loadFactor;
        this.size = 0;
        this.bucket = new Node[initialSize];
        for(int i = 0; i < initialSize; i++) {
            bucket[i] = null;
        }
    }
    /** Removes all of the mappings from this map. */
    @Override
    public void clear() {
        this.size = 0;
        this.bucket = new Node[initialSize];
        for(int i = 0; i < initialSize; i++) {
            bucket[i] = null;
        }
    }

    /** Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key) {
        int hash = Math.floorMod(key.hashCode(),initialSize);
        Node find = bucket[hash];
        if (find == null) {
            return false;
        }
        while (find != null) {
            if (find.key.equals(key)) {
                return true;
            }
            find = find.next;
        }
        return false;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        int hash = Math.floorMod(key.hashCode(),initialSize);
        Node find = bucket[hash];
        if (find == null) {
            return null;
        }
        while (find != null) {
            if (find.key.equals(key)) {
                return (V)find.value;
            }
            find = find.next;
        }
        return null;
    }

    /** Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key,
     * the old value is replaced.
     */
    @Override
    public void put(K key, V value){
        int hash = Math.floorMod(key.hashCode(),initialSize);
        Node find = bucket[hash];
        if(!containsKey(key)) {
            //插入完了之后resize
            if(find == null) {
                bucket[hash] = new Node(key, value);
                this.size = this.size + 1;
                double factor = this.size* 1.0/initialSize ;
                if (factor >= loadFactor) {
                    resize();
                }
                return;
            }
            while(find.next!=null) {
                find = find.next;
            }
            find.next = new Node(key,value);
            this.size = this.size + 1;
            double factor = this.size* 1.0/initialSize ;
            if (factor >= loadFactor) {
                resize();
            }
            return;
        }
        while (find != null) {
            if (find.key.equals(key)) {
                find.value = value;
                break;
            }
            find = find.next;
        }
        return;
    }

    private void resize() {
        Node<K,V>[] result = new Node[initialSize * 2];
        for (int i = 0; i < initialSize; i++) {
            Node head = bucket[i];
            while (head != null) {
                int hash = Math.floorMod(head.key.hashCode(),2*initialSize);
                Node newHead = result[hash];
                if(newHead == null) {
                    result[hash] = new Node<K,V>((K)head.key, (V)head.value);
                    head = head.next;
                    continue;
                }
                while (newHead.next != null){
                    newHead = newHead.next;
                }
                newHead.next = new Node<K,V>((K)head.key, (V)head.value);
                head = head.next;
            }
        }
        initialSize = initialSize * 2;
        bucket = result;
    }
    /** Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> result = new HashSet<K>();
        for (int i = 0; i < initialSize; i++) {
            Node head = bucket[i];
            while (head != null) {
                result.add((K)head.key);
                head = head.next;
            }
        }
        return result;
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    @Override
    public V remove(K key) {
        int hash = Math.floorMod(key.hashCode(),initialSize);
        Node find = bucket[hash];
        if(find == null) {
            return null;
        }
        if (find.key.equals(key)) {
            V result = (V)find.key;
            bucket[hash] = find.next;
            this.size = this.size - 1;
            return result;
        }
        while(find.next!=null) {
            if(find.next.key.equals(key)) {
                V result = (V)find.next.key;
                find.next = find.next.next;
                this.size = this.size - 1;
                return result;
            }
            find = find.next;
        }
        return null;
    }

    /**
     * Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.
     */
    @Override
    public V remove(K key, V value) {
        int hash = Math.floorMod(key.hashCode(),initialSize);
        Node find = bucket[hash];
        if(find == null) {
            return null;
        }
        if (find.key.equals(key) && find.value.equals(value)){
            V result = (V)find.key;
            bucket[hash] = find.next;
            this.size = this.size - 1;
            return result;
        }
        while(find.next!=null) {
            if(find.next.key.equals(key) && find.next.value.equals(value)) {
                V result = (V)find.next.key;
                find.next = find.next.next;
                this.size = this.size - 1;
                return result;
            }
            find = find.next;
        }
        return null;
    }

    public Iterator iterator(){
        return new hashIterator();
    }

    private class hashIterator<K> implements Iterator<K> {
        @Override
        public boolean hasNext() {
           return false;
        }

        @Override
        public K next() {
            return null;
        }
    }
}
