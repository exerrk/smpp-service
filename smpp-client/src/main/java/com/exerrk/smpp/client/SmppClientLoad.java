package com.exerrk.smpp.client;

/**
 * Created by ifsidorov on 31.01.2017.
 */
public class SmppClientLoad {
    public static void main(String arg []){
        SmppClient smppClient = new SmppClient(arg[0],Integer.valueOf(arg[1]));
        smppClient.send(arg[2]);
    }
}
