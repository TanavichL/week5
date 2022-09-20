package com.example.week5;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class SentenceCustomer {
    protected Sentence sentences;
    public SentenceCustomer(){
        sentences = new Sentence();
    }

    @RabbitListener(queues = "BadWordQueue")
    public void addBadSentence(String s){
        sentences.badSentence.add(s);
        System.out.println("In addBadSentence Method : " + sentences.badSentence);
    }

    @RabbitListener(queues = "GoodWordQueue")
    public void addGoodSentence(String s){
        sentences.goodSentence.add(s);
        System.out.println("In addGoodSentence Method : " + sentences.goodSentence);
    }

    @RabbitListener(queues = "GetQueue")
    public Sentence getSentence(){
        return sentences;
    }
}
