/**
 * This class use array and a top index to implement the StackADT interface
 * @param <T> The Type of elements in the stack
 */
public class ArrayStack<T> implements StackADT<T> {
    private T[] array; // this array holds the items in the stack
    private int top; // the index of the top element in the array

    /**
     * default constructor
     */
    public ArrayStack() {
        this(10);
    }

    /**
     * constructor with an initial of the capacity of this stack
     * @param initCapacity the capacity of this stack
     */
    public ArrayStack(int initCapacity) {
        this.array = (T[]) new Object[initCapacity];
        this.top = -1;
    }

    /**
     * push the element onto the stack
     *
     * @param element data item to be pushed onto stack
     */
    public void push(T element) {
        if (this.top == array.length - 1) {
            this.expandCapacity();
        }
        array[++this.top] = element;
    }

    /**
     * pop the first element of the stack
     *
     * @return the first element of the stack
     * @throws CollectionException when the stack is empty
     */
    public T pop() throws CollectionException {
        if (this.isEmpty()) {
            throw new CollectionException("Stack is empty");
        }
        T item = array[this.top];
        array[this.top--] = null;
        return item;
    }

    /**
     * peek the first element of the stack
     *
     * @return the first element of the stack
     * @throws CollectionException when the stack is empty
     */
    public T peek() throws CollectionException {
        // check is the stack is empty, throw an exception
        if (this.isEmpty()) {
            throw new CollectionException("Stack is empty");
        }
        return array[this.top];//return the top element without removing it
    }

    /**
     *
     * @return ture, if the stack is empty
     */
    public boolean isEmpty() {
        return this.top == -1;
    }

    /**
     *
     * @return the number of the elements in the stack
     */
    public int size() {
        return this.top + 1;
    }

    /**
     *
     * @return the length capacity of array
     */
    public int getCapacity() {
        return this.array.length;
    }

    /**
     *
     * @return the top index
     */
    public int getTop() {
        return this.top;
    }

    /**
     * build and return a string containing all the items in the stack starting from the top to the bottom
     * @return a string containing all the items in the stack or "Empty Stack" when the stack is empty
     */
    public String toString() {
        if (this.isEmpty()) {
            return "Empty Stack";
        }

        StringBuilder result = new StringBuilder();
        for (int i = this.top; i > -1 ; i--) {
            if (i != this.top) {
                result.append(", ");
            }
            result.append(array[i]);
        }
        return result.toString();
    }

    /**
     * a helper method, expand the current capacity when the array is full and another item needs to be pushed on
     */
    private void expandCapacity() {
        int currentCapacity = getCapacity();
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

        for (int i = 0; i < currentCapacity; i++) {
            newArray[i] = this.array[i];
        }
        this.array = newArray;
    }
}