/**
 * @auther Yosra Alim
 * Date: July 20th, 2023
 * 
 * 
 * This class represents a generic Doubly Linked Stack (DLStack) data structure that supports push, pop, peek, isEmpty, and size operations. It utilizes a Doubly Linked List to store the elements. The class uses the
 * 
 * DoubleLinkedNode class to construct the linked list. The DLStack supports generic types, allowing the stack to contain elements of any type.
 * 
 */


public class DLStack<T> implements DLStackADT<T> {
    private DoubleLinkedNode<T> top;
    private int numItems;

    
    /**
     * Constructor for the DLStack class. Initializes the top pointer to null and the number of items to zero.
     */
    public DLStack() {
        this.top = null;
        this.numItems = 0;
    }

    
    /**
     * Adds a new element to the top of the stack.
     *
     * @param dataItem The data item to be pushed onto the stack.
     */
    public void push(T dataItem) {
        DoubleLinkedNode<T> newNode = new DoubleLinkedNode<>(dataItem);
        if (this.top != null) {
            newNode.setNext(this.top);
            this.top.setPrevious(newNode);
        }
        this.top = newNode;
        numItems++;
    }

    
    
    /**
     * Removes and returns the top element from the stack.
     *
     * @return The top element that was removed from the stack.
     * @throws EmptyStackException If the stack is empty.
     */
    public T pop() throws EmptyStackException {
        if (isEmpty())
            throw new EmptyStackException("Stack is empty.");
        T data = this.top.getElement();
        this.top = this.top.getNext();
        if (this.top != null)
        	this.top.setPrevious(null);
        this.numItems--;
        return data;
    }

    public T pop(int k) throws InvalidItemException {
        if (k <= 0 || k > this.numItems)
            throw new InvalidItemException("Invalid index k.");
        if (k == 1)
            return pop();
        DoubleLinkedNode<T> current = this.top;
        for (int i = 1; i < k; i++)
            current = current.getNext();
        T data = current.getElement();
        DoubleLinkedNode<T> prev = current.getPrevious();
        DoubleLinkedNode<T> next = current.getNext();
        if (prev != null)
            prev.setNext(next);
        if (next != null)
            next.setPrevious(prev);
        this.numItems--;
        return data;
    }

    
    public T peek() throws EmptyStackException {
        if (isEmpty())
            throw new EmptyStackException("Stack is empty.");
        return top.getElement();
    }

    
    public boolean isEmpty() {
        return this.numItems == 0;
    }

    
    public int size() {
        return this.numItems;
    }

    
    public DoubleLinkedNode<T> getTop() {
        return this.top;
    }

    
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        DoubleLinkedNode<T> current = this.top;
        while (current != null) {
            sb.append(current.getElement());
            current = current.getNext();
            if (current != null)
                sb.append(" ");
        }
        sb.append("]");
        return sb.toString();
    }
}


