// Represents the user details
public class Person {

    // Initialize the attributes
    private String name, surname, email;

    // Constructor to initialize the person info
    public Person (String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    // The getters and the setters
    public String getName() { return name; }
    public void setName (String name) { this.name = name; }
    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    // Print the user info
    public void personInfo() {
        System.out.println("\nPersonal Information:- ");
        System.out.println("Name: " + name);
        System.out.println("Surname: " + surname);
        System.out.println("Email: " + email);
    }
}