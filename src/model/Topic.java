/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author YoungDON
 */
public class Topic {
    private final int id;
    private String topic;

    public int getId() {
        return id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Topic(int questionID, String topic) {
        this.id = questionID;
        this.topic = topic;
    }
}
