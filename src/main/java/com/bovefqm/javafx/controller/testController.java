package com.bovefqm.javafx.controller;


import com.bovefqm.javafx.services.loginService;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.io.IOException;

import static com.bovefqm.javafx.JavaFxApplication.loadfxml;

@Component
public class testController {
    @Autowired
    loginService loginService;
    public PasswordField txtPassword;
    public TextField txtUsername;

    public void btLoginClick() throws IOException{
        String username= txtUsername.getText();
        String password = txtPassword.getText();
       if(loginService.login(username,password)){
           openWindow();
       }else {
           alertLoginFail();
       }

    }

    private void alertLoginFail() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("用户名或密码错误！！");
        alert.showAndWait();
    }

    private void openWindow() throws IOException {
        //to do something
        Stage mainStage = new Stage();
        mainStage.setScene(new Scene(loadfxml("/FxmlViews/Ipscan.fxml").load()));
        mainStage.show();
        Window window = txtUsername.getScene().getWindow();
        if(window instanceof Stage){
            ((Stage)window).close();
        }
    }
}
