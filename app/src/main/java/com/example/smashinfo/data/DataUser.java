package com.example.smashinfo.data;

public class DataUser {

    public String mail;
    public String pseudo;

    public DataUser() {
    }

    public DataUser(String mail, String pseudo) {
        this.mail = mail;
        this.pseudo = pseudo;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
         this.mail = mail;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
}
