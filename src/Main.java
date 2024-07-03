import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static Scanner scanner = null;
    static String choice;
//    static HashMap<String, String> participantDetails = new HashMap<>();
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
        consoleReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("\n------------------\nWelcome to the MCMS");
        System.out.println("--------------------\n");
        welcome();


    }

     static void welcome() {
         System.out.println("""
                Re-write the following commands in the terminal to perform the actions
                Replace the square brackets with the actual values
                                
                Here are the available options:

                register [username] [firstname] [lastname] [emailAddress] [password] [date_of_birth] [school_registration_number] [image_file.png]
                login [username] [password]""");

         System.out.println("Enter command: ");
         String command = null;
         try {
             command = consoleReader.readLine();

             client.printWriter.println(command);
             String response = client.reader.readLine();
             System.out.println(response);
             response = client.reader.readLine();

             switch (response) {

                 case "valid register":
                     System.out.println( "\n----------\nCommand sent successfully. Wait for confirmation email then log in");
                     welcome();
                     break;
                 case "valid login":
                     System.out.println(  "\n----------\nValid login details");
                     response = client.reader.readLine();
                     if (response.split(" ")[0].equalsIgnoreCase("representative")) rep(response);
                     else if (response.split(" ")[0].equalsIgnoreCase("participant")) participant(response);
                     else {
                         System.out.println(response + "\n----------\nInvalid command");
                         welcome();
                     }
                 case "invalid":
                     System.out.println(response + "\n----------\nInvalid command");
                     welcome();
                     break;
                    default:
                        System.out.println(response + "\n----------\nInvalid command");
                        welcome();
                        break;
             }
//             if(!response.equalsIgnoreCase("valid command")){
//                 System.out.println("\n----------------------------\n"+response+"\n----------------------------\n");
//                 welcome();
//             }
//             else {
//                 System.out.println("\n----------------------------\n"+response+"\n----------------------------\n");
//
//                 System.out.println("""
//                         Command sent successfully
//                         Wait for confirmation email then log in
//                         """);
//                 if (command.strip().split(" ")[0].equalsIgnoreCase("login") {
//                     System.out.println(client.reader.readLine());
//                     System.out.println(client.reader.readLine());
//                     System.out.println(client.reader.readLine());
//                     System.out.println(client.reader.readLine());
//                     client.printWriter.println(consoleReader.readLine());
//                     welcome();
//
//
//             }
//         } catch (IOException e) {
//             e.getMessage();
//         }}
         }catch (IOException e){
             e.getMessage();
         }
    }

    private static void participant(String response) {


    }

    private static void rep(String response) {

            while ( true){
                try {
                System.out.println("---------\nHello "+response.split(" ")[1]);
                response=client.reader.readLine();
                if (response==null)     {    welcome();break;}
                System.out.println("\n Enter 'yes' to confirm or 'no' to reject participant details\n-----*******-------\n");

                System.out.println(response);
                choice=consoleReader.readLine();
                client.printWriter.println(choice);


            }catch (IOException e) {
                    e.printStackTrace();
                }

        }

    }


}