import java.util.Scanner;

public class MathStack {

    public static void main(String[] args){
        String temp=""; // will store number values
        char iter; //iterates through every character in a string.
        int num; //converts temp into number
        int num1;
        int num2;
        char operator; //stores operator when popping from operatorStack
        int solution=0;

        Stack valueStack = new Stack<Integer>();
        Stack opStack = new Stack<Character>();

        System.out.println("Enter calculation: ");
        Scanner in = new Scanner(System.in);
        String sentence = in.nextLine();



        for(int i=0; i < sentence.length(); i++){
            iter = sentence.charAt(i);

            switch (iter){
                case '+':
                case '-':
                case '/':
                case'*':
                    opStack.push(iter);

                case ')':
                    num1= (Integer) valueStack.pop();
                    num2 = (Integer)valueStack.pop();
                    operator = (Character) opStack.pop();

                    switch (operator){
                        case '+':
                            solution = num2+num1;
                            break;
                        case '-':
                            solution = num2-num1;
                            break;
                        case'/':
                            solution = num2/num1;
                            break;
                        case '*':
                            solution = num2*num1;
                            break;
                        default:
                            System.out.println("Default op switch");
                    }//switch
                    //System.out.println("solution:"+solution);
                    valueStack.push(solution);
                    if(i+1 >= sentence.length())
                        System.out.println("The result of the postfix equation is "+solution);

                    break;
                case ' ':
                    if(temp == "")
                        break;
                    //temp = temp+iter;
                    else if((temp != " ") ||(temp != "")){
                        valueStack.push(Integer.valueOf(temp));
                    }

                    //System.out.println("pushed :"+temp);
                    temp = "";
                    break;
                case '(':
                    break;
                default:
                    temp = temp+iter;
                    if(i+1 >= sentence.length()){ //checks if the item is the last in String.
                        valueStack.push(Integer.valueOf(temp));
                        //System.out.println("pushed :"+temp+".default");
                    }
                    //num = Integer.parseInt(temp);
                    break;
            }

        }//for
        //valueStack.print();
        //opStack.print();


    }//main
}//mathStack class
