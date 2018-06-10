package com.communication;

import com.company.Agent;

public enum Message {
    MOVE("MOVE"),
    MOVE_IMPOSSIBLE("IMPOSSIBLE TO MOVE");

    private String message = "";
    private Agent emetteur;

    Message(String message){
        this.message = message;
    }

    public Agent getEmetteur() {
        return emetteur;
    }

    public void setEmetteur(Agent emetteur) {
        this.emetteur = emetteur;
    }
}
