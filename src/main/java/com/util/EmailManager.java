package com.util;

import javax.mail.*;
import javax.mail.internet.*;

import org.apache.commons.io.IOUtils;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class EmailManager {

    private String emailAddressTo = new String();
    private String msgSubject = new String();
    private String msgText = new String();

    final String USER_NAME = "";   //User name of the Goole(gmail) account
    final String PASSSWORD = "";  //Password of the Goole(gmail) account
    final String FROM_ADDRESS = USER_NAME;  //From addresss



    public EmailManager(String toAddress) {
        this.emailAddressTo = toAddress;
    }

    public void signupEmail(String link) {
        this.msgSubject = msgSubject;
        sendEmailMessage(link, "verification_email");

    }

    private void sendEmailMessage(String link, String emailType) {

        //Create email sending properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(USER_NAME, PASSSWORD);
                    }
                });

        try {
            if (emailType.equals("verification_email")) {
                msgText = getVerifyTemplate(link);
            }
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_ADDRESS)); //Set from address of the email
            message.setContent(msgText, "text/html"); //set content type of the email

            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailAddressTo)); //Set email recipient

            message.setSubject(msgSubject); //Set email message subject
            Transport.send(message); //Send email message

            System.out.println("sent email successfully!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void setEmailAddressTo(String emailAddressTo) {
        this.emailAddressTo = emailAddressTo;
    }

    public void setSubject(String subject) {
        this.msgSubject = subject;
    }

    public void setMessageText(String msgText) {
        this.msgText = msgText;
    }

    public String getVerifyTemplate(String link) {
		//insert your template here
    	String template = null;
    	try {
    		template = IOUtils.toString(new FileReader("C:/Users/chusung/Downloads/basic-email-template/test.html"));
    	}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return template;
	}
}