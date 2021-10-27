package com.bovefqm.javafx.services;

import org.springframework.stereotype.Service;

@Service
public class loginService {
    public boolean login(String username,String password){
        return username.equals("admin")&&password.equals("admin");
    }
}
