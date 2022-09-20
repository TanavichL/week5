package com.example.week5;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Objects;

@RestController
public class WordPublisher {
    protected Word words;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    public WordPublisher(){
        words = new Word();
        words.badWords.add("fuck");
        words.badWords.add("olo");
        words.goodWords.add("happy");
        words.goodWords.add("enjoy");
        words.goodWords.add("life");
    }
    @RequestMapping(value = "/addBad/{word}", method = RequestMethod.POST)
    public ArrayList<String> addBadWord(@PathVariable("word") String s){
        words.badWords.add(s);
        return words.badWords;
    }
    @RequestMapping(value = "/delBad/{word}", method = RequestMethod.GET)
    public ArrayList<String> deleteBadWord(@PathVariable("word") String s){
        words.badWords.remove(words.badWords.indexOf(s));
        return words.badWords;
    }

    @RequestMapping(value = "/addGood/{word}", method = RequestMethod.POST)
    public ArrayList<String> addGoodWord(@PathVariable("word") String s){
        words.goodWords.add(s);
        return words.goodWords;
    }
    @RequestMapping(value = "/delGood/{word}", method = RequestMethod.GET)
    public ArrayList<String> deleteGoodWord(@PathVariable("word") String s){
        words.goodWords.remove(words.goodWords.indexOf(s));
        return words.goodWords;
    }

    @RequestMapping(value = "/proof/{sentence}", method = RequestMethod.POST)
    public void proofSentence(@PathVariable("sentence") String s){
        boolean checkBad = false, checkGood = false;
        for (String text: words.badWords)
        {
            if(s.contains(text)){
                checkBad = true;
            }
        }
        for (String text: words.goodWords)
        {
            if (s.contains(text)) {
                checkGood = true;
            }
        }
        if(checkBad && checkGood){
            rabbitTemplate.convertAndSend("Fanout", "", s);
        }else if(checkBad){
            rabbitTemplate.convertAndSend("Direct", "bad", s);
        }else{
            rabbitTemplate.convertAndSend("Direct", "good", s);
        }

    }

    @RequestMapping(value = "/getSentence", method = RequestMethod.GET)
    public Sentence getSentence(){
        Object obj = rabbitTemplate.convertSendAndReceive("Direct", "", "");
        return (Sentence)obj;
    }
}
