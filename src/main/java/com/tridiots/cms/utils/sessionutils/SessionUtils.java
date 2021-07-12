/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tridiots.cms.utils.sessionutils;

import javax.servlet.http.HttpSession;
/**
 *
 * @author kanye
 */
public class SessionUtils {
    enum SessionNames {
        LOGGED_IN_USER, 
        LOGGED_IN_CONTESTANT, 
        LOGGED_IN_JUDGE, 
        LOGGED_IN_ADMIN
    } 
    
    HttpSession session;
    public class SessionCreator {
        /*
        public void makeUserSession(HttpSession session, User user) {
            session.setAttribute(SessionNames.LOGGED_IN_USER, user);
        }
        public void makeContestantSession(HttpSession session, Contestant contestant) {
            session.setAttribute(SessionNames.LOGGED_IN_CONTESTANT, contestant);
        }
        public void makeJudgeSession(HttpSession session, Judge judge) {
            session.setAttribute(SessionNames.LOGGED_IN_JUDGE, judge);
        } 
        public void makeAdminSession(HttpSession session, Admin admin) {
            session.setAttribute(SessionNames.LOGGED_IN_ADMIN, admin);
        } */
    }
    
    public class SessionDestroyer {
        public void destroySession (HttpSession session) {
            session.invalidate();
        }
    }
    
}
