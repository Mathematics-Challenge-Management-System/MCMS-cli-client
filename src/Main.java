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
// a method to check that the email is valid
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
    //a method to ensure thaat the date format is correct for mysql yyyy-mm-dd
    public static boolean isValidDate(String date) {
        String dateRegex = "^(19|20)\\d{2}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$";
        return date.matches(dateRegex);
    }

    //a method to ensure that the command is either login or register
    public static boolean isValidCommand(String command) {
        return command.equalsIgnoreCase("login") || command.equalsIgnoreCase("register");
    }

     static void welcome() {
         System.out.println("""
                 Re-write the following commands in the terminal to perform the actions
                 Replace the square brackets with the actual values
                                 
                 Here are the available options:
                 The date should be in the format yyyy-mm-dd
                 

                 register [username] [firstname] [lastname] [emailAddress] [password] [date_of_birth] [school_registration_number] [image_file.png]
                 login [username] [password]""");

         System.out.println("Enter command: ");
         String command = null;
         try {
             command = consoleReader.readLine();

             if (!isValidCommand(command.strip().split(" ")[0])) {
                 System.out.println("\n----------\nInvalid command\n----------\n");
                 welcome();
             }

             switch (command.strip().split(" ")[0].toLowerCase()){

                 case "register":
                     if (command.strip().split(" ").length < 9) {
                         System.out.println("\n----------\nIncomplete command. please enter all the required fields\n----------\n");
                         welcome();
                     } else if ( command.strip().split(" ").length > 9) {
                         System.out.println("\n----------\nToo many fields\n----------\n");
                         welcome();
                     }else if ( !command.strip().split(" ")[8].contains(".png")) {
                         System.out.println("\n----------\nInvalid image file\n----------\n");
                         welcome();
                     }else if (!isValidEmail(command.strip().split(" ")[4])) {
                         System.out.println("\n----------\nInvalid email\n----------\n");
                         welcome();
                     }
                     else if (!isValidDate(command.strip().split(" ")[6])) {
                         System.out.println("\n----------\nInvalid date\n----------\n");
                         welcome();
                     }
                     break;
                 case "login":
                     if ( command.strip().split(" ").length < 3) {
                         System.out.println("\n----------\nIncomplete command. please enter all the required fields\n----------\n");
                         welcome();
                     }else if ( command.strip().split(" ").length > 3) {
                         System.out.println("\n----------\nToo many fields\n----------\n");
                         welcome();
                     }
                     break;
             }



                 client.printWriter.println(command);
                 String response;
                 response = client.reader.readLine();
//             System.out.println("\n"+response+"\n");

                 switch (response.strip().split(" ")[0]) {

                     case "valid":
//                     System.out.println("in valid");
                         if (response.split(" ")[1].equalsIgnoreCase("register")) {
                             System.out.println("\n----------\nCommand sent successfully. Wait for confirmation email then log in\n----------\n");
                             welcome();
//                     break;
                         } else {
                             System.out.println("\n----------\nValid login details\n----------\n");
                             response = client.reader.readLine();
                             System.out.println(response);
                             if (response.split(" ")[0].equalsIgnoreCase("representative")) rep(response);
                             else if (response.split(" ")[0].equalsIgnoreCase("participant")) participant(response);
                             else {
                                 System.out.println("\n----------\nUser not found\n----------\n");
                                 welcome();
//                             break;
                             }
                         }

                         break;
                     case "invalid":
                         if (response.split(" ")[1].equalsIgnoreCase("school")) {
                             System.out.println("\n----------\nSchool registration number does not exist\n----------\n");
                             welcome();
                         } else if (response.split(" ")[1].equalsIgnoreCase("values")) {
                             System.out.println("\n----------\nIncomplete command. please enter all the required fields\n----------\n");
                             welcome();
                         } else if (response.split(" ")[1].equalsIgnoreCase("username")) {
                             System.out.println("\n----------\nUsername already exists\n----------\n");
                             welcome();
                         } else if (response.split(" ")[1].equalsIgnoreCase("participant")) {
                             System.out.println("\n----------\nParticipant already registered!! Please login!\n----------\n");
                             welcome();
                         } else if (response.split(" ")[1].equalsIgnoreCase("rejected")) {
                             System.out.println("\n----------\nYou were rejected by this school. \nPlease register with a correct school\n----------\n");
                             welcome();
                         } else {
                             System.out.println("\n----------\nFailed to register\n----------\n");
                             welcome();
                         }

                         break;
                     default:
                         System.out.println("\n----------\nInvalid command\n----------\n");
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
             }catch(IOException e){
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
                    System.out.println("\n***********\nThere are no more participants to validate\n");
    welcome();
            }catch (IOException e) {
                    e.printStackTrace();
                }



    }


}