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

        //Setting up configurations for the email connection to the Google SMTP server using TLS
        Properties props = new Properties();
        props.put("mail.smtp.host", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp-out.atena.pl");
        props.put("mail.smtp.port", "26");
        props.put("mail.smtp.auth", "true");

        //Establishing a session with required user details
        Session session = Session.getInstance(props, new Authenticator()
        {
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication("aiptest", "sd!jZq@EYMcmMw9M");
            }
        });

        try
        {
            //Creating a Message object to set the email content
            MimeMessage msg = new MimeMessage(session);
            //Storing the comma seperated values to email addresses
            String to = "robertperleje@gmail.com";
            /*Parsing the String with defualt delimiter as a comma by marking the boolean as true and storing the email
            addresses in an array of InternetAddress objects*/
            InternetAddress[] address = InternetAddress.parse(to, true);
            //Setting the recepients from the address variable
            msg.setRecipients(Message.RecipientType.TO, address);
            String timeStamp = new SimpleDateFormat("yyyymmdd_hh-mm-ss").format(new Date());
            msg.setSubject("Sample Mail : " + timeStamp);
            msg.setSentDate(new Date());
            msg.setText("Sampel System Generated mail");
            msg.setHeader("XPriority", "1");
            Transport.send(msg);
            System.out.println("Mail has been sent successfully");

        } catch (MessagingException mex)
        {
            System.out.println("Unable to send an email" + mex);
        }
    }

}