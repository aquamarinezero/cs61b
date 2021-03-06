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

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    public BSTMap() {
        size = 0;
        root = null;
    }

    /**
     * Removes all of the mappings from this map.
     */
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
        if (key.compareTo(bst.key) < 0) {
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
        if (key.compareTo(bst.key) < 0) {
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
        putHelper(root, key, value);
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
        } else if ((key.compareTo(bst.key) > 0) && (bst.right == null)) {
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
        if (!containsKey(key)) {
            return null;
        }
        //如果根节点就是
        Node parent = root;
        if (parent.key.equals(key)) {
            V result = parent.value;

            //左右都没有
            if (size() == 1) {
                size = size - 1;
                root = null;
                return result;
            }
            size = size - 1;
            if (parent.left == null) {
                root = parent.right;
                return result;
            }
            if (parent.right == null) {
                root = parent.left;
                return result;
            }
            Node p1 = parent.left;
            Node p2 = p1.right;
            if (p2 == null) {
                p1.right = root.right;
                root = parent.left;
                return result;
            }
            while (p2.right != null) {
                p1 = p1.right;
                p2 = p2.right;
            }
            p2.left = root.left;
            p2.right = root.right;
            p1.right = null;
            return result;
        }
        return getRemove(root, root, key);
    }

    private V getRemove(Node parent, Node child, K key) {
        //要先找到key所在位置
        if (child.key.equals(key)) {
            V result = child.value;
            size = size - 1;
            //第一种情况，没有子树，直接使上一节点为空
            if ((child.left == null) && (child.right == null)) {
                //左边
                if ((parent.left != null) && (parent.left.key.equals(key))) {
                    parent.left = null;
                    return result;
                } else {
                    parent.right = null;
                    return result;
                }
            }
            //第三种情况，只有一个子树,parent和子树直接相连
            //左边有
            if ((child.left != null) && (child.right == null)) {
                if ((parent.left != null) && (parent.left.key.equals(key))) {
                    parent.left = child.left;
                    return result;
                } else {
                    parent.right = child.left;
                    return result;
                }
            }
            //右边有
            if ((child.left == null) && (child.right != null)) {
                if ((parent.left != null) && (parent.left.key.equals(key))) {
                    parent.left = child.right;
                    return result;
                } else {
                    parent.right = child.right;
                    return result;
                }
            }
            //第四种情况，两边都有，该怎么办？找到左子树最右边的节点
            Node p1 = child.left;
            Node p2 = p1.right;
            if (p2 == null) {
                if ((parent.left != null) && (parent.left.key.equals(key))) {
                    parent.left = p1;
                } else {
                    parent.right = p1;
                }
                return result;
            }
            while (p2.right != null) {
                p2 = p2.right;
                p1 = p1.right;
            }
            p1.right = null;

            if ((parent.left != null) && (parent.left.key.equals(key))) {
                parent.left = p2;
            } else {
                parent.right = p2;
            }
            p2.right = child.right;
            p2.left = child.left;
            return result;
        } else if (key.compareTo(child.key) < 0) {
            return getRemove(child, child.left, key);
        } else {
            return getRemove(child, child.right, key);
        }
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
