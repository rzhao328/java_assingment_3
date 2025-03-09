import java.util.Arrays;

public class implement ArrayStack<T> {
    private T[] array; // this array holds the items in the stack
    private int top;

    public ArrayStack() {
        this(10);
        top = -1;
    }

    public ArrayStack(int initCapacity) {
        array = (T[]) new Object[initCapacity];
        top = -1;
    }

    public void push(T element) {

    }

    public T pop() throws CollectionException {
        if (top == array.length - 1) throw new CollectionException("Stack is empty");

        T item = array[top + 1];
        array[top + 1] = null;
        top++;

        return item;
    }

    public T peek() throws CollectionException {
        if (top == array.length - 1) {
            throw new CollectionException("Stack is empty");
        }
            // check is the stack is empty, throw an exception
        return array[top];//return the top element without removing it
    }

    public boolean isEmpty() {
        if (top == -1) return true;
        else return false;
    }

    public int size() {
        return top + 1;
    }

    public int getCapacity() {
        return array.length;
    }

    public int getTop() {
        return top;
    }

    public String toString() {
        if (top == -1) return "Empty Stack";

        StringBuilder result = new StringBuilder();
        for (int i = top + 1; i < array.length; i++){
            result.append(array[i]);
            if (i < array.length - 1){
                result.append(", ");
            }
        }
            return array[top].toString();
    }

    private void expandCapacity() {
        int currentCapacity = array.length;
        int newCapacity;

        //if the current array capacity is 15 or less, then expand it by doubling its current capacity
        if (currentCapacity <= 15){
            newCapacity = currentCapacity * 2;
        }
        // otherwise, expand it by adding 10 additional spaces in the array
        else {
            newCapacity = currentCapacity + 10;
        }
        T[] newArray = (T[]) new Object[newCapacity];

        int shift = newCapacity - currentCapacity;
        for (int i = 0; i < currentCapacity; i++) {
            newArray[i + shift] = array[i];
        }
        array = newArray;
        top = top + shift;
    }
}