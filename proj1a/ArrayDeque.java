public class ArrayDeque<T> {
    private int size;
    private T[] items;
    private int nextFirst;
    private int nextLast;
    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 4;
        nextLast = 5;
    }
    /*
    public ArrayDeque(ArrayDeque other){
        size = other.size;
        items = (T[]) new Object[other.items.length];
    }
    */
    private void resizeBig(double rfactor) {
        T[] newItems = (T[]) new Object[(int) (rfactor * items.length)];
        //需要扩充的情况肯定是，两个指针重合了，需要在两个指针中间进行扩充，分成两段
        System.arraycopy(items, 0, newItems, 0,  nextLast);
        int temp = items.length - nextFirst - 1;
        System.arraycopy(items, nextFirst + 1, newItems, newItems.length - temp, temp);
        nextFirst = newItems.length - (items.length - nextFirst);
        items = newItems;
    }

    private void resizeSmall(double rfactor) {
        T[] newItems = (T[]) new Object[(int) (rfactor * items.length)];
        //需要缩小分成两种情况，左和右
        //fisrt<last
        if (nextFirst < nextLast) {
            System.arraycopy(items, nextFirst + 1, newItems, 1, size);
            nextFirst = 0;
            nextLast = (1 + size) % newItems.length;
        } else {
            System.arraycopy(items, 0, newItems, 0, nextLast);
            int temp = items.length - nextFirst - 1;
            System.arraycopy(items, nextFirst + 1, newItems, newItems.length - temp, temp);
            nextFirst = (newItems.length - (items.length - nextFirst));
        }
        items = newItems;
    }

    //JAVA里面取余的概念和C里面不一样，负数%正数还是负数，坑爹
    //所有涉及到-1的操作，记得加上一个8，防止出现负数的情况
    public void addFirst(T item) {
        if (nextFirst == nextLast) {
            resizeBig(2);
        }
        items[nextFirst] = item;
        nextFirst = (nextFirst - 1 + items.length) % items.length;
        size = size + 1;
    }

    public void addLast(T item) {
        if (nextFirst == nextLast) {
            resizeBig(2);
        }
        items[nextLast] = item;
        nextLast = (nextLast + 1) % items.length;
        size = size + 1;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        if (nextLast > nextFirst + 1) {
            for (int i = nextFirst + 1; i < nextLast; i++) {
                System.out.print(items[i] + " ");
            }
        } else if (nextLast < nextFirst) {
            for (int i = nextFirst + 1; i < items.length; i++) {
                System.out.print(items[i] + " ");
            }
            for (int i = 0; i < nextLast; i++) {
                System.out.print(items[i] + " ");
            }
        }
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        if (size <= 0.25 * items.length) {
            resizeSmall(0.5);
        }
        T result = items[(nextFirst + 1) % items.length];
        nextFirst = (nextFirst + 1) % items.length;
        size = size - 1;
        return result;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        if (size <= 0.25 * items.length) {
            resizeSmall(0.5);
        }
        T result = items[(nextLast - 1 + items.length) % items.length];
        nextLast = (nextLast - 1 + items.length) % items.length;
        size = size - 1;
        return result;
    }

    public T get(int index) {
        if (index >= size) {
            return null;
        }
        return items[(nextFirst + 1 + index) % items.length];
    }
/*
    public static void main(String[] args){
        ArrayDeque<String> ad = new ArrayDeque<String>();
        ad.addLast("first");
        ad.addFirst("irst");
        ad.addFirst("irst");
        ad.addFirst("irst");
        ad.addFirst("irst");
        ad.addFirst("irst");
        ad.addFirst("irst");
        ad.addFirst("second");
        ad.removeLast();
        ad.removeLast();
        ad.removeLast();
        ad.removeLast();
        ad.removeLast();
        ad.removeLast();
        ad.removeLast();
        ad.removeLast();
        boolean c = ad.isEmpty();
        String a = ad.get(0);
        System.out.println(a);
        ad.printDeque();
    }*/

}
