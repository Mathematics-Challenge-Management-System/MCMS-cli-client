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
                 The image file should be in png format
                 login is done after registration and the username and password should be the same as the ones used in registration
                
                 
                 register [username] [firstname] [lastname] [emailAddress] [password] [date_of_birth] [school_registration_number] [image_file.png]
                 login [username] [password]""");

         System.out.println("Enter command: ");
         String[] command = null;
         try {
             String commandString=consoleReader.readLine();
             command = commandString.strip().split(" ");
             if (!isValidCommand(command[0])) {
                 System.out.println("\n----------\nInvalid command \n Expected login or register\n----------\n");
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
                 client.printWriter.println(commandString);
                 String response;
                 response = client.reader.readLine();
                String[] responseArray = response.strip().split(" ");
                 switch (response.strip().split(" ")[0]) {
                     case "valid":
                         if (responseArray[1].equalsIgnoreCase("register")) {
                             System.out.println("\n----------\nRegister command sent successfully. Wait for confirmation email then log in\n----------\n");
                             welcome();
                         } else if (responseArray[1].equalsIgnoreCase("login")){
                             response = client.reader.readLine();
                             if (response.strip().split(" ")[0].equalsIgnoreCase("representative")){
                                System.out.println("Hello "+response.split(" ")[1].toUpperCase()+"\n*************Confirm participant****************");

                                 rep(response);
                             }
                             else if (response.strip().split(" ")[0].equalsIgnoreCase("participant")) participant(response);
                             else {
                                 System.out.println("\n----------\nUser not found\n----------\n");
                                 welcome();
                             }
                         }
                         break;
                         //incase of invalid input
                     case "invalid":
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
        System.out.println("\nHello "+response.split(" ")[1].toUpperCase()+"\n*****************************");
        try {
            String command=enterCommand().strip();
            client.printWriter.println(command);
            String response1;
            switch (command) {
                case "view challenges":
                     response1 = client.reader.readLine();
                    if (response1.equalsIgnoreCase("no challenges")) {
                        System.out.println("\n***********\nThere are no challenges available\n***********\n");
                        participant(response);
                        break;
                    } else {
                        System.out.println("\n*********** These are the available challenges ***********\n");
                        while (!Objects.equals(response1, "done")) {
                            //if response1 starts with 'Challenge name' first print a new line
                            if(response1.startsWith("Challenge name")){
                                System.out.println("---------------------\n");
                            }
                            System.out.println(response1);
                            response1 = client.reader.readLine();
                        }
                        participant(response);

                    }
                    break;
                case "attempt challenge":
                    response1 = client.reader.readLine();
                    if (response1.equalsIgnoreCase("no challenges")) {
                        System.out.println("\n***********\nThere are no challenges available\n***********\n");
                        participant(response);
                        break;
                    } else {
                        System.out.println("\n*********** These are the available challenges ***********\n");
                        while (!Objects.equals(response1, "done")) {
                            //if response1 starts with 'Challenge name' first print a new line
                            if(response1.startsWith("Challenge name")){
                                System.out.println("---------------------\n");
                            }
                            System.out.println(response1);
                            response1 = client.reader.readLine();
                        }
                        System.out.println("\nEnter a challenge name to attempt\n***********\n");
                        String challengeName = scanner.nextLine();

                        client.printWriter.println(challengeName);
                        while(!Objects.equals(response1 = client.reader.readLine(), "done")){
                            if (response1.equalsIgnoreCase("invalid challenge")) {
                                System.out.println("\nInvalid challenge name\n***********\n");
                                participant(response);
                            } else if(response1.equalsIgnoreCase("3")){
                                System.out.println("""
                                                              ***********
                                        You have reached the maximum number of attempts for this challenge(3)
                                                        Please try another challenge
                                                              ***********
                                        """);
                                participant(response);
                            }else{
                                    System.out.println(response1);
                            }
                        }
                        choice = startOrBack();
                        if (choice.strip().equalsIgnoreCase("start")) {

                            client.printWriter.println("start");
                            System.out.println("\n************");
                            while (!Objects.equals(response1 = client.reader.readLine(), "done")){
                                if(Objects.equals(response1, "Time is up!!"))
                                {

                                    System.out.println("************\n"+response1);
                                    continue;
                                }
                                System.out.println("\n--------------------------\n"+response1+"\nQuestion:");
                                response1=client.reader.readLine();
                                System.out.println(response1);
                                System.out.println("\nSolution:");
                                choice=scanner.nextLine();
                                client.printWriter.println(choice);


                            }
                            System.out.println("************\n"+client.reader.readLine());
                            System.out.println("Thank you for participating\n************");
                            participant(response);

                        } else if (choice.strip().equalsIgnoreCase("back")) {
                            System.out.println("back");
                            client.printWriter.println("back");
                            participant(response);
                        } else {
                            System.out.println("\nInvalid command\n***********");
                            participant(response);
                        }
//                        System.out.println(response1);
                        participant(response);
                    }
                    break;
                case "logout":
                    System.out.println("\n***********\nGoodbye\n***********\n");
                    client.printWriter.println("exit");
                    welcome();
                    break;
                default:
                    System.out.println("\n----------\nInvalid command\n Expected view challenges or attempt challenge or exit\n----------\n");
                    participant(response);
            }


        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String startOrBack() {
        String choice;
        do {
            System.out.println("\n-----------------------\nEnter 'start' to start the challenge or 'back' to go back");
            choice=scanner.nextLine();
            if(!(choice.strip().equalsIgnoreCase("start") || choice.strip().equalsIgnoreCase("back"))){
                System.out.println("\nInvalid command\n***********");

            }
        }while (!(choice.strip().equalsIgnoreCase("start") || choice.strip().equalsIgnoreCase("back")));


        return choice;
    }
    //a method to take

    private static String chooseChallenge() {
        System.out.println("\n***********\nEnter a challenge name to attempt\n***********\n");
        return scanner.nextLine();

    }

    private static String enterCommand() throws IOException {
        System.out.println("""
                Enter one of the following commands:
                view challenges
                attempt challenge 
                logout
                """);
        System.out.println("Enter command: ");
        String command =scanner.nextLine();
        if (!command.equalsIgnoreCase("view challenges") && !command.equalsIgnoreCase("attempt challenge") && !command.equalsIgnoreCase("logout")) {

            System.out.println("""
                                     Invalid command
                    -----------------------------------------------------------
                     Expected 'view challenges' , 'attempt challenge' or 'exit'
                                *****************************
                    """);
            command=enterCommand();

        }

        return command;
    }

    private static void rep(String response) {
        System.out.println("""
                                      -------------
                Enter 'yes' to Confirm participants and 'no' to go back.
                                       -------------
                """
        );
        choice=scanner.nextLine();
        if (choice.strip().equalsIgnoreCase("yes")) {
            client.printWriter.println("yes");
            try {
                while (!Objects.equals(response = client.reader.readLine(), "done")) {
                    String[] participantDetails = response.strip().split(" ");
                    System.out.println("Name : "+participantDetails[1].toUpperCase()+" "+participantDetails[2].toUpperCase()+"\nEmail : "+participantDetails[3]+"\nDate of Birth : "+participantDetails[5]+"\nSchool Registration Number : "+participantDetails[6]+"\n");
                    client.printWriter.println( getValidationResponse());
                }
                System.out.println("\n***********\nThere are no more participants to validate\n***********\n");
                welcome();
            }catch (IOException e) {
                e.printStackTrace();
            }
        } else if (choice.strip().equalsIgnoreCase("no")) {
            client.printWriter.println("no");
            welcome();
        } else {
            System.out.println("\n----------\nInvalid response\n----------\n");
            rep(response);

        }
    }
    // get response from rep ensuring that it is either yes or no
    private static String getValidationResponse() {
        try {

            System.out.println("\n-----*******-------\n Enter 'yes' to confirm or 'no' to reject participant details\n-----*******-------\n");

            choice = consoleReader.readLine().strip();
            if (!(choice.equalsIgnoreCase("yes") || choice.equalsIgnoreCase("no"))){
                System.out.println("\n-----*******-------\nInvalid response\n-----*******-------\n");
                getValidationResponse();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return choice;
    }

}