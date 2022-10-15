/**
 * The MinQueue implements a generic array of length 3 which will iterate through
 * every line of data for a country and store the top 3 days that have the most new
 * cases.
 *
 * @author Ben Heinze
 */

public class MinPQ<Key extends Comparable<Key>> {
   // private int index=0;
    private int size; //size is the number of present elements
    private int len=3;

    private Key[] q;

    /**
     * Constructor for MinQueue, creates a generic size 3 array to store
     * the top 3 days with the most new cases along with the rest of the
     * values
     *
     * @Throws none
     *
     */
    MinPQ(){
        q = (Key[]) new Comparable[len];
        size = 0;
    }


    /**
     * isEmpty checks if any contents exist in the MinQueue.
     * If something exists, return true, else return false.
     *
     * @return true or false
     * @Throws  none
     *
     */

    public boolean isEmpty(){
        if(q[0]==null) return true;
        return false;
    }

    /**
     *
     * This is a sorted method that rearranges the array as items
     * are inserted into the queue
     *
     * @param item
     * @Throws IllegalArgumentException
     */
    public void insert(Key item){
        //if the current item in first slot is greater than 'item', skip it
        if((!isEmpty()) && (q[0].compareTo(item)>=0)){
            return;
        }


        //Code follows these procedures if the array is not full
        else if(!isFull()) {
            //if nothing is in the first slot, fill slot
            if(q[0]==null){ q[0] = item;}

            //nothing in second slot
            else if(q[1]==null){
                //if the new item is smaller than the first item, swap them
                if(q[0].compareTo(item)>0){
                    q[1] = q[0];
                    q[0]=item;
                }
                //inserts without switching order of q
                else{q[1] = item;}
            }

            //nothing in third slot
            else if(q[2] ==null){
                //if second element is smaller than the new item, insert new item into third slot


                //if the second element is greater than the new item,  swap second and third slot
                if(q[1].compareTo(item)>0){
                    q[2] = q[1];
                    q[1]= item;
                }

                //inserts without switching order
                else{
                    q[2]=item;
                }

                //if the new second slot is smaller than the first slot, swap first and second slot
                if(q[0].compareTo(q[1])>0){
                    Key temp = q[1];
                    q[1] = q[0];
                    q[0] = temp;
                }
            }

        }// array not Full
        else if(isFull()) {

            //deletes the minimum
            delMin();

            //if item is greater than second and third item, insert item in last slot
            if((item.compareTo(q[0])>0) && (item.compareTo(q[1])>0)){ q[2] = item;}

            //if item is greater than first, but less than second, move current second to third
            else if((item.compareTo(q[0])>0) && (item.compareTo(q[1])<0)){
                q[2] = q[1];
                q[1] = item;
            }

            //if item is less than first,
            else{
                q[2]= q[1];
                q[1]= q[0];
                q[0] = item;
            }

        }// array is full
        size++;

    }//insert


    /**
     *
     * locates lowest key in the queue and removes it
     * @return index : returns index of whatever key was removed.
     * @Throws none
     */
    public Key delMin(){
        Key result = q[0];// stores the minimum value
        size--;
        //moves the two other values down one slot

        q[0] = q[1]; //move
        q[1] = q[2];
        q[2] = null;

        //System.out.println("Array after delmin [[[ [[[ [[[[ [[[[ [[[ [[[[ [[[ [[[[ [[[ [[[[ [[[ [[[ [[[[ ");
        //print();
        return result;
        //if there is something in the array
        //System.out.println(size);
    }

    /**
     * Clears all elements currently present in array
     */
    public void clear(){
        for(int i=0;i<len;i++){
            q[i]=null;
        }

    }

    public int getSize(){
        return size;
    }
    public Key getVal(int index){
        return q[index];
    }


    public boolean isFull(){
        if (q[2]==null){
            return false;
        }
        return true;
    }

    /**
     *
     * For testing
     */

    public void print(){
        int iter=0;
        while(iter!=len){
            System.out.println(q[iter]);
            iter++;
        }
    }




}


