package com.exerrk.smpp.client;

import org.jsmpp.InvalidResponseException;
import org.jsmpp.PDUException;
import org.jsmpp.bean.*;
import org.jsmpp.extra.NegativeResponseException;
import org.jsmpp.extra.ResponseTimeoutException;
import org.jsmpp.session.BindParameter;
import org.jsmpp.session.SMPPSession;
import org.jsmpp.util.AbsoluteTimeFormatter;
import org.jsmpp.util.TimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;

/**
 * Created by ifsidorov on 31.01.2017.
 */
public class SmppClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(SmppClient.class);
    private static final TimeFormatter TIME_FORMATTER = new AbsoluteTimeFormatter();

    private String host;
    private int port;
    private SMPPSession session;

    public SmppClient(String host, int port) {
        this.host = host;
        this.port = port;
        this.session = new SMPPSession();
    }

    public String connect(){
        try {
            LOGGER.info("Connecting");
            String systemId = session.connectAndBind(host, port, new BindParameter(BindType.BIND_TX, "j", "jpwda", "cp", TypeOfNumber.UNKNOWN, NumberingPlanIndicator.UNKNOWN, null));
            LOGGER.info("Connected with SMSC with system id {}", systemId);
            return systemId;
        } catch (IOException e) {
            LOGGER.error("Failed connect and bind to host", e);
            return "";
        }
    }

    public void close(){
        session.unbindAndClose();
    }

    public void send (String message){
        try {
            String messageId = session.submitShortMessage("CMT",
                    TypeOfNumber.INTERNATIONAL, NumberingPlanIndicator.UNKNOWN, "1616",
                    TypeOfNumber.INTERNATIONAL, NumberingPlanIndicator.UNKNOWN, "628176504657",
                    new ESMClass(), (byte)0, (byte)1,  TIME_FORMATTER.format(new Date()), null,
                    new RegisteredDelivery(SMSCDeliveryReceipt.DEFAULT), (byte)0, new GeneralDataCoding(Alphabet.ALPHA_DEFAULT, MessageClass.CLASS1, false), (byte)0,
                    message.getBytes());
            LOGGER.info("Message submitted, message_id is {}", messageId);
        } catch (PDUException e) {
            // Invalid PDU parameter
            LOGGER.error("Invalid PDU parameter", e);
        } catch (ResponseTimeoutException e) {
            // Response timeout
            LOGGER.error("Response timeout", e);
        } catch (InvalidResponseException e) {
            // Invalid response
            LOGGER.error("Receive invalid response", e);
        } catch (NegativeResponseException e) {
            // Receiving negative response (non-zero command_status)
            LOGGER.error("Receive negative response, e");
        } catch (IOException e) {
            LOGGER.error("IO error occured", e);
        }
    }
}
