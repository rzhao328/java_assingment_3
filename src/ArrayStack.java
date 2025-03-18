/**
 * This class use array and a top index to implement the StackADT interface
 * @param <T> The Type of elements in the stack
 */
public class ArrayStack<T> implements StackADT<T> {
    private T[] array; // this array holds the items in the stack
    private int top; // the index of the top element in the array

    /**
     * default constructor initializes the stack with a capacity of 10
     */
    public ArrayStack() {
        this(10);
    }

    /**
     * constructor with an initial of the capacity of this stack
     * @param initCapacity the capacity of this stack
     */
    public ArrayStack(int initCapacity) {
        // Create a new array of type T with the given capacity
        this.array = (T[]) new Object[initCapacity];
        //Set top to the rightmost available index
        this.top = initCapacity - 1;
    }

    /**
     * push the element onto the stack
     *
     * @param element data item to be pushed onto stack
     */
    public void push(T element) {
        // if the stack is full (no available position), expand its capacity
        if (this.top < 0) {
            this.expandCapacity();
        }
        // Store the element at the rightmost available position and update top
        array[top--] = element;
    }

    /**
     * removes and return the first element of the stack
     *
     * @return the first element of the stack
     * @throws CollectionException when the stack is empty
     */
    public T pop() throws CollectionException {
        if (this.isEmpty()) {
            throw new CollectionException("Stack is empty");
        }
        // Retrieve the current top element
        T item = array[++top];
        array[top] = null; // Remove reference to allow garbage collection
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
        return array[top + 1];//return the top element without removing it
    }

    /**
     * Checks if the stack is empty
     *
     * @return ture, if the stack is empty
     */
    public boolean isEmpty() {
        return this.top == array.length - 1;
    }

    /**
     *
     * @return the number of the elements in the stack
     */
    public int size() {
        return array.length - 1 - this.top;
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
        // Traverse from the first element to the bottom
        for (int i = this.top + 1; i < array.length ; i++) {
            if (i != this.top + 1) {
                result.append(", ");
            }
            result.append(array[i]);
        }
        return result.toString();
    }

    /**
     * Expands the capacity of the stack when it is full
     * - if the current capacity is less than 15, it doubles the capacity
     * - otherwise, it expands bt adding 10 additional slots
     * - ensures that elements remain stored in the rightmost positions
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

        // Create a new array with the updated capacity
        T[] newArray = (T[]) new Object[newCapacity];

        // Shift elements to rightmost positions in the new array
        int newTop = newCapacity - size() - 1;
        for (int i = newTop + 1, j = top + 1; j < array.length; i++, j++) {
            newArray[i] = array[j];
        }
        this.array = newArray; // update the reference
        this.top = newTop; // update top index
    }
}