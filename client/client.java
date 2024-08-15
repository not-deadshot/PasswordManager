/* Client side for password manager */
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class client {

    public String PASSWORD;
    public static final int MIN_CHARACTERS = 8;
    public static final int MAX_CHARACTERS = 64;
    public static int TOTAL_PASSWORDS = 0;
    public static String SPECIALCHARACTER_REGEX = ".*[!@#$%^&*()_+{}\\[\\]:;\"'<>,.?/~`|-].*";


    /* Used to create the password, only checks for password length */
    public String createPassword(){
        Scanner input = new Scanner(System.in);
        System.out.print("Enter The Password: ");
        PASSWORD = input.nextLine();
        input.close();
        if (PASSWORD.length() > MIN_CHARACTERS && PASSWORD.length() < MAX_CHARACTERS){
            return validatePassWord(PASSWORD);
        } else {
            return "Password length is not strong enough";
        }
    }

    /* Validating Password Using RegEx constraints */
    public String validatePassWord(String input){
        Pattern pattern = Pattern.compile(SPECIALCHARACTER_REGEX);
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()){
            return "Validated";
        } else {
            return "At least one special character is needed";
        }
    }

    /* Used to keep track of all passwords used */
    public client(){
        TOTAL_PASSWORDS++;
    }



    public static void main(String[] args) {
        client myClient = new client();
        System.out.println(myClient.createPassword());

}
}