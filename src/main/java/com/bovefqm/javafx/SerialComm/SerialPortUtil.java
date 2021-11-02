package com.bovefqm.javafx.SerialComm;

import gnu.io.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.TooManyListenersException;

public class SerialPortUtil {

    @SuppressWarnings("unchecked")

    public static List<String> getSerialPortNameList() {
        List<String> systemPorts = new ArrayList<>();
        //获得系统可用的端口
        Enumeration<CommPortIdentifier> portList = CommPortIdentifier.getPortIdentifiers();
        while (portList.hasMoreElements()) {
            String portName = portList.nextElement().getName();//获得端口的名字
            systemPorts.add(portName);
        }
        return systemPorts;
    }

    public static List<CommPortIdentifier> getSerialPortList(){
        List<CommPortIdentifier> ports = new ArrayList<>();
        Enumeration<CommPortIdentifier> portList = CommPortIdentifier.getPortIdentifiers();
        while (portList.hasMoreElements()) {

            ports.add(portList.nextElement());;//获得端口的ID
        }

        return ports;
    }

    public static SerialPort openSerialPort(String serialPortName)
            throws NoSuchPortException, PortInUseException, UnsupportedCommOperationException {
        SerialPortParameterVO parameter = new SerialPortParameterVO(serialPortName);
        return openSerialPort(parameter);
    }

    public static SerialPort openSerialPort(String serialPortName, int baudRate)
            throws NoSuchPortException, PortInUseException, UnsupportedCommOperationException {
        SerialPortParameterVO parameter = new SerialPortParameterVO(serialPortName, baudRate);
        return openSerialPort(parameter);
    }

    public static SerialPort openSerialPort(String serialPortName, int baudRate, int timeout)
            throws NoSuchPortException, PortInUseException, UnsupportedCommOperationException {
        SerialPortParameterVO parameter = new SerialPortParameterVO(serialPortName, baudRate);
        return openSerialPort(parameter, timeout);
    }

    public static SerialPort openSerialPort(SerialPortParameterVO parameter)
            throws NoSuchPortException, PortInUseException, UnsupportedCommOperationException {
        return openSerialPort(parameter, 5000);
        //return openSerialPort(parameter);
    }

    public static SerialPort openSerialPort(SerialPortParameterVO parameter, int timeout)
            throws NoSuchPortException, PortInUseException, UnsupportedCommOperationException {
        //通过端口名称得到端口
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(parameter.getSerialPortName());
        //打开端口，（自定义名字，打开超时时间）
        CommPort commPort = portIdentifier.open(parameter.getSerialPortName(), timeout);
        //判断是不是串口
        if (commPort instanceof SerialPort) {
            SerialPort serialPort = (SerialPort) commPort;
            //设置串口参数（波特率，数据位8，停止位1，校验位无）
            serialPort.setSerialPortParams(parameter.getBaudRate(), parameter.getDataBits(), parameter.getStopBits(), parameter.getParity());
            System.out.println("开启串口成功，串口名称：" + parameter.getSerialPortName());
            return serialPort;
        } else {
            //是其他类型的端口
            throw new NoSuchPortException();
        }
    }

    public static void closeSerialPort(SerialPort serialPort) {
        if (serialPort != null) {
            serialPort.close();
            System.out.println("关闭了串口：" + serialPort.getName());
        }
    }

    public static void sendData(SerialPort serialPort, byte[] data) {
        OutputStream os = null;
        try {
            //获得串口的输出流
            os = serialPort.getOutputStream();
            System.out.println(data);
            os.write(data);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static byte[] readData(SerialPort serialPort) {
        InputStream is = null;
        byte[] bytes = null;

        try {
//            BufferedReader bf = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
//            if (bf.readLine().length()!=-1) {
//            }
            //-------------------------
            //获得串口的输入流
            is = serialPort.getInputStream();
            //获得数据长度

            int bufflenth = is.available();

            while (bufflenth != 0) {
                //初始化byte数组
                bytes = new byte[bufflenth];
                is.read(bytes);
                bufflenth = is.available();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }

    public static void setListenerToSerialPort(SerialPort serialPort, SerialPortEventListener listener) throws TooManyListenersException {
        //给串口添加事件监听
        System.out.println(listener);
        if(listener.equals(null)) {
            serialPort.addEventListener(listener);
            //设置串口有数据的事件true有效,false无效
            serialPort.notifyOnDataAvailable(true);
            //设置中断事件true有效,false无效
            serialPort.notifyOnBreakInterrupt(true);
        }else{
            serialPort.removeEventListener();
            serialPort.addEventListener(listener);
            //设置串口有数据的事件true有效,false无效
            serialPort.notifyOnDataAvailable(true);
            //设置中断事件true有效,false无效
            serialPort.notifyOnBreakInterrupt(true);
        }
    }

}