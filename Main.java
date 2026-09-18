import java.util.*;

public class Main {


    public static void main(String[] args) {

        System.out.println("Welcome to Program 3: Creating MyList");
        Scanner input = new Scanner(System.in);

        String choice = " "; 
        choice = Functions.getMenuItem(input);
        while (!(choice.equals("Q"))){

            
            choice = Functions.getMenuItem(input);

        }
        input.close();
    }  

}
