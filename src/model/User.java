/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author YoungDON
 */
public class User {
    private final String username;
    private final String password;
    private final String rePass;

    public User(String username, String password, String rePass) {
        this.username = username;
        this.password = password;
        this.rePass = rePass;
    }

    public boolean verifyUsername(){
        return username.matches("\\w+");
    }
    
    public boolean verifyPassword(){
        return password.length() >= 1;
    }
    
    public boolean verifyRePass(){
        return password.equals(rePass);
    }
}
