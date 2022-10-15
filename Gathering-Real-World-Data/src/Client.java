import java.io.*;
import java.util.Scanner;

/**
 * The client class holds the main function.
 */
public class Client {

    /**
    *@author Ben Heinze
    */

    public static void main(String [] args) throws IOException {
        //Opens file and makes it readable
        File covid = new File("owid-covid-data.csv"); //locates file
        Scanner in = new Scanner(covid);  //scans file
        System.out.println(in.nextLine());// prints and skips first line

        //creates instance of BST and MinPQ
        BST bst = new BST<Long, Country>();
        //MinPQ<Long> key = new MinPQ<Long>(); //stores int of newCases
        MinPQ<Country> pq = new MinPQ<Country>(); //stores Country class


        String line;
        //stores line location for the sake of comparing a single country's stats for minPQ
        String lastLocation = "";
        boolean firstIteration=true;

        while(in.hasNextLine()){
        //for(int k=0; k<1000;k++){
            line = in.nextLine(); //creates String of the entire line
            String[] temp = line.split(","); //turns string into array separated by "," ex)     [continent,location,date,totalCases,newCases,population]

            //assigns each column of data to a variable
            String continent = temp[0], location = temp[1], date = temp[2];
            long totalCases = Long.parseLong(temp[3]), newCases = Long.parseLong(temp[4]), population = Long.parseLong(temp[5]);

            //creates instance of country 
            Country country = new Country(continent, location, date, totalCases, newCases, population);

            //if it is the first iteration,
            if(firstIteration) {
                lastLocation = temp[1];
                firstIteration = false;
            }

            //If the lastLocation is the same as current location, insert country;
            if(lastLocation.compareTo(location)==0){
                pq.insert(country);
            }

            //Checks to see if the country in this line is different than lastLocation
            else if(lastLocation.compareTo(location)!=0){
                //loops until there are no items left in the MinQ
                while(!pq.isEmpty()){
                    Country top = pq.delMin();
                    bst.put(top.getNewCases(), top);

                }//while
                pq.insert(country); //
                lastLocation = location;
            }//else


        }//while loop

        Queue result = new Queue<Country>();
        bst.inOrder(result);// loads every item in BST into a queue (in order from least new cases to most new cases)
        //out.close();
        //.out.println("88888888888888888888888888888888888888888888888888888888888888888888888888888888888");
        result.print();//prints every item in the queue
        //System.out.println("88888888888888888888888888888888888888888888888888888888888888888888888888888888888");
        result.writeToFile(); //writes to file //output 545 is where the file breaks
        in.close();//closes scanner
    }//main

}

