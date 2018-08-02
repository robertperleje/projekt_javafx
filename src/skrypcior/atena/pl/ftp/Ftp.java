/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.ftp;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author perlro1
 */
public class Ftp
{

    private final SimpleIntegerProperty id;
    private final SimpleStringProperty host;
    private final SimpleIntegerProperty port;
    private final SimpleStringProperty login;
    //private final SimpleStringProperty pass;
    private final SimpleStringProperty modul;
    private final SimpleStringProperty sciezka;
    private final SimpleStringProperty data_utw;
    private final SimpleStringProperty operator;

    public Ftp(Integer id, String host, Integer port, String login, /*String pass,*/ String modul, String sciezka, String data_utw, String operator)
    {
        this.id = new SimpleIntegerProperty(id);
        this.host = new SimpleStringProperty(host);
        this.port = new SimpleIntegerProperty(port);
        this.login = new SimpleStringProperty(login);
        //this.pass = new SimpleStringProperty(pass);
        this.modul = new SimpleStringProperty(modul);
        this.sciezka = new SimpleStringProperty(sciezka);
        this.data_utw = new SimpleStringProperty(data_utw);
        this.operator = new SimpleStringProperty(operator);
    }

    public Integer getId()
    {
        return id.get();
    }

    public String getHost()
    {
        return host.get();
    }
    
    public Integer getPort()
    {
        return port.get();
    }
    
    public String getLogin()
    {
        return login.get();
    }
/*
    public String getPass()
    {
        return pass.get();
    }
    */
    public String getModul()
    {
        return modul.get();
    }

    public String getSciezka()
    {
        return sciezka.get();
    }

    public String getData_utw()
    {
        return data_utw.get();
    }

    public String getOperator()
    {
        return operator.get();
    }
}
