package com.example.smashinfo.data;

public class DataUser {

    public String mail;
    public String pseudo;
    public String deckMajeur;
    public int nbPartie, nbVictoire, piece;

    public DataUser() {
    }

    public DataUser(String mail, String pseudo, String deckMajeur, int nbPartie, int nbVictoire, int piece) {
        this.mail = mail;
        this.pseudo = pseudo;
        this.deckMajeur = deckMajeur;
        this.nbPartie = nbPartie;
        this.nbVictoire = nbVictoire;
        this.piece = piece;
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

    public String getDeckMajeur() { return deckMajeur; }

    public void setDeckMajeur(String deckMajeur) { this.deckMajeur = deckMajeur; }

    public int getNbPartie() { return nbPartie; }

    public void setNbPartie(int nbPartie) { this.nbPartie = nbPartie; }

    public int getNbVictoire() { return nbVictoire; }

    public void setNbVictoire(int nbVictoire) { this.nbVictoire = nbVictoire; }

    public int getPiece() { return piece; }

    public void setPiece(int piece) { this.piece = piece; }
}
