package com.bovefqm.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


//https://www.bilibili.com/video/BV1av411B7LC/
@SpringBootApplication
public class JavaFxApplication extends Application {

    private static ApplicationContext applicationContext;
    public static FXMLLoader loadfxml(String fxmlPath){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(JavaFxApplication.class.getResource(fxmlPath));
        fxmlLoader.setControllerFactory(applicationContext::getBean);
        return fxmlLoader;
    }
    public static void main(String[] args) {
        applicationContext = SpringApplication.run(JavaFxApplication.class, args);
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(loadfxml("/FxmlViews/test.fxml").load()));
        primaryStage.setTitle("系统登录");

        primaryStage.show();

    }
}
