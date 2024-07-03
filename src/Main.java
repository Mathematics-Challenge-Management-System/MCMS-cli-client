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
             String response;
             response = client.reader.readLine();
//             System.out.println("\n"+response+"\n");

             switch (response.strip().split(" ")[0]) {

                 case "valid":
//                     System.out.println("in valid");
                     if (response.split(" ")[1].equalsIgnoreCase("register")){
                     System.out.println( "\n----------\nCommand sent successfully. Wait for confirmation email then log in\n----------\n");
//                     welcome();
//                     break;
                     }else {
                         System.out.println(  "\n----------\nValid login details\n----------\n");
                         response = client.reader.readLine();
                         if (response.split(" ")[0].equalsIgnoreCase("representative")) rep(response);
                         else if (response.split(" ")[0].equalsIgnoreCase("participant")) participant(response);
                         else {
                             System.out.println( "\n----------\nUser not found\n----------\n");
//                             welcome();
//                             break;
                         }
                     }

                     break;
                 case "invalid":
                     if (response.split(" ")[1].equalsIgnoreCase("school")) {
                         System.out.println( "\n----------\nSchool registration number does not exist\n----------\n");
                         welcome();
                     } else if (response.split(" ")[1].equalsIgnoreCase("values")) {
                         System.out.println( "\n----------\nIncomplete command. please enter all the required fields\n----------\n");
                         welcome();
                     }
                     else {
                         System.out.println( "\n----------\nFailed to register\n----------\n");
                         welcome();
                     }

                     break;
                    default:
                        System.out.println( "\n----------\nInvalid command\n----------\n");
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


                try {
                    while (!Objects.equals(response = client.reader.readLine(), "done")){
                        System.out.println("something");
                System.out.println("---------\nHello "+response.split(" ")[1]);


                System.out.println("\n Enter 'yes' to confirm or 'no' to reject participant details\n-----*******-------\n");

                System.out.println(response);
                choice=consoleReader.readLine();
                client.printWriter.println(choice);
                    }
    welcome();
            }catch (IOException e) {
                    e.printStackTrace();
                }



    }


}