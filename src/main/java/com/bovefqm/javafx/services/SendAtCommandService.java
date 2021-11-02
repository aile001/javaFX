package com.bovefqm.javafx.services;

import com.bovefqm.javafx.SerialComm.SerialPortParameterVO;
import com.bovefqm.javafx.Utils.typeChanges;
import gnu.io.*;
import javafx.application.Platform;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import org.springframework.stereotype.Service;

import java.util.TooManyListenersException;

import static com.bovefqm.javafx.SerialComm.SerialPortUtil.*;

@Service
public  class SendAtCommandService  {
    final StringBuilder sb = new StringBuilder();
    public SerialPort comport = null;


    public void openComPort(ComboBox porname,ComboBox BaudRate,ComboBox DataBit,ComboBox StopBit,ComboBox Parity){
        String ComName = porname.getSelectionModel().getSelectedItem().toString();
        int baudRate = Integer.parseInt(BaudRate.getSelectionModel().getSelectedItem().toString());
        int dataBit = DataBit.getSelectionModel().getSelectedIndex();
        int stopBit = StopBit.getSelectionModel().getSelectedIndex();
        int parity = Parity.getSelectionModel().getSelectedIndex();
        System.out.println(ComName+""+baudRate+""+dataBit+""+stopBit+""+parity);
        SerialPortParameterVO comSerialVO= new SerialPortParameterVO();
        comSerialVO.setSerialPortName(ComName);
        comSerialVO.setBaudRate(baudRate);
        comSerialVO.setDataBits(dataBit);
        comSerialVO.setStopBits(stopBit);
        comSerialVO.setParity(parity);
        System.out.println(comSerialVO.getSerialPortName()+" "+comSerialVO.getBaudRate()+" "+comSerialVO.getDataBits()+" "+comSerialVO.getStopBits()+" "+comSerialVO.getParity());
        try {
            comport = openSerialPort(comSerialVO,baudRate);

        } catch (NoSuchPortException e) {
            e.printStackTrace();
        } catch (PortInUseException e) {
            e.printStackTrace();
        } catch (UnsupportedCommOperationException e) {
            e.printStackTrace();
        }


    }
    public void sendatcommder(String atstr,TextArea textArea) {

        sendData(comport, typeChanges.hexStringToByteArray(atstr));

       new Thread(()->{

           try {
                setListenerToSerialPort(comport, event -> {
                    //数据通知
                    byte[] bytes = null;
                    if (event.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
                        bytes = readData(comport);
                        sb.append(typeChanges.bytesToHexString(bytes));
                    } else {
                        sb.append(typeChanges.bytesToHexString(bytes));
                    }

                    switch (sb.substring(sb.length() - 2, sb.length())) {
                        case "16":
                            //System.out.println(sb.toString());
                            StringBuilder sb1 = new StringBuilder();
                            byte[] bytes1 = typeChanges.hexStringToByteArray(sb.toString());
                            for (int i = 16; i < bytes1.length - 4; i++) {
                                sb1.append((char) bytes1[i]);
                            }
                            String msgTemp=sb1.toString();
                            Platform.runLater(()-> textArea.appendText(msgTemp+"\n"+"-----------FINISHIPED-----------"+"\n"));
                            sb1.delete(0, sb1.length());
                            sb.delete(0, sb.length());
                            break;
                    }

                });

            } catch (TooManyListenersException e) {
                e.printStackTrace();
            }
        }).start();
    }
    public boolean comIsOpen(){
        if(comport==null){
            return false;
        }else{
            return true;
        }
    }
    public void closeComPort(){
        closeSerialPort(comport);
        comport = null;
    }
}
