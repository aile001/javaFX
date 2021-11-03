package com.bovefqm.javafx.controller;

import com.bovefqm.javafx.SerialComm.SerialPortUtil;
import com.bovefqm.javafx.services.SendAtCommandService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
    public RadioButton rdString;
    public RadioButton rdHex;
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
        openCOM.setDisable(cmComPort.getSelectionModel().isEmpty());
        cmComPort.getSelectionModel().selectFirst();
        cmComBaudRate.getSelectionModel().selectFirst();
        cmComDataBit.getSelectionModel().selectFirst();
        cmComStopBit.getSelectionModel().selectFirst();
        cmComParity.getSelectionModel().selectFirst();
        cmComHandShak.getSelectionModel().selectFirst();
        ToggleGroup swicher = new ToggleGroup();
        rdHex.setToggleGroup(swicher);
        rdString.setToggleGroup(swicher);
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

    public void btCommandAction() {

        String atstr = atCommand.getText();
        atCommandService.sendatcommder(atstr,atResult);
    }

    public void txClearAtion() {
        atResult.clear();
    }

    public void rdStringAction() {
        atCommandService.rdStringHandler(atResult);
    }

    public void rdHexAction() {
        atCommandService.rdHexHandler(atResult);
    }
}
