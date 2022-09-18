import java.util.Scanner;

public class QueueDriver {

    public static void main(String[] args){
        Queue<Float> q = new Queue<>();


        System.out.println("enqueue manually or automatically (m or a)?");
        Scanner in = new Scanner(System.in);
        String answer = in.nextLine(); //gates answer

        switch (answer){
            //allows you to enter numbers manually.
            case "m":
            case "M":
                float num;
                do {
                    System.out.println("Enter 0 to stop.");
                    System.out.print("Enter float: ");
                    Scanner in2 = new Scanner(System.in);
                    num = in2.nextFloat();
                    if(num != 0) {
                        q.enqueue(num);
                    }
                }while(num !=0);
                break;

                //cases to select automatic
            case "a":
            case "A":
            default:
                q.enqueue((float) 4.5);
                q.enqueue((float) 2.6);
                q.enqueue((float) 3.2);
                q.enqueue((float) 7.2);
                q.enqueue((float) 15.5);
                q.enqueue((float) 17.2);
                q.enqueue((float) 2.7);
                q.enqueue((float) 16.2);
                q.enqueue((float) 13.3);
        }



        float runningTotal=0; //counts running total

        //enqueues the numbers from the lab1 document


        //this loops through the array and dequeues one item at a time, printing the running total each time.
        do{
            float temp = q.dequeue();
            runningTotal +=temp;
            System.out.println(temp + " dequeued, running total is "+runningTotal);
        }while(q.getSize()!= 0);

    }//main
}//class
