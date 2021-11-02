package com.bovefqm.javafx.SerialComm;

import gnu.io.SerialPort;

import java.io.Serializable;

public final class SerialPortParameterVO implements Serializable {

    private static final long serialVersionUID = 8874815910121927542L;

    /**
     * 串口名称(COM0、COM1、COM2等等)
     */
    private String serialPortName;
    /**
     * 波特率
     * 默认：115200
     */
    private int baudRate;
    /**
     * 数据位 默认8位
     * 可以设置的值：SerialPort.DATABITS_5、SerialPort.DATABITS_6、SerialPort.DATABITS_7、SerialPort.DATABITS_8
     * 默认：SerialPort.DATABITS_8
     */
    private int dataBits;
    /**
     * 停止位
     * 可以设置的值：SerialPort.STOPBITS_1、SerialPort.STOPBITS_2、SerialPort.STOPBITS_1_5
     * 默认：SerialPort.STOPBITS_1
     */
    private int stopBits;
    /**
     * 校验位
     * 可以设置的值：SerialPort.PARITY_NONE、SerialPort.PARITY_ODD、SerialPort.PARITY_EVEN、SerialPort.PARITY_MARK、SerialPort.PARITY_SPACE
     * 默认：SerialPort.PARITY_NONE
     */
    private int parity;

    public SerialPortParameterVO(String serialPortName) {
        this.serialPortName = serialPortName;
        this.baudRate = 115200;
        this.dataBits = SerialPort.DATABITS_8;
        this.stopBits = SerialPort.STOPBITS_1;
        this.parity = SerialPort.PARITY_EVEN;
    }

    public SerialPortParameterVO(String serialPortName, int baudRate) {
        this.serialPortName = serialPortName;
        this.baudRate = baudRate;
        this.dataBits = SerialPort.DATABITS_8;
        this.stopBits = SerialPort.STOPBITS_1;
        this.parity = SerialPort.PARITY_EVEN;
    }

    public SerialPortParameterVO() {

    }

    public String getSerialPortName() {
        return serialPortName;
    }

    public void setSerialPortName(String serialPortName) {
        this.serialPortName = serialPortName;
    }

    public int getBaudRate() {
        return baudRate;
    }

    public void setBaudRate(int baudRate) {
        this.baudRate = baudRate;
    }

    public int getDataBits() {
        return dataBits;
    }

    public void setDataBits(int dataBits) {
        switch (dataBits){
            case 0:
                this.dataBits = SerialPort.DATABITS_8;
            case 1:
                this.dataBits = SerialPort.DATABITS_7;
            case 2:
                this.dataBits = SerialPort.DATABITS_6;
            case 3:
                this.dataBits = SerialPort.DATABITS_5;
            default:
                this.dataBits = SerialPort.DATABITS_8;
        }

    }

    public int getStopBits() {
        return stopBits;
    }

    public void setStopBits(int stopBits) {
        switch (stopBits){
            case 0:
                this.stopBits = SerialPort.STOPBITS_1;
            case 1:
                this.stopBits = SerialPort.STOPBITS_2;
            case 2:
                this.stopBits = SerialPort.STOPBITS_1_5;
            default:
                this.stopBits = SerialPort.STOPBITS_1;
        }

    }

    public int getParity() {
        return parity;
    }

    public void setParity(int parity) {
        switch (parity){
            case 0:
                this.parity = SerialPort.PARITY_EVEN;
            case 1:
                this.parity = SerialPort.PARITY_ODD;
            case 2:
                this.parity = SerialPort.PARITY_MARK;
            case 3:
                this.parity = SerialPort.PARITY_SPACE;
            default:
                this.parity = SerialPort.PARITY_EVEN;
        }

    }
}
