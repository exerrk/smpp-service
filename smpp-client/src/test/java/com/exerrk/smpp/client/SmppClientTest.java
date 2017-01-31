package com.exerrk.smpp.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by ifsidorov on 31.01.2017.
 */
public class SmppClientTest {
    @org.junit.Test
    public void send() throws Exception {
        int size = 1;
        List<SmppClient> smppClients = new ArrayList<SmppClient>();
        for (int i = 0; i < size; i++){
            smppClients.add(new SmppClient("localhost",8056));
        }

        long allTime = new Date().getTime();
        long connectionTime = new Date().getTime();
        for (int i = 0; i < size; i++){
            smppClients.get(i).connect();
        }
        System.out.println("Connection time: " + (new Date().getTime() - connectionTime));

        long sendTime = new Date().getTime();
//        for (int i = 0; i < size; i++){
//            smppClients.get(i).send();
//        }
        for (int i = 0; i < 1; i++){
            smppClients.get(0).send("Добро пожаловать в REGION_NAME!");
        }
        System.out.println("Send time: " + (new Date().getTime() - sendTime));

        long closeTime = new Date().getTime();
        for (int i = 0; i < size; i++){
            smppClients.get(i).close();
        }
        System.out.println("Close time: " + (new Date().getTime() - closeTime));
        System.out.println("All time: " + (new Date().getTime() - allTime));
    }

}