package com.example.week5;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;


@Route(value = "/index2")
public class MyView2 extends FormLayout {
    private Button addGood, addBad, addSen, showSen;
    private TextField addWord, addSent;
    private ComboBox goodBox, badBox;
    private TextArea goodArea, badArea;
    public MyView2(){
        this.setResponsiveSteps(new ResponsiveStep("40em", 2));
        addWord = new TextField();
        addSent = new TextField();
        addWord.setLabel("Add Word");
        addSent.setLabel("Add Sentence");
        addGood = new Button("Add Good Word");
        addBad = new Button("Add Bad Word");
        addSen = new Button("Add Sentence");
        showSen = new Button("Show Sentence");
        badBox = new ComboBox<>();
        goodBox = new ComboBox<>();
        badBox.setLabel("Bad Words");
        goodBox.setLabel("Good Words");
        badArea = new TextArea();
        badArea.setLabel("Bad Sentences");
        badArea.setEnabled(false);
        goodArea = new TextArea();
        goodArea.setLabel("Good Sentences");
        goodArea.setEnabled(false);
        add(addWord, addSent, addGood, addSen, addBad, goodArea, goodBox, badArea, badBox, showSen);
        addGood.addClickListener(event -> {
            String text = addWord.getValue();
            ArrayList out = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/addGood/"+text)
                    .retrieve()
                    .bodyToMono(ArrayList.class)
                    .block();
            goodBox.setItems(out);
        });
        addBad.addClickListener(event -> {
            String text = addWord.getValue();
            ArrayList out = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/addBad/"+text)
                    .retrieve()
                    .bodyToMono(ArrayList.class)
                    .block();
            badBox.setItems(out);
        });
        addSen.addClickListener(event -> {
            String text = addSent.getValue();
            ArrayList out = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/proof/"+text)
                    .retrieve()
                    .bodyToMono(ArrayList.class)
                    .block();
        });
        showSen.addClickListener(event -> {
            Sentence out = WebClient.create()
                    .get()
                    .uri("http://localhost:8080/getSentence")
                    .retrieve()
                    .bodyToMono(Sentence.class)
                    .block();
            goodArea.setValue(out.goodSentence+"");
            badArea.setValue(out.badSentence+"");
        });
    }
}
