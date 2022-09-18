
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Scanner;

public class driver {
    public static void main(String args[]) throws FileNotFoundException {

        int n=0;
        double[] arr = new double[8000];

        File file = new File("lab2in.txt");
        Scanner in = new Scanner(file);


        //This increments n to fill the array with the data in the .txt file.
        while(in.hasNextDouble()){
            arr[n] = in.nextDouble(); //fills arr[n] with the double
            n++;                      //increments n
        }


        //https://docs.oracle.com/javase/7/docs/api/java/util/Arrays.html#sort(double[])
        //^^^^^^^^^^^^^Link^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

        /*"This algorithm offers O(n log(n)) performance on many data sets that cause
        other quicksorts to degrade to quadratic performance, and is typically faster
        than traditional (one-pivot) Quicksort implementations." */
        Arrays.sort(arr); //sorts array
        double value1=0;
        double value2=0;


        double differenceTemp= arr[1]-arr[0];


        for(int i=1; i < arr.length;i++){
            double iter1 = arr[i];
            double iter2 = arr[i-1];

            if(differenceTemp > (iter1-iter2)){
                differenceTemp= iter1-iter2;
                value1 = iter1;
                value2 = iter2;
            }

        }
        DecimalFormat difference = new DecimalFormat("0.000000");
        System.out.println("The number "+ value2 + " and " + value1
        +" are the closest pair with a difference of " + difference.format(differenceTemp));

    }//main
}
