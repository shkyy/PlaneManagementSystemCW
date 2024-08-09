// Import java util package
import java.util.*;

// Main class
public class w2052738_PlaneManagement {

    // Constants to initialize the seat array
    public static final int totalRows = 4;
    public static final int seatsPerSecondThirdRows = 12;
    public static final int seatsPerFirstFourthRows = 14;

    // Total seats array
    public static final int[][] seatsArray = new int[totalRows][];

    // Common scanner to get the user input
    public static final Scanner userInput = new Scanner(System.in);

    // Initialize ticket info array
    public static int totalSeats = totalRows * (seatsPerSecondThirdRows + seatsPerFirstFourthRows);
    public static Ticket[] ticketsInfoArray = new Ticket[totalSeats];

    // Check if a seat is available
    private static boolean isAvailable(int row, int seat) {
        return row >= 0 && seat >= 0;
    }

    // Common method to get user input for the row and validate the input
    private static char getValidRowInput() {
        char row;

        while (true) {
            System.out.print("Enter the letter of your preferred row: ");
            row = userInput.next().toUpperCase().charAt(0);

            if (row >= 'A' && row <= 'D') {
                break;
            } else {
                System.out.println("Invalid input! Please enter a letter between A and D"); // If user enters an invalid input
            }
        }
        return row;
    }

    // Common method to get user input for the seat and validate the input
    private static int getValidSeatInput(int row) {
        int seat = 0;

        while (true) {
            try {
                System.out.print("Enter the number of your preferred seat: ");
                seat = userInput.nextInt();

                if (seat >= 1 && seat <= seatsArray[row].length) {
                    break;
                } else {
                    System.out.println("Invalid input! Please enter a number between 1 and " + seatsArray[row].length); // If user enters a number which is not in the array
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number between 1 and " + seatsArray[row].length); // If user enters an input which is not a number
                userInput.next();
            }
        }
        return seat;
    }

    // Common method to get user input for String inputs and validate the input
    private static String getValidStringInput(String printMessage) {
        String userDetailInput;

        while (true) {
            System.out.print(printMessage);
            userDetailInput = userInput.next();

            if (userDetailInput != null && !userDetailInput.isEmpty()) {
                break;
            }
            else {
                System.out.println("Input cannot be empty!");
            }
        }
        return userDetailInput;
    }

    // Calculate price according to the seat number
    private static double ticketPrice(int seat) {
        if (seat < 6) {
            return 200.0;
        }
        else if (seat > 9) {
            return 180.0;
        }
        else {
            return 150.0;
        }
    }

    // Buy seat functionality
    private static void buySeat() {

        // Calling common methods to get user inputs
        char row = getValidRowInput();
        int rowNumber = row - 'A';
        int seatNumber = getValidSeatInput(rowNumber) - 1;

        // Checks if the user entered row and seat is available
        if (isAvailable(rowNumber, seatNumber) && seatsArray[rowNumber][seatNumber] == 0){ // If available

            seatsArray[rowNumber][seatNumber] = 1; // Set the value of the particular seat in the array to 1

            double price = ticketPrice(seatNumber); // Get price by calling the price methods

            // Calling common String input method to get user inputs
            String name = getValidStringInput("Enter your name: ");
            String surName = getValidStringInput("Enter your surname: ");
            String email = getValidStringInput("Enter you email: ");

            Person userInfo = new Person(name, surName, email); // Create a new person object

            Ticket ticketInfo = new Ticket(rowNumber, seatNumber, price, userInfo); // Create a new ticket object
            ticketsInfoArray[(rowNumber * seatsArray[rowNumber].length) + seatNumber] = ticketInfo; // Set the ticket info to the ticket info array

            ticketInfo.saveTicketInfoToFile(row, seatNumber, price); // Save the ticket info into the text file

            System.out.println("You've secured the seat successfully.");
        }
        else {
            System.out.println("Seat already booked"); // If not available
        }
    }

    // Cancel seat functionality
    private static void cancelSeat() {

        // Calling common methods to get user inputs
        int rowNumber = getValidRowInput() - 'A';
        int seatNumber = getValidSeatInput(rowNumber) - 1;

        // Checks if the user entered row and seat is booked or not
        if (isAvailable(rowNumber, seatNumber) && seatsArray[rowNumber][seatNumber] == 1){ // If booked already

            seatsArray[rowNumber][seatNumber] = 0; // Set the value of the particular seat in the array to 0

            ticketsInfoArray[(rowNumber * seatsArray[rowNumber].length) + seatNumber] = null; // Set null for ticket info that was set before
            System.out.println("The seat has been cancelled successfully.");
        }
        else {
            System.out.println("The seat haven't booked yet."); // If not booked yet
        }
    }

    // Print the Seats
    private static void displaySeatPlan() {
        for (int i = 0; i < totalRows; ++i) {
            for (int j = 0; j < seatsArray[i].length; ++j) {
                if (seatsArray[i][j] == 1) {
                    System.out.print(" X ");
                }
                else {
                    System.out.print(" O ");
                }
            }
            System.out.println();
        }
    }

    // Find the first available seat
    private static void findFirstAvailableSeat() {

        // Loop through the seats array and get first the seat which is set to 0
        for (int i = 0; i < totalRows; i++) {
            for (int j = 0; j < seatsArray[i].length; j++) {
                if (seatsArray[i][j] == 0) {
                    char row = (char) ('A' + i);
                    System.out.println("First available seat is at " + row + (j + 1));
                    return;
                }
            }
        }
    }

    // Print ticket information for all purchased seats and display the total amount of sales
    private static void displayTicketInfo() {
        double totalSold = 0;

        System.out.println("--------------------------------------------------------------------------");
        System.out.println("|          Information of the tickets bought during the session          |");
        System.out.println("--------------------------------------------------------------------------");

        // Iterate through the ticket info array and get the tickets which are not null
        for (int i = 0; i < totalRows; ++i) {
            for (int j = 0; j < seatsArray[i].length; ++j) {
                if (seatsArray[i][j] == 1 && ticketsInfoArray[(i * seatsArray[i].length) + j] != null) {

                    Ticket ticket = ticketsInfoArray[(i * seatsArray[i].length) + j];

                    ticket.TicketInfo(); // Print the ticket info
                    System.out.println("---------------------------------------------");

                    totalSold += ticket.getPrice(); // Add price to the total of sold
                }
            }
        }
        // Print the total sold of the tickets
        System.out.println("Total Sales: £" + totalSold);
        System.out.println("---------------------------------------------");
    }

    // Search a ticket
    private static void searchTicket() {

        // Calling common methods to get user inputs
        int rowNumber = getValidRowInput() - 'A';
        int seatNumber = getValidSeatInput(rowNumber) - 1;

        // Checks if the user entered row and seat is available
        if (isAvailable(rowNumber,seatNumber)  && seatsArray[rowNumber][seatNumber] == 0) { // If available

            System.out.print("Seat available..\n");
            while (true) {
                System.out.print("Do you want to book this seat? (enter y or n): "); // Ask user whether they want to book the seat or not
                String input = userInput.next().toLowerCase();

                if (input.equals("y")) {
                    buySeat();
                    break;
                }
                else if (input.equals("n")) {
                    break;
                }
                else {
                    System.out.println("Invalid input! Enter 'y' or 'n'.");
                }
            }
        }
        else { // If already booked
            Ticket ticket = ticketsInfoArray[(rowNumber * seatsArray[rowNumber].length) + seatNumber];

            // Print the ticket info the booked seat
            if (ticket != null) {
                System.out.println("Seat already booked..\n");
                System.out.println("Ticket " + (char) ('A' + ticket.getRow()) + (ticket.getSeat() + 1));
                System.out.println("Name: " + ticket.getPerson().getName());
                System.out.println("Surname: " + ticket.getPerson().getSurname());
                System.out.println("Email: " + ticket.getPerson().getEmail());
                System.out.println("Price: £" + ticket.getPrice());
            }
        }
    }

    public static void main(String[] args) {
        // Initialize the seats in the array
        seatsArray[0] = new int[seatsPerFirstFourthRows];
        seatsArray[1] = new int[seatsPerSecondThirdRows];
        seatsArray[2] = new int[seatsPerSecondThirdRows];
        seatsArray[3] = new int[seatsPerFirstFourthRows];

        // Print the menu
        System.out.println("\n     -WELCOME TO THE PLANE MANAGEMENT SYSTEM-     \n");
        System.out.println("**************************************************");
        System.out.println("*                      MENU                      *");
        System.out.println("**************************************************");
        System.out.println("    1) Buy a seat");
        System.out.println("    2) Cancel a seat");
        System.out.println("    3) Find first available seat");
        System.out.println("    4) Show seating plan");
        System.out.println("    5) Print ticket information and total sales");
        System.out.println("    6) Search ticket");
        System.out.println("    0) Quit");
        System.out.println("**************************************************");

        // Main loop
        while (true) {
            try {
                System.out.print("\nPlease enter your choice: ");
                int userChoice = userInput.nextInt();
                System.out.print("\n");
                if (userChoice == 0) {
                    System.out.println("Exiting...");
                    break;
                }
                if (userChoice >= 1 && userChoice <= 6) {
                    switch (userChoice) {

                        case 1:
                            buySeat();
                            break;

                        case 2:
                            cancelSeat();
                            break;

                        case 3:
                            findFirstAvailableSeat();
                            break;

                        case 4:
                            displaySeatPlan();
                            break;

                        case 5:
                            displayTicketInfo();
                            break;

                        case 6:
                            searchTicket();
                            break;
                    }
                } else {
                    System.out.println("Invalid input! Enter a number between 0 and 6");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Enter a number.");
                userInput.next();
            }
        }
    }


}