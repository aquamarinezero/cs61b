import javax.swing.*;
import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private int size;
    private Node root;
    private class Node {
        private K key;
        private V value;
        private Node left;
        private Node right;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    public BSTMap() {
        size = 0;
        root = null;
    }

    /** Removes all of the mappings from this map. */
    @Override
    public void clear() {
        size = 0;
        root = null;
    }

    /* Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to contains() is null");
        }
        return getContainKey(root, key);
    }

    private boolean getContainKey(Node bst, K key) {
        if (bst == null) {
            return false;
        }
        if (bst.key.equals(key)) {
            return true;
        }
        if (key.compareTo(bst.key)<0) {
            return getContainKey(bst.left, key);
        } else {
            return getContainKey(bst.right, key);
        }
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to contains() is null");
        }
        if (!containsKey(key)) {
            return null;
        }
        return getHelper(root, key);
    }

    private V getHelper(Node bst, K key) {
        if (bst.key.equals(key)) {
            return bst.value;
        }
        if (key.compareTo(bst.key)<0) {
            return getHelper(bst.left, key);
        } else {
            return getHelper(bst.right, key);
        }
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        if ((key == null) || (value == null)) {
            throw new IllegalArgumentException("argument to contains() is null");
        }
        if (root == null) {
            root = new Node(key, value);
            size = size + 1;
            return;
        }
        putHelper(root, key,value);
    }

    private void putHelper(Node bst, K key, V value) {
        //如果查找到相应的key，修改对应的value，然后返回
        if (bst.key.equals(key)) {
            bst.value = value;
            return;
        }
        //如果没找到，就要一直找，直到为空
        //记得要提前一层就判断，是不是该增加节点了
        if ((key.compareTo(bst.key) < 0) && (bst.left == null)) {
            bst.left = new Node(key, value);
            size = size + 1;
            return;
        } else if ((key.compareTo(bst.key) < 0) && (bst.left != null)) {
            putHelper(bst.left, key, value);
            return;
        } else if((key.compareTo(bst.key) > 0) && (bst.right == null)) {
            bst.right = new Node(key, value);
            size = size + 1;
            return;
        } else {
            putHelper(bst.right, key, value);
            return;
        }
    }

    /* Returns a Set view of the keys contained in this map. */
    public Set<K> keySet() {
        throw new UnsupportedOperationException("unsupport operation");
    }

    /* Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException("unsupport operation");
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException("unsupport operation");
    }

    public Iterator<K> iterator() {
        throw new UnsupportedOperationException("unsupport operation");
    }

    private class BSTIterator implements Iterator<K> {
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
