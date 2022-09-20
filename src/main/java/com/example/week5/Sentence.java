package com.example.week5;

import java.io.Serializable;
import java.util.ArrayList;

public class Sentence implements Serializable {
    public ArrayList<String>badSentence ;
    public ArrayList<String>goodSentence ;
    public Sentence(){
        badSentence = new ArrayList<String>();
        goodSentence = new ArrayList<String>();
    }
}
