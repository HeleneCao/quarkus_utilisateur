package fr.rootar;

import java.util.regex.Pattern;

public class Validator {

    public static boolean isValidMail(String infoEmail){
    String regex = "^[\\w!#$%&'+/=?`{|}~^-]+(?:\\.[\\w!#$%&'+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        return Pattern.compile(regex)
            .matcher(infoEmail)
                .matches();
    }
    public static boolean isValidPassword(String password){
        String regex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$";
        return Pattern.compile(regex)
                .matcher(password)
                .matches();
    }
}
