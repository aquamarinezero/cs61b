public class LinkedListDeque<T> {
    private int size;
    private Node<T> sentinel;
    private class Node<T>{
        public T item;
        public Node<T> next;
        public Node<T> prev;

        public Node(Node<T> prev,T item,Node<T> next){
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
    public LinkedListDeque(){
        size = 0;
        sentinel = new Node<T>(null,null,null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
    }

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


    public void addFirst(T item){
        sentinel.next = new Node<T>(sentinel,item,sentinel.next);
        sentinel.next.next.prev = sentinel.next;
        size += 1;
    }

    public void addLast(T item){
        sentinel.prev = new Node<T>(sentinel.prev,item,sentinel);
        sentinel.prev.prev.next = sentinel.prev;
        size += 1;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int size(){
        return size;
    }

    public void printDeque(){
        int count = size;
        Node<T> p = sentinel.next;
        while(count != 0){
            count = count - 1;
            System.out.println(p.item+" ");
            p = p.next;
        }
        //System.out.println('\n');
    }

    public T removeFirst(){
        T result = sentinel.next.item;
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;
        size = size - 1;
        return result;
    }

    public T removeLast(){
        T result = sentinel.prev.item;
        sentinel.prev.prev.next = sentinel;
        sentinel.prev = sentinel.prev.prev;
        size = size - 1;
        return result;
    }

    public T get(int index){
        Node<T> p = sentinel.next;
        while(index!=0){
            index = index - 1;
            p = p.next;
        }
        return p.item;
    }


    /*这个迭代的方法不太会写*/
    public T getRecursive(int index){
        if(index == 0)
            return sentinel.item;
        return sentinel.item;
    }


}
