/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.skrypty.email;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.CheckBox;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author perlro1
 */
public class Email
{
    static Properties mailServerProperties;
    static Session getMailSession;
    static MimeMessage generateMailMessage;

    private final SimpleIntegerProperty id;
    private final SimpleStringProperty email;
    //private CheckBox adresat;
    private final SimpleBooleanProperty adresat;
    private final SimpleStringProperty grupa;
    private final SimpleStringProperty data_utw;
    private final SimpleStringProperty operator;

    public Email(Integer id, String email, Boolean adresat, String grupa, String data_utw, String operator)
    {
        this.id = new SimpleIntegerProperty(id);
        this.email = new SimpleStringProperty(email);
        //this.adresat = new CheckBox();
        this.adresat = new SimpleBooleanProperty(adresat);
        this.grupa = new SimpleStringProperty(grupa);
        this.data_utw = new SimpleStringProperty(data_utw);
        this.operator = new SimpleStringProperty(operator);
    }

    public Integer getId()
    {
        return id.get();
    }

    public String getEmail()
    {
        return email.get();
    }

    /*
    public CheckBox getAdresat() {
        return adresat;
    }
     */
    public Boolean getAdresat()
    {
        return adresat.get();
    }

    public String getGrupa()
    {
        return grupa.get();
    }

    public String getData_utw()
    {
        return data_utw.get();
    }

    public String getOperator()
    {
        return operator.get();
    }

    public static void sendMail(String adres, String tytul, String tresc) throws AddressException, MessagingException
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
		generateMailMessage.setSubject("Greetings from Crunchify..");
		String emailBody = "Test email by Crunchify.com JavaMail API example. " + "<br><br> Regards, <br>Crunchify Admin";
		generateMailMessage.setContent(emailBody, "text/html");
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
        
    

