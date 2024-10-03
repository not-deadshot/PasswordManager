/* Client side for password manager */
/*
 * Needed Libraries and imports 
 */
import java.util.ArrayList;
import static java.util.Arrays.asList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;


public class client {
    /* Password Requirements
     * Min & Max Characters used for initialization 
     * Total Passwords used for counter of passwords
     * REGEX used to enforce special character password rule
     * passwordList used to keep track of each password in the list of passwords
     * dupSet used to detect duplicates in a password list
     */
    public static final int MIN_CHARACTERS = 8;
    public static final int MAX_CHARACTERS = 64;
    public static int TOTAL_PASSWORDS = 0;
    public static String SPECIALCHARACTER_REGEX = ".*[!@#$%^&*()_+{}\\[\\]:;\"'<>,.?/~`|-].*";

    /* List of passwords for duplication and storage */
    private ArrayList<String> passwordList = new ArrayList<String>();
    private Set<String> dupSET = new HashSet<String>();
    private ArrayList<String> dummyList = new ArrayList<String>();



    /* Used to create the password, only checks for password length then is feed to validatePasswords for further
     * validation
     */
    public void createPassword(String PASSWORD){
        if (PASSWORD.length() > MIN_CHARACTERS && PASSWORD.length() < MAX_CHARACTERS){
            validatePassWord(PASSWORD);
        } else {
            System.out.println("Password length is not strong enough\n");
        }
    }

    private void updatePassword(int selected){
        /* User inputs value to change in the list ** REMEMBER USER IS NOT ACCOUNTING FOR ZERO INDEXING SO DO SELECTED - 1 FOR CORRECT INDEX** */
        ArrayList<String> passwordList = new ArrayList<>(Arrays.asList("Password_!", "Daking17_!", "Salat123_!"));
        Scanner input = new Scanner(System.in);
        if (selected <= 0 || selected > passwordList.size()){
            System.out.println("Error: Input given either too small or big for list, please try again with a appicable selection. \n");
        } else {
            System.out.println("You've selected password #" + selected);
            System.out.println("What would you like to change it to?\n");
            String trigger = input.nextLine();
            passwordList.set(selected-1, trigger);
            System.out.println("Update Successful");
        }
    }

    /* Validating Password Using the set requirements. Primary function for password checking 
     * pattern is used to compule the special character regex into a variable to later be compared against the password inputted using the 
     * matcher class.
     * Introduced a new list called "dummyList" that gets implemented on instance, same as with the dupSET
     * We now compare the two upon execution of the conditional statement which better handles duplicates as before it wasn't adding the password into the dupSET only the password list
     * which got implemented on first execution (dupSET didn't) and made the conditional statement fail
     * Now, if the matcher returns true then it satisfies the special character requirement
     * the dupSet is mapped to the passwordList earlier in the intialization of the two because of the nature of sets not allowing duplicates by default
     * if the dupSet size is the same as the password list & the password matches the special character regex
     * then no duplicates are detected and the current iteration can be added to the password list
     * The status is filled with the response of conditional loop handling validation and is returned at the end
    */

    private void validatePassWord(String input){
        Pattern pattern = Pattern.compile(SPECIALCHARACTER_REGEX);
        Matcher matcher = pattern.matcher(input);
        dupSET.add(input);
        dummyList.add(input);
        if (matcher.matches() && dupSET.size() == dummyList.size()){
            passwordList.add(input);
            System.out.println("Validated!\n");
        } else  if (!matcher.matches()){
            System.out.println("Error: Non-Special Character\n");
            //System.out.print("Does it contain a special character: " + matcher.matches() + "\n");
        } else {
            System.out.println("Error: Duplicate Passwords are not allowed\n");
            /*
            System.out.println("dupSET Size: " + dupSET.size());
            System.out.println("dummyList Size: " + dummyList.size());
            System.out.println("dupSet Content: " + dupSET);
            System.out.println("dummyList Content: " + dummyList);
            */
        }
    }

    /*
     * Used to simulate a typical runtime from a user interacting with our program
     * the variable run is used to stabilize the while loop until the user enters "exit" to break the loop/simulation
     * right now, the user is prompted to enter a password which is stored in a variable called line
     * ideally the run program should send this password into our createpassword function which will validate the base requirements for a password (min, max) 
     * and then also send the result of createpassword into validatepassword to check for duplication, special characters and any other complex checking needed for the password
     * eventually the user exits the program and we send them a goodbye message
     */

    private void run(){
        boolean run = true;
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to the Password Manager!\n");
        while (run){
            System.out.println("Create = Create Password" +  "\n" +  "Show = PasswordList" + "\n" + "Update = Update Password" + "\n" + "Exit = Exit Program \n");
            System.out.println("What would we like to do?:");
            String trigger = input.nextLine();
            System.out.println();
            if ("exit".equalsIgnoreCase(trigger)) {
                break;
            } else if ("update".equalsIgnoreCase(trigger)){
                System.out.println("Which password would you like to update?: ");
                int line2 = input.nextInt();
                input.nextLine();
                updatePassword(line2);
            } else if ("create".equalsIgnoreCase(trigger)){
                System.out.println("Enter the password you'd like to add: (No Repeated Passwords)");
                String createPassword = input.nextLine();
                createPassword(createPassword);
            } else if ("show".equalsIgnoreCase(trigger)){
                System.out.println("Your Password List: " + passwordList + "\n");
            } else {
                System.out.println("Sorry, please enter a correct command or exit the program");
            }
        }
        input.close();
        System.out.println("Goodbye!");
    }

    /* Hashing algorith for SHA - 256 using MessageDigest and Standard Charsets.UTF_8 */
    /* Uses the messageDigest class to get the instance of SHA-256. Simply just pulling the hash from a library in java and storing it in MessageDigest 
     * We then use the messageDigest class to create a array of bytes. We use the class to take the password given in the input and convert the string into an array of bytes
     * using the StandardCharsets.UTF_8 encoding (recommeneded by NIST)
     * After we create a biginterger called noHash using the hash created in the hashBytes variable. We used biginterger because it allows us to handle large numbers
     * After creating the hash we then create the string using .toString() to get only the 16 bytes needed for the hash
     * returns error in the stack if encountered
      */
    private String hashString(String input){
    try {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte hashBytes[] = messageDigest.digest(input.getBytes(StandardCharsets.UTF_8));
        BigInteger noHash = new BigInteger(1, hashBytes);
        String hashStr = noHash.toString(16);
        return hashStr;
    } catch (NoSuchAlgorithmException e) {
        e.printStackTrace();
    }
    return "Hashed";
    }



    /* Used to keep track of all passwords used */
    public client(){
        TOTAL_PASSWORDS++;
    }



    public static void main(String[] args) {
        client myClient = new client();
        myClient.run();


        
    }
}