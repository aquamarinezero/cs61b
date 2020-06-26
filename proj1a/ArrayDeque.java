public class ArrayDeque<T> {
    private int size;
    private T[] items;
    private int nextFirst;
    private int nextLast;
    public ArrayDeque(){
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 4;
        nextLast = 5;
    }

    public ArrayDeque(ArrayDeque other){
        size = other.size;
        items = (T[]) new Object[other.items.length];
    }

    private void resize(double RFACTOR){
        T[] newItems = (T[])new Object[(int)RFACTOR * items.length];
        //需要扩充的情况肯定是，两个指针重合了，需要在两个指针中间进行扩充，分成两段
        System.arraycopy(items,0,newItems,0,nextLast);
        System.arraycopy(items,nextFirst+1,newItems,newItems.length-(items.length-nextFirst-1),items.length-nextFirst-1);
        items = newItems;
        nextFirst = newItems.length-(items.length-nextFirst);
    }
    public void addFirst(T item){
        if(nextFirst == nextFirst)
            resize(2);
        items[nextFirst] = item;
        nextFirst = (nextFirst - 1)%items.length;
        size = size + 1;
    }

    public void addLast(T item){
        if(nextFirst == nextFirst)
            resize(2);
        items[nextLast] = item;
        nextLast = (nextLast + 1)%items.length;
        size = size + 1;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int size(){
        return size;
    }

    public void printDeque(){
        if(nextLast>nextFirst+1){
            for(int i = nextFirst + 1; i < nextLast; i++)
                System.out.println(items[i]+" ");}
        else if(nextLast < nextFirst){
            for(int i = nextFirst + 1;i < items.length;i++)
                System.out.println(items[i]+" ");
            for(int i = 0; i < nextLast;i++)
                System.out.println(items[i]+" ");
        }
    }

    public T removeFirst(){
        if(size <= 0.25 * items.length)
            resize(0.5);
        T result = items[(nextFirst + 1) % items.length];
        nextFirst = (nextFirst + 1) % items.length;
        return result;
    }

    public T removeLast(){
        if(size <= 0.25 * items.length)
            resize(0.5);
        T result = items[(nextLast - 1) % items.length];
        nextLast = (nextLast - 1) % items.length;
        return result;
    }

    public T get(int index){
        return items[index];
    }
}
