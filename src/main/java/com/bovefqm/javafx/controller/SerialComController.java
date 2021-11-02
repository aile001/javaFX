package com.bovefqm.javafx.controller;

import com.bovefqm.javafx.SerialComm.SerialPortUtil;
import com.bovefqm.javafx.services.SendAtCommandService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class SerialComController implements Initializable {

    @FXML
    public TextField atCommand;
    public TextArea atResult;
    public Button openCOM;
    public Button getCom;
    @FXML
    private ComboBox cmComPort;
    @FXML
    private ComboBox cmComBaudRate;
    @FXML
    private ComboBox cmComDataBit;
    @FXML
    private ComboBox cmComStopBit;
    @FXML
    private ComboBox cmComParity;
    @FXML
    private ComboBox cmComHandShak;
    @Autowired
    SendAtCommandService atCommandService;
    @Override
    public void initialize(URL url, ResourceBundle rb){
        if(cmComPort.getSelectionModel().isEmpty()){
            openCOM.setDisable(true);
        }else {
            openCOM.setDisable(false);
        }
        cmComPort.getSelectionModel().selectFirst();
        cmComBaudRate.getSelectionModel().selectFirst();
        cmComDataBit.getSelectionModel().selectFirst();
        cmComStopBit.getSelectionModel().selectFirst();
        cmComParity.getSelectionModel().selectFirst();
        cmComHandShak.getSelectionModel().selectFirst();
    }


    public void comPortSelect() {

        String coms = cmComPort.getSelectionModel().getSelectedItem().toString();
    }

    public void getComAction() {
        if(!cmComPort.getSelectionModel().isEmpty()) {
            cmComPort.getSelectionModel().clearSelection();
        }else {
            List<String> comlists;
            comlists = SerialPortUtil.getSerialPortNameList();
            ObservableList<String> comlist = FXCollections.observableArrayList(comlists);

            cmComPort.setItems(comlist);
            cmComPort.getSelectionModel().selectFirst();
            if(!cmComPort.getSelectionModel().isEmpty()){
                openCOM.setDisable(false);
            }
        }

    }

    public void openComAction() {
        boolean comisopen = atCommandService.comIsOpen();
        System.out.println(comisopen);

        if(comisopen){
            openCOM.setText("Open");
            atCommandService.closeComPort();
            cmComPort.getSelectionModel().getSelectedItem();
            cmComPort.setDisable(false);
            cmComBaudRate.setDisable(false);
            cmComDataBit.setDisable(false);
            cmComStopBit.setDisable(false);
            cmComParity.setDisable(false);
            cmComHandShak.setDisable(false);
        }else {
            openCOM.setText("close");
            atCommandService.openComPort(cmComPort,cmComBaudRate,cmComDataBit,cmComStopBit,cmComParity);
            cmComPort.setDisable(true);
            cmComBaudRate.setDisable(true);
            cmComDataBit.setDisable(true);
            cmComStopBit.setDisable(true);
            cmComParity.setDisable(true);
            cmComHandShak.setDisable(true);
        }
    }

    public void btCommandAction(ActionEvent actionEvent) {
        String atstr = atCommand.getText();
        atCommandService.sendatcommder(atstr,atResult);
    }

    public void txClearAtion(ActionEvent actionEvent) {
        atResult.clear();
    }
}
