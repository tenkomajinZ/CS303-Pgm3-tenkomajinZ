import java.util.Scanner;

/*
 * Jason Nguyen
 * Date: October 11, 2026
 * Assignment: CS303 Program 3 - Dynamic Arrays / MyList
 * Some parts of this codebase were tested through Codex (OpenAI) to ensure accuracy and functionality. The code was then modified to fit the specific requirements of the assignment.
 */
public class Main {

    /**
     * Runs file selection, loads the data, and dispatches menu actions.
     * PRE: Standard input is available.
     * POST: The program ends after the user chooses Q and closes the keyboard scanner.
     */
    public static void main(String[] args) {
        System.out.println("Welcome to Program 3: Creating MyList");
        Functions.startReport();
        Scanner input = new Scanner(System.in);
        String fileName = Functions.chooseInputFile(input);
        MyArrayList<Integer> numbers = new MyArrayList<>();
        Functions.loadInputFile(fileName, numbers);

        String choice = "";
        while (!choice.equals("Q")) {
            choice = Functions.getMenuItem(input);

            if (choice.equals("A")) {
                Functions.addValue(input, numbers);
            } else if (choice.equals("B")) {
                Functions.addList(input, numbers);
            } else if (choice.equals("C")) {
                Functions.checkAll(input, numbers);
            } else if (choice.equals("D")) {
                Functions.deleteByIndex(input, numbers);
            } else if (choice.equals("E")) {
                Functions.removeList(input, numbers);
            } else if (choice.equals("F")) {
                Functions.findValue(input, numbers);
            } else if (choice.equals("M")) {
                Functions.modifyValue(input, numbers);
            } else if (choice.equals("P")) {
                Functions.printList(numbers);
            } else if (!choice.equals("Q")) {
                System.out.println("Please choose one of the listed menu options.");
            }
        }

        input.close();
    }
}
