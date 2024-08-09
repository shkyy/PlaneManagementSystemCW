import java.io.FileWriter;
import java.io.IOException;

// Represents the ticket purchased for a seat in the plane
public class Ticket {

    // Initialize the attributes
    private int row;
    private int seat;
    private double price;
    private Person person;

    // Constructor to initialize the ticket info
    public Ticket (int row, int seat, double price, Person person) {
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.person = person;
    }

    // The getters and the setters
    public  int getRow() { return row; }
    public void setRow(int row) { this.row = row; }
    public  int getSeat() { return seat; }
    public void setSeat(int seat) { this.seat = seat; }
    public  double getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }
    public Person getPerson() { return person; }
    public void setPerson(Person person) { this.person = person; }

    // Print the ticket info
    public void TicketInfo() {
        System.out.println("Row: " + ((char) (row + 'A')));
        System.out.println("Seat: " + (seat + 1));
        System.out.println("Price: £" + price);
        person.personInfo();
    }

    // Save the ticket info to the text file
    public void saveTicketInfoToFile(char row, int seat, double price) {
        String fileName = "Ticket_No_" + row + (seat + 1) + ".txt";

        try (FileWriter writeFile = new FileWriter(fileName)) {
            writeFile.write("Ticket information:- \n");
            writeFile.write("Row: " + row + "\n");
            writeFile.write("Seat: " + (seat + 1) + "\n");
            writeFile.write("Price: " + price + "\n");
            writeFile.write("Name: " + person.getName() + "\n");
            writeFile.write("Surname: " + person.getSurname() + "\n");
            writeFile.write("Email: " + person.getEmail() + "\n");
        }
        catch (IOException e) {
            System.out.println("Failed to save ticket info to the file");
            e.printStackTrace();
        }
    }
}
