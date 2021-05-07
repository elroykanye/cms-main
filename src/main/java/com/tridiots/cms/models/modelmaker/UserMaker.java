package com.tridiots.cms.models.modelmaker;

import com.tridiots.cms.models.User;

import java.sql.Date;

public class UserMaker {
    User user = new User();
    public UserMaker(int userId,
                String userName, String userEmail,
                String userFirstName, String userLastName,
                String userGender, Date userDob,
                boolean userVerified) {
        user.setUserId(userId);
        user.setUserName(userName);
        user.setUserEmail(userEmail);
        user.setUserFirstName(userFirstName);
        user.setUserLastName(userLastName);
        user.setUserGender(userGender);
        user.setUserDob(userDob);
        user.setUserVerified(userVerified);
    }

    

    public User getUser() {
        return user;
    }
}
