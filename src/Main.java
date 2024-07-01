import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static Scanner scanner = null;
    static String choice;
    static HashMap<String, String> participantDetails = new HashMap<>();
    static BufferedReader consoleReader = null;
    static Client client = new Client();
//    Participant participant=new Participant();


    public static void main(String[] args) {
        scanner = new Scanner(System.in);
//    System.out.println("Enter target address");
//    String address=scanner.nextLine();
//
//    System.out.println("Enter target port");
//    int port=scanner.nextInt();
//


        client.establish("127.0.0.1", 3333);
//        Main.welcome();
        consoleReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("\n------------------\nWelcome to the MCMS");
        System.out.println("--------------------\n");

        System.out.println("""
                Re-write the following commands in the terminal to perform the actions
                Replace the square brackets with the actual values
                                
                Here are the available options:

                register [username] [firstname] [lastname] [emailAddress] [date_of_birth] [school_registration_number] [image_file.png]
                login [username] [password]""");

        System.out.println("Enter command: ");
        String command = null;
        try {
            command = consoleReader.readLine();

            client.printWriter.println(command);
            client.reader.readLine();
        } catch (IOException e) {
            e.getMessage();
        }
    }


}