package com.bovefqm.javafx.services;

import com.bovefqm.javafx.Utils.typeChanges;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;

@Service
public class IPScanService {
    public void startScan(int beginIP, int endIP, TextArea result){
        new Thread(() -> {
                for (int i = beginIP; i <= endIP; i++) {
                    //判断线程状态标志，如果被终止则停止线程
//                    if (readThread.isInterrupted()){
//                        readThread.interrupt();
//                        result.appendText("终止扫描！");
//                        break;
//                    }
                    String ip = typeChanges.longToIp(i);
                    try {
                        InetAddress address = InetAddress.getByName(ip);
                        boolean status = address.isReachable(200);
                        Platform.runLater(() -> {
                            if (status)
                                result.appendText(address + " is reachable.\n");
                            else
                                result.appendText(address + " is not reachable.\n");
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                result.appendText("扫描结束！\n");
            }).start();

        }
   public void stopScan(){

   }
   public void cmdExcute(String cmd,TextArea textArea){
       new Thread(()->{
           try {
               Process process = Runtime.getRuntime().exec(cmd);
               InputStream in = process.getInputStream();
               BufferedReader br=new BufferedReader(new InputStreamReader(in, "GB2312"));

               String msg;
               while ((msg=br.readLine())!=null){
                   String msgTemp=msg;
                   Platform.runLater(()-> textArea.appendText(msgTemp+"\n"));
               }
           }
           catch (IOException e) {
               System.err.println(e.getMessage());
           }
       }).start();
   }



}

