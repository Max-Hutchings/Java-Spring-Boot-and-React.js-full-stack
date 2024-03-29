package com.dfchallenge.twitterclone.validators;

public class AccountValidators {


    public static void checkEmail(String email){
        if(email == null || !email.contains("@")){
            throw new IllegalArgumentException("Invalid email");
        }
    }

    public static void checkUsername(String username){
        if(username == null || username.isEmpty()){
            throw new IllegalArgumentException("Invalid username");
        }
    }


    public static void checkFName(String fName){
        if(fName == null || fName.isEmpty()){
            throw new IllegalArgumentException("Invalid fName");
        }
    }

    public static void checkLName(String lName){
        if(lName == null || lName.isEmpty()){
            throw new IllegalArgumentException("Invalid lName");
        }
    }

    public static void checkPassword(String password) {
        if (password == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }
        if (password.length() < 5) {
            throw new IllegalArgumentException("Password too short");
        }
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{5,}$";
        if (!password.matches(regex)) {
            throw new IllegalArgumentException("Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character");
        }
    }


}
