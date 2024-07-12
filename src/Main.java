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


    public static void main(String[] args) {
        scanner = new Scanner(System.in);

// to establish a connection
        client.establish("127.0.0.1", 3333);
        consoleReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("\n------------------\nWelcome to the MCMS\n--------------------\n");
        welcome();


    }
// a method to check that the email is valid
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
    //a method to ensure that the date format is correct for mysql yyyy-mm-dd
    public static boolean isValidDate(String date) {
        String dateRegex = "^(19|20)\\d{2}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$";
        return date.matches(dateRegex);
    }

    //a method to ensure that the command is either login or register
    public static boolean isValidCommand(String command) {
        return command.equalsIgnoreCase("login") || command.equalsIgnoreCase("register");
    }
    //method to display instructions to the user
     static void welcome() {
         System.out.println("""
                 Re-write the following commands in the terminal to perform the actions
                 Replace the square brackets with the actual values in that particular order
                                 
                 Here are the available options(instructions):
                 Start with the register command then replace the square brackets with the actual values
                 The user name should br unique
                 The date should be in the format yyyy-mm-dd
                 The image file sshould be in png format
                 login is done after registration and the username and passwor should be the same as the ones used in registration
                
                 

                 register [username] [firstname] [lastname] [emailAddress] [password] [date_of_birth] [school_registration_number] [image_file.png]
                 login [username] [password]""");

         System.out.println("Enter command: ");
         String[] command = null;
         try {
            command = consoleReader.readLine().split(" ");

            if (!isValidCommand(command[0].strip())) {
                 System.out.println(" Invalid command .Please re-read the instructions and try again ");
                 welcome();
             }

             switch (command[0].toLowerCase()){

                 case "register":
                     if (command.length < 9) {
                         System.out.println("\n----------\nIncomplete command. please enter all the required fields and try again\n----------\n");
                         welcome();
                     } else if ( command.length > 9) {
                         System.out.println("\n Too many fields.please re- read the instructions and try again");
                    
                         welcome();
                     }else if ( !command[8].contains(".png")) {
                        System.out.println("\n----------\nInvalid image file path\nPlease ensure that the image file format is png\n----------\n");
                         welcome();
                     }else if (!isValidEmail(command[4])) {
                         System.out.println("\n----------\nInvalid email\n.please enter a valid email address\n----------\n");
                         welcome();
                     }
                     else if (!isValidDate(command[6])) {
                         System.out.println("\n----------\nInvalid date\nPlease enter the date in the format provided in the instructions----------\n");
                         welcome();
                     }
                     break;
                 case "login":
                     if ( command.length < 3) {
                         System.out.println("\n----------\nIncomplete command. please enter all the required fields\n----------\n");
                         welcome();
                     }else if ( command.length > 3) {
                         System.out.println("\n----------\nToo many fields .Please re-read the instructions and try again\n----------\n");
                         welcome();
                     }
                     break;
             }


//server communication
                client.printWriter.println(command);
                 String response;
                 response = client.reader.readLine();
                String[] responseArray = response.strip().split(" ");
                 switch (response.strip().split(" ")[0]) {

                     case "valid":
                         if (responseArray[1].equalsIgnoreCase("register")) {
                             System.out.println("\n----------\nRegister command sent successfully. Wait for confirmation email then log in\n----------\n");
                             welcome();
//                     break;
                         } else {
                             response = client.reader.readLine();
                             if (response.strip().split(" ")[0].equalsIgnoreCase("representative")){
                                System.out.println("Hello "+response.split(" ")[1].toUpperCase()+"\n*************Confirm participant****************");

                                 rep(response);
                             }
                             else if (response.strip().split(" ")[0].equalsIgnoreCase("participant")) participant(response);
                             else {
                                 System.out.println("\n----------\nUser not found\n----------\n");
                                 welcome();
//                             break;
                             }
                         }

                         break;
                         //incase of invalid input
                     case "invalid":
                     if ( responseArray.length> 1){

                     
                         if (responseArray[1].equalsIgnoreCase("school")) {
                             System.out.println("\n----------\nSchool registration number does not exist.\n Please enter the right school registration number\n----------\n");
                             welcome();
                         } else if (responseArray[1].equalsIgnoreCase("values")) {
                             System.out.println("\n----------\nIncomplete command. Please enter all the required fields!!\n----------\n");
                             welcome();
                         } else if (responseArray[1].equalsIgnoreCase("username")) {
                             System.out.println("\n----------\nUsername already exists.Please enter a unique username!!\n----------\n");
                             welcome();
                         } else if (responseArray[1].equalsIgnoreCase("participant")) {
                             System.out.println("\n----------\nParticipant already registered!! Please login to view the challenges!!\n----------\n");
                             welcome();
                         } else if (responseArray[1].equalsIgnoreCase("rejected")) {
                             System.out.println("\n----------\nYou were rejected by this school. \nPlease register with a correct school registration number\n----------\n");
                             welcome();
                         } else {
                             System.out.println("\n----------\nFailed to register.Please follow the instructions and try again\n----------\n");
                             welcome();
                         }
                        }

                         break;
                     default:
                         System.out.println("\n----------\nInvalid command\n Expected login or register!!Try again\n----------\n");
                         welcome();
                         break;
                 }
             }catch(IOException e){
                 e.getMessage();
             }
         }


    private static void participant(String response) {


    }

    private static void rep(String response) {


                try {
                    while (!Objects.equals(response = client.reader.readLine(), "done")) {
                        System.out.println("--------\nConfirm participant.\n---------");
                        String[] participantDetails = response.strip().split(" ");
                     System.out.println("Name : "+participantDetails[1].toUpperCase()+" "+participantDetails[2].toUpperCase()+"\nEmail : "+participantDetails[3]+"\nDate of Birth : "+participantDetails[5]+"\nSchool Registration Number : "+participantDetails[6]+"\n");
                     client.printWriter.println( getResponse());
                    }
                    System.out.println("\n***********\nThere are no more participants to validate\n***********\n");
                 welcome();
                }catch (IOException e) {
                    e.printStackTrace();
                }
    }
    // get response from rep ensuring that it is either yes or no
    private static String getResponse() {
        try {

            System.out.println("\n-----*******-------\n Enter 'yes' to confirm or 'no' to reject participant details\n-----*******-------\n");

            choice = consoleReader.readLine().strip();
            if (!(choice.equalsIgnoreCase("yes") || choice.equalsIgnoreCase("no"))){
                System.out.println("\n-----*******-------\nInvalid response!!Expected yes or no \n-----*******-------\n");
                getResponse();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return choice;
    }

}