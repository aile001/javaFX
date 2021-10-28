package com.bovefqm.javafx.controller;

import com.bovefqm.javafx.Utils.typeChanges;
import com.bovefqm.javafx.services.IPScanService;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IPScanCotroller {

    public TextField txCmd;
    @Autowired
    IPScanService ipScanService;
    public TextField txtBeginIP;
    public TextField txtEndIP;
    public TextArea txResult;
    public void btStopScanAction() {
        ipScanService.stopScan();
    }

    public void btScanAction() {
        int beginip = typeChanges.ipToLong(txtBeginIP.getText());
        int endip= typeChanges.ipToLong(txtEndIP.getText());
        System.out.println(beginip+""+endip);
        ipScanService.startScan(beginip,endip,txResult);
    }

    public void btExcuteCmdAction() {
        String cmd = txCmd.getText();
        ipScanService.cmdExcute(cmd,txResult);
    }
}
