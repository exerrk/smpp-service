package com.exerrk.smpp.server;

/**
 * Created by ifsidorov on 31.01.2017.
 */
public class SmppServerLoad {
    private static final Integer DEFAULT_PORT = 8056;
    private static final String DEFAULT_SYSID = "j";
    private static final String DEFAULT_PASSWORD = "jpwd";
    public static void main(String [] arg){
        String systemId = System.getProperty("jsmpp.client.systemId", DEFAULT_SYSID);
        String password = System.getProperty("jsmpp.client.password", DEFAULT_PASSWORD);
        int port;
        try {
            if (arg.length > 0) {
                port = Integer.parseInt(arg[0]);
            } else {
                port = DEFAULT_PORT;
            }
        } catch (NumberFormatException e) {
            port = DEFAULT_PORT;
        }
        SmppServer smppServerSim = new SmppServer(port, systemId, password);
        smppServerSim.run();
    }
}
