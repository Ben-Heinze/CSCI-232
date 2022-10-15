import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
* author Ben Heinze
*/


public class Queue<T> {
    private T[] q; //declares generic array called q
    private int first=0; //head of queue
    private int last=0; //tail of queue
    private int len=4; //keeps track of array size
    public Queue(){
        q = (T[]) new Object[len]; //c
        }//Queue constructor

    public void enqueue(T item){

        //if the array is full, double it by transfering contents to array twice the size
        if(last == len){

            T[] temp = (T[]) new Object[len*2]; //creates larger array

            for(int i=0; i<len;i++){
                temp[i] = q[i];
            }//move contents to new array
            temp[last] = item;

            len=len*2; //reassigns length
            q = temp; //reassigns q to new array
        }

        else{
            q[last] = item; //adds item to queue
        }
        last++; //iters last

    }//enqueue
    public int getSize(){
        return last-first; //tells you how many items exist in queue
    }//getsize

    public T dequeue() {
        T value = q[first];
        q[first] = null;
        first++;
        return value;
    }//dequeue

    public void resize(){
        //remove null from front
        T[] temp = (T[]) new Object[len];
        int n = 0;

        while(q[first+n] != null){
            temp[n] = q[first+n];
            n++;
        }
        q = temp;
    }//resize
    public boolean isEmpty(){
        if(getSize()==0){
            return true;
        }
        return false;
    }

    //This traverses through every item in queue and writes them to a file called "coivd-19.txt"
    public void writeToFile() throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter("covid-19.txt"));
        for(T i : q){

            if(i!=null){
                out.write(i.toString());
                out.newLine();
            }
            else{
                continue;
            }

        }//for every element in queue
        out.close();
    }//writeToFile

    //print method for testing
    public void print(){
        for (T i : q){
            System.out.println(i);
        }
    }

}//classQueue<T>
