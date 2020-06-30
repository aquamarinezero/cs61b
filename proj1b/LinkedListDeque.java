public class LinkedListDeque<Item> implements Deque<Item> {
    private int size;
    private Node<Item> sentinel;
    private class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
    public LinkedListDeque() {
        size = 0;
        sentinel = new Node<Item>(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
    }
    /*
    public LinkedListDeque(LinkedListDeque other){
        this.size = other.size;
        this.sentinel = new Node(null,null,null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        Node<T> p = other.sentinel.next;
        while(size!=0){
            this.addLast(p.item);
            size--;
        }
    }
    */

    @Override
    public void addFirst(Item item) {
        sentinel.next = new Node<Item>(sentinel, item, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
        size += 1;
    }

    @Override
    public void addLast(Item item) {
        sentinel.prev = new Node<Item>(sentinel.prev, item, sentinel);
        sentinel.prev.prev.next = sentinel.prev;
        size += 1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        int count = size;
        Node<Item> p = sentinel.next;
        while (count != 0) {
            count = count - 1;
            System.out.println(p.item + " ");
            p = p.next;
        }
        //System.out.println('\n');
    }

    @Override
    public Item removeFirst() {
        Item result = sentinel.next.item;
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;
        if (size != 0) {
            size = size - 1;
        }
        return result;
    }

    @Override
    public Item removeLast() {
        Item result = sentinel.prev.item;
        sentinel.prev.prev.next = sentinel;
        sentinel.prev = sentinel.prev.prev;
        if (size != 0) {
            size = size - 1;
        }
        return result;
    }

    @Override
    public Item get(int index) {
        Node<Item> p = sentinel.next;
        while (index != 0) {
            index = index - 1;
            p = p.next;
        }
        return p.item;
    }

    /*帮助递归*/
    private Item getPass(Node<Item> p, int index) {
        if (index == 0) {
            return p.next.item;
        }
        return getPass(p.next, index - 1);

    }
    /*这个迭代的方法不太会写*/
    public Item getRecursive(int index) {
        return getPass(sentinel, index);
    }

}
