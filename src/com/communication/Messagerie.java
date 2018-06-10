package com.communication;

import com.company.*;

import java.util.ArrayList;

public class Messagerie {

    private ArrayList<Message> messages = new ArrayList<Message>();

    public void sendMessage(Agent emetteur, Agent destinataire, Message message){
        System.out.println("emetteur: " + emetteur.getPositionActuelle().getX() + ";" + emetteur.getPositionActuelle().getY());
        System.out.println("destinataire: " + destinataire.getPositionActuelle().getX() + ";" + destinataire.getPositionActuelle().getY());
        System.out.println("message: " + message.toString());
        message.setEmetteur(emetteur);
        destinataire.getMessagerie().getMessages().add(message);
        destinataire.setMessageRecu(true);
        destinataire.setInTransaction(true);
        if(destinataire.getIndex() == emetteur.getDestinataireDernierMessage().getIndex()) {
            emetteur.setCompteurDemandeConsecutive(emetteur.getCompteurDemandeConsecutive() + 1);
        }
        else{
            emetteur.setCompteurDemandeConsecutive(0);
            emetteur.setDestinataireDernierMessage(destinataire);
        }
        System.out.println("COMPTEUUUUUUUUUR: " + emetteur.getCompteurDemandeConsecutive());
    }


    /////////////////////////////////////////// GETTER AND SETTER ///////////////////////////////////////////


    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }
}
