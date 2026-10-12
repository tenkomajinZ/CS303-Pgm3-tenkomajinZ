import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * Jason Nguyen
 * Date: October 11, 2026
 * Assignment: CS303 Program 3 - Dynamic Arrays / MyList
 * Some parts of this codebase were tested through Codex (OpenAI) to ensure accuracy and functionality.
 */
public class Functions {

    private Functions() { }

    /** Starts a fresh report file for this program run.
     * PRE: The program can write in its current directory.
     * POST: report.txt is created or cleared for the current run.
     */
    public static void startReport() {
        try (PrintWriter report = new PrintWriter("report.txt")) {
            report.println("CS303 Program 3 report");
        } catch (FileNotFoundException e) {
            System.out.println("Could not create report.txt.");
        }
    }

    /** Adds one result line to report.txt.
     * PRE: message is not null.
     * POST: The message is appended to report.txt when the file is writable.
     */
    private static void writeReport(String message) {
        try (PrintWriter report = new PrintWriter(new FileWriter("report.txt", true))) {
            report.println(message);
        } catch (IOException e) {
            System.out.println("Could not write to report.txt.");
        }
    }

    /** Prompts the user to choose one of the two input files.
     * PRE: input is open and connected to the keyboard.
     * POST: Returns A1input.txt or A2input.txt based on a valid choice.
     */
    public static String chooseInputFile(Scanner input) {
        while (true) {
            System.out.print("Choose an input file (1 or 2): ");
            if (input.hasNextInt()) {
                int choice = input.nextInt();
                if (choice == 1) return "A1input.txt";
                if (choice == 2) return "A2input.txt";
            } else {
                input.next();
            }
            System.out.println("Please enter 1 or 2.");
        }
    }

    /** Reads the selected file, adding integers and skipping invalid tokens.
     * PRE: fileName identifies an input file and numbers is not null.
     * POST: Valid integers are added to numbers; invalid tokens are reported and skipped.
     */
    public static void loadInputFile(String fileName, MyArrayList<Integer> numbers) {
        int invalidCount = 0;
        try (Scanner fileInput = new Scanner(new File(fileName))) {
            while (fileInput.hasNext()) {
                if (fileInput.hasNextInt()) {
                    numbers.add(fileInput.nextInt());
                } else {
                    String badValue = fileInput.next();
                    invalidCount++;
                    System.out.println("Skipping invalid value: " + badValue);
                    writeReport("Skipped invalid input value: " + badValue);
                }
            }
            String result = fileName + " was processed successfully. "
                    + numbers.size() + " valid values loaded; " + invalidCount + " invalid values skipped.";
            System.out.println(result);
            writeReport(result);
        } catch (FileNotFoundException e) {
            String error = "Error: " + fileName + " could not be opened.";
            System.out.println(error);
            writeReport(error);
        }
    }

    /** Displays the menu and reads a choice.
     * PRE: input is open and connected to the keyboard.
     * POST: Returns the choice as an uppercase string.
     */
    public static String getMenuItem(Scanner input) {
        System.out.println("\nMenu");
        System.out.println("A: Add an integer");
        System.out.println("B: Add a list of integers");
        System.out.println("C: Check whether all values are present");
        System.out.println("D: Delete by index");
        System.out.println("E: Remove a group of values");
        System.out.println("F: Find a value");
        System.out.println("M: Modify a value");
        System.out.println("P: Print the list");
        System.out.println("Q: Quit");
        System.out.print("Choose an action: ");
        return input.next().toUpperCase();
    }

    /** Prints all current items in order.
     * PRE: numbers is not null.
     * POST: Prints the list without changing it.
     */
    public static void printList(MyArrayList<Integer> numbers) {
        String result = listText(numbers);
        System.out.println(result);
        writeReport("Printed list: " + result);
    }

    /** Converts the list to its bracketed display form.
     * PRE: numbers is not null.
     * POST: Returns a string containing all list values in order.
     */
    private static String listText(MyArrayList<Integer> numbers) {
        StringBuilder result = new StringBuilder("[");
        for (int i = 0; i < numbers.size(); i++) {
            if (i > 0) result.append(", ");
            result.append(numbers.get(i));
        }
        return result.append(']').toString();
    }

    /** Prompts for and appends one integer.
     * PRE: input is open and numbers is not null.
     * POST: A valid integer is added to the end of numbers.
     */
    public static void addValue(Scanner input, MyArrayList<Integer> numbers) {
        System.out.print("Enter an integer to add: ");
        int value = readInteger(input);
        numbers.add(value);
        String result = "Added " + value + " to the list.";
        System.out.println(result);
        writeReport(result);
    }

    /** Reads a line of integers and appends valid values.
     * PRE: input is open and numbers is not null.
     * POST: All valid integers on the line are appended to numbers.
     */
    public static void addList(Scanner input, MyArrayList<Integer> numbers) {
        input.nextLine();
        System.out.print("Enter values to add separated by spaces: ");
        ArrayList<Integer> added = readIntegerLine(input.nextLine());
        for (Integer value : added) numbers.add(value);
        String result = "Items added to your list: " + added;
        System.out.println(result);
        writeReport(result);
    }

    /** Checks whether all entered values occur in the list.
     * PRE: input is open and numbers is not null.
     * POST: Reports whether all valid entered values are present; numbers is unchanged.
     */
    public static void checkAll(Scanner input, MyArrayList<Integer> numbers) {
        input.nextLine();
        System.out.print("Enter values to check separated by spaces: ");
        ArrayList<Integer> checked = readIntegerLine(input.nextLine());
        boolean allPresent = true;
        for (Integer value : checked) {
            if (!numbers.contains(value)) allPresent = false;
        }

        String result;
        if (allPresent) {
            result = "These valid items are in your list: " + checked;
        } else {
            result = "Not all values provided are in your list.";
        }
        System.out.println(result);
        writeReport(result);
    }

    /** Deletes the value at a user-selected index.
     * PRE: input is open and numbers is not null.
     * POST: Removes the selected item, or reports that an empty list cannot be changed.
     */
    public static void deleteByIndex(Scanner input, MyArrayList<Integer> numbers) {
        if (numbers.size() == 0) {
            System.out.println("The list is empty; there is nothing to delete.");
            return;
        }
        int index = readIndex(input, numbers, "delete");
        int removed = numbers.remove(index);
        String result = "Item at index: " + index + " was " + removed + " has been removed.";
        System.out.println(result);
        writeReport(result);
    }

    /** Removes each entered value from the list if present.
     * PRE: input is open and numbers is not null.
     * POST: One matching occurrence per entered value is removed and reported.
     */
    public static void removeList(Scanner input, MyArrayList<Integer> numbers) {
        input.nextLine();
        System.out.print("Enter values to remove separated by spaces: ");
        ArrayList<Integer> requested = readIntegerLine(input.nextLine());
        ArrayList<Integer> removed = new ArrayList<>();
        for (Integer value : requested) {
            if (numbers.remove(value)) removed.add(value);
        }
        String result = "Items removed from your list: " + removed;
        System.out.println(result);
        writeReport(result);
    }

    /** Finds and reports the first position of a value.
     * PRE: input is open and numbers is not null.
     * POST: Reports the first matching index or that the value is absent; list is unchanged.
     */
    public static void findValue(Scanner input, MyArrayList<Integer> numbers) {
        System.out.print("Enter a value to find: ");
        int value = readInteger(input);
        int position = numbers.indexOf(value);
        String result = position >= 0
                ? "The value " + value + " was found at position: " + position
                : "The value " + value + " could not be found";
        System.out.println(result);
        writeReport(result);
    }

    /** Replaces a value at a user-selected index.
     * PRE: input is open and numbers is not null.
     * POST: Updates the selected item, or reports that an empty list cannot be modified.
     */
    public static void modifyValue(Scanner input, MyArrayList<Integer> numbers) {
        if (numbers.size() == 0) {
            System.out.println("The list is empty; there is nothing to modify.");
            return;
        }
        int index = readIndex(input, numbers, "modify");
        System.out.print("Enter new value: ");
        int newValue = readInteger(input);
        int oldValue = numbers.set(index, newValue);
        String result = "Item at index: " + index + " was " + oldValue + " is now " + newValue;
        System.out.println(result);
        writeReport(result);
    }

    /** Reads an integer, discarding invalid tokens until one is entered.
     * PRE: input is open.
     * POST: Returns the next integer from input.
     */
    private static int readInteger(Scanner input) {
        while (!input.hasNextInt()) {
            input.next();
            System.out.println("You entered incorrect data. Please try again.");
        }
        return input.nextInt();
    }

    /** Reads all integer tokens from one line and skips invalid tokens.
     * PRE: line is not null.
     * POST: Returns the valid integers in input order.
     */
    private static ArrayList<Integer> readIntegerLine(String line) {
        ArrayList<Integer> numbers = new ArrayList<>();
        Scanner lineInput = new Scanner(line);
        while (lineInput.hasNext()) {
            if (lineInput.hasNextInt()) {
                numbers.add(lineInput.nextInt());
            } else {
                System.out.println("Skipping invalid value: " + lineInput.next());
            }
        }
        lineInput.close();
        return numbers;
    }

    /** Reads an index, repeating until it is in the list's valid range.
     * PRE: input is open and numbers is not empty.
     * POST: Returns an index from 0 through size - 1.
     */
    private static int readIndex(Scanner input, MyArrayList<Integer> numbers, String action) {
        while (true) {
            System.out.print("Enter index to " + action + " (0-" + (numbers.size() - 1) + "): ");
            if (input.hasNextInt()) {
                int index = input.nextInt();
                if (index >= 0 && index < numbers.size()) return index;
                System.out.println("Index: " + index + " is not in range. Please try again.");
            } else {
                input.next();
                System.out.println("You entered incorrect data. Please try again.");
            }
        }
    }
}
