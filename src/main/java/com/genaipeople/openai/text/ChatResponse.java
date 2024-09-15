package com.genaipeople.openai.text;

import java.util.List;

import com.genaipeople.openai.Choice;
import com.genaipeople.openai.Usage;

public class ChatResponse {
    String id;
    String object;
    Long created;
    String model;
    List<Choice> choices;
    Usage usage;
}