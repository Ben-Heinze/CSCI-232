import java.util.Iterator;
import java.util.NoSuchElementException;

public class Stack<Item> implements Iterable<Item> {
    private Node<Item> first;
    int size;

    public Stack(){
        first = null;
        this.size = size;
    }//constructor


    public void push(Item temp){
        if(first==null){
            first = new Node<>(temp);
        }// put first item in linked list
        else{
            Node<Item> oldFirst = first;
            first = new Node<>(temp);
            first.setNext(oldFirst);
        }//adds to front
        this.size++;
    }//push
    public Item pop(){
        if(isEmpty()) throw new NoSuchElementException("Stack underflow");
        Item item = first.getData();
        first = first.getNext();
        this.size--;
        return item;
    }

    public boolean isEmpty(){
        if(first==null){
            return true;
        }
        return false;
    }//isEmpty
    public int getSize(){
        Node<Item> iter = first;
        int count =1;
        if(first==null){
            return 0;
        } //checks if the list is empty

        while(iter.getNext() != null){ //iterates through LL until it hits the end
            count++;
            iter = iter.getNext();
        }//while
        return count;

    }//getSize
    public void print(){
        Node<Item> iter = first;

        if(iter==null){
            return;
        }
        else {
            System.out.print(iter.getData() + " ");
            while (iter.getNext() != null) {
                iter = iter.getNext();
                System.out.print(iter.getData()+" ");
            }//while
        }//else
    }//print

    @Override
    public Iterator<Item> iterator() {
        return null;
    }
}//class stacksLL





class Node<T>{
    private T data;
    private Node<T> first;
    private Node<T> next;
    public Node(T input){
        this.data = input;
        this.next = null;
    }
    public void setNext(Node n){
        next = n;
    }
    public Node getNext(){
        return next;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Data: " + data;
    }

}//Node Class
