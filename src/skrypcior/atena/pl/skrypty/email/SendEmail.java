/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.skrypty.email;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


/**
 *
 * @author perlro1
 */
public class SendEmail        
{
    static Properties mailServerProperties;
    static Session getMailSession;
    static MimeMessage generateMailMessage;
    public static void sendMail(String adres, String subject, String tresc, String nazwa, String folder, String srod) throws AddressException, MessagingException
    {

        		
		System.out.println("\n\n ===> Your Java Program has just sent an Email successfully. Check your email..");
	// Step1
		System.out.println("\n 1st ===> setup Mail Server Properties..");
		mailServerProperties = System.getProperties();
		mailServerProperties.put("mail.smtp.port", "1200");
		mailServerProperties.put("mail.smtp.auth", "true");//true
		mailServerProperties.put("mail.smtp.starttls.enable", "true");//true
                mailServerProperties.put("mail.debug", "true");
                //mailServerProperties.put("java.net.preferIPv4Stack" , "true");
		System.out.println("Mail Server Properties have been setup successfully..");
 
		// Step2
		System.out.println("\n\n 2nd ===> get Mail Session..");
		getMailSession = Session.getDefaultInstance(mailServerProperties, null);
		generateMailMessage = new MimeMessage(getMailSession);
		generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress("test@localhost.com"));
		//generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress("test2@crunchify.com"));
		generateMailMessage.setSubject(subject);
		String emailBody = tresc;
		generateMailMessage.setContent(emailBody, "text/html; charset=ISO-8859-2");
		System.out.println("Mail Session has been created successfully..");
 
		// Step3
		System.out.println("\n\n 3rd ===> Get Session and Send mail");
		Transport transport = getMailSession.getTransport("smtp");
 
		// Enter your correct gmail UserID and Password
		// if you have 2FA enabled then provide App Specific Password
		transport.connect("localhost", "postmaster", "");
		transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
		transport.close();
	}
}
