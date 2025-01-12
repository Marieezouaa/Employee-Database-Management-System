/*
 * The purpose of this class is to be called by the main method
 * This call will serve as the medium between the client and the database
 * The client's input would allow them to navigate through various features of the database
 *
 * It needs an instance of the DatabaseConnection("connection")
 */
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientInteraction {

    public static void commenceProgram(String tableName) throws SQLException {

        DatabaseConnection connection = new DatabaseConnection(); // database connection object
        connection.createConnection(); // creates connection
        Connection conn = connection.getConnection();

        DatabaseTableFunctionality employeeTable = new DatabaseTableFunctionality(tableName); // object can be used to create new table

        Scanner inputScanner = new Scanner(System.in);

        System.out.println("\nWelcome to the company's Employee Database\n");

        boolean loopFlag = true;

        while (loopFlag) {

            System.out.println("What feature would you like to access (enter number)? \n" +
                    "   1. View Employee Database\n" + // performs "SELECT * FROM"
                    "   2. Add New Employee(s)\n" + // Create function would fall under this (C)
                    "   3. Update Employee Info\n" + // read functions would fall under this (R)
                    "   4. Search Employee Table\n" + // update functions would fall under this (U)
                    "   5. Delete Data\n" + // delete data would fall under this (D)
                    "   6. Exit"); // exit program
            System.out.print("> ");
            int userInput = inputScanner.nextInt();

            if (userInput < 1 || userInput > 6) { // checks if user input is not within range of numbers
                System.out.println("Not a valid option");
                System.out.print("Would you like to try again?(Y/N)\n> ");
                String choice = inputScanner.next().toLowerCase();

                if (choice.equals("y") || choice.equals("yes")) {
                    System.out.print("Please enter a menu option > ");
                    userInput = inputScanner.nextInt();
                    menuOptions(userInput, tableName.toUpperCase(), inputScanner, employeeTable, conn);
                } else if (choice.equals("n")) {
                    System.out.println("Database Access Terminated");
                    System.exit(0);
                } else {
                    System.out.println("Invalid Option. Database Access Terminated.");
                    System.exit(0);
                }
            } else {
                menuOptions(userInput, tableName.toUpperCase(), inputScanner, employeeTable, conn);
                // Ask the user if they want to continue
                System.out.print("Would you like to continue? (Y/N)\n> ");
                String continueChoice = inputScanner.next().toLowerCase();
                if (continueChoice.equals("n") || continueChoice.equals("no")) {
                    loopFlag = false;
                    System.out.println("Exiting...");
                }
            }
        }
    }

    // This function accepts the user's input and navigate through the menu based on that option
    static void menuOptions(int userInput, String tableName, Scanner inputScanner, DatabaseTableFunctionality employeeTable, Connection conn) throws SQLException {
        switch (userInput) {
            case 1:
                employeeTable.displayTable(tableName.toUpperCase());
                System.out.println("Here are all the contents of the table");
                break;
            case 2:
                addEmployees(inputScanner, conn, tableName);
                break;
            case 3:
                EmployeeFucntionality.updateEmployee(conn, inputScanner, tableName);
                break;
            case 4:
                EmployeeFucntionality.searchEmployeeTable(conn, inputScanner, tableName);
                break;
            case 5:
                EmployeeFucntionality.deleteEmployee(conn, inputScanner, tableName);
                break;
            case 6:
                System.out.println("Exiting...");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid Option.");
                break;
        }
        System.out.print("Would you like to perform another operation? (Y/N) ");
        String choice = inputScanner.next().toLowerCase();
        if (!choice.equals("y") && !choice.equals("yes")) {
            System.out.println("Database Access Terminated.");
            System.exit(0);
        }
    }

    // Method to add multiple employees
    static void addEmployees(Scanner inputScanner, Connection conn, String tableName) throws SQLException {
        ArrayList<Employee> employees = new ArrayList<>();
        System.out.print("How many employees would you like to add? ");
        int numberOfEmployees = inputScanner.nextInt();

        for (int i = 0; i < numberOfEmployees; i++) {
            Employee employee = new Employee();


            employee.setEmpId(EmployeeFucntionality.generateUniqueEmployeeId(conn, tableName));

            inputScanner.nextLine(); // Consume the newline

            System.out.print("Enter first name: ");
            employee.setName(inputScanner.nextLine());

            System.out.print("Enter division: ");
            employee.setDivision(inputScanner.nextLine());

            System.out.print("Enter job title: ");
            employee.setJobTitle(inputScanner.nextLine());

            System.out.print("Enter salary: ");
            employee.setSalary(inputScanner.nextDouble());

            inputScanner.nextLine();

            System.out.print("Enter SSN: ");
            employee.setSSN(inputScanner.nextLine());

            employees.add(employee);
        }

        // Confirms user wants to save changes before pushing to database
        System.out.print("Would you like to save these changes? (Y/N) ");
        String choice = inputScanner.next().toLowerCase();
        if (choice.equals("y") || choice.equals("yes")) {
            for (Employee employee : employees) {
                EmployeeFucntionality.addEmployee(conn, tableName, employee);
            }
            System.out.println("Employees added to the database.");
        } else {
            System.out.println("Employees not saved.");
        }
    }
}
