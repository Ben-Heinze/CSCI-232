import java.util.Scanner;

public class WordStack {

    public static void main(String[] args){
        Stack stack = new Stack<String>();


        System.out.println(stack.isEmpty());
        Scanner in = new Scanner(System.in);

       System.out.print("Enter a sentence:");
       String sentence = in.nextLine();

       wordStack(sentence);

    }//main


    public static void wordStack(String sentence){
        Stack words = new Stack<String>();
        String temp="";
        String gotPopped ="";


        for(int i=0; i < sentence.length(); i++){
            char letter = sentence.charAt(i);
            //to be $ or not $ $ to be is the question $ $ $

            switch(letter){
                case '$':
                    gotPopped = gotPopped+ " " +words.pop();
                case ' ':
                    if((temp == "") || (temp == " ")){
                        break;
                    }
                    temp = temp+letter;
                    words.push(temp);
                    //System.out.println("pushed :"+temp);
                    temp = "";
                    break;
                default:
                    if(i+1 >= sentence.length()){//accounts for last letter
                        temp = temp+letter;
                        //System.out.println("pushed :"+temp);
                        words.push(temp);

                    }
                    else{
                        temp = temp+letter;
                    }
            }//switch



        }
        System.out.println();

        System.out.println(gotPopped);
        System.out.print("Left on stack: " ); words.print();
    }//wordstack


}//driver
