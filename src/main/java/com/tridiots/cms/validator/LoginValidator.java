package com.tridiots.cms.validator;

import com.tridiots.cms.message.Message;
import com.tridiots.cms.utils.modeldao.UserUtils;

public class LoginValidator {
    private static char[] acceptedChars = new char[]
            {'!','@','#','$','%','^','&','*','(',')','+','=',
            ']','[','}','{','"','\'',':',';','>','<','.',',','/','\\',
                    '|', '`', '~'};

    public static Message loginExists(String login) {
        return UserUtils.findUser(login);
    }
    public static Message loginCharsOkay (String login) {
        char[] loginToArray = login.toCharArray();
        for(char element : loginToArray) {
            if(contains(element, acceptedChars)) return new Message("Please avoid use of special characters in your login...", false);
        } return new Message("Login okay", true);
    }

    /*** Helpers ***/
    private static boolean contains (char element, char[] space) {
        for(char s: space) {
            if(s == element) return true;
        } return false;
    }
}
