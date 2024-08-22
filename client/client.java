/* Client side for password manager */
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class client {
    /* Password Requirements */
    public static final int MIN_CHARACTERS = 8;
    public static final int MAX_CHARACTERS = 64;
    public String TRIGGER;
    public String PASSWORD;
    public static int TOTAL_PASSWORDS = 0;
    public static String SPECIALCHARACTER_REGEX = ".*[!@#$%^&*()_+{}\\[\\]:;\"'<>,.?/~`|-].*";

    /* List of passwords for duplication and storage */
    private ArrayList<String> passwordList = new ArrayList<String>();
    private Set<String> dupSET = new HashSet<String>(passwordList);



    /* Used to create the password, only checks for password length then is feed to validatePasswords for further
     * validation
     */
    public String createPassword(String PASSWORD){
        if (PASSWORD.length() > MIN_CHARACTERS && PASSWORD.length() < MAX_CHARACTERS){
            return validatePassWord(PASSWORD);
        } else {
            return "Password length is not strong enough";
        }
    }

    /* Validating Password Using the set requirements. Primary function for password checking */
    private String validatePassWord(String input){
        String status;
        Pattern pattern = Pattern.compile(SPECIALCHARACTER_REGEX);
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches() && dupSET.size() == passwordList.size()){
            passwordList.add(input);
            status = "Validated";
        } else {
            status = "Error: Duplicate or Non-Special Character";
        }
        System.out.println(status);
        return status;
    }

    public String run(){
        boolean run = true;
        Scanner input = new Scanner(System.in);
        while (run){
            System.out.print("Enter The Password: ");
            String line = input.nextLine();
            if ("exit".equalsIgnoreCase(line)) {
                System.out.println("Goodbye!");
                break;
            }
            createPassword(line);
        }
        input.close();
        System.out.println(passwordList);
        return "Success!";
    }



    /* Used to keep track of all passwords used */
    public client(){
        TOTAL_PASSWORDS++;
    }



    public static void main(String[] args) {
        client myClient = new client();
        System.out.println(myClient.run());
}
}