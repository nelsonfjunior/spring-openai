package com.bookstore.ai.controllers;

import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/bookstore")
public class BookStoreAssistantController {
    @Autowired
    private OpenAiChatClient chatClient;

//    @GetMapping("/informations")
//    public String bookStoreChat(@RequestParam(value = "message",
//            defaultValue = "Quais são os livros best sellers dos ultimos anos?") String message){
//        return chatClient.call(message);
//    }

//    @GetMapping("/informations")
//    public ChatResponse bookStoreChatEx2(@RequestParam(value = "message",
//            defaultValue = "Quais são os livros best sellers dos ultimos anos?") String message){
//        return chatClient.call(new Prompt(message)); // apareceo json do chat
//    }

    @PostMapping("/ask")
    public ChatResponse askQuestion(@RequestBody String question){
        return chatClient.call(new Prompt(question));
    }

    @GetMapping("/reviews")
    public String bookStoreReview(@RequestParam(value = "book", defaultValue = "Dom Quixote") String book){
        PromptTemplate promptTemplate = new PromptTemplate("""
                Por favor, me forneca um 
                breve resumo do livro {book}
                e também a biografia de seu autor.
                """);
        promptTemplate.add("book", book);
        return this.chatClient.call(promptTemplate.create()).getResult().getOutput().getContent();
    }

    @GetMapping("/stream/informations")
    public Flux<String> bookStoreChatStream(@RequestParam(value = "message",
            defaultValue = "Quais são os livros best sellers dos ultimos anos?") String message){
        return chatClient.stream(message);
    }

//    @GetMapping("/stream/informations")
//    public Flux<ChatResponse> bookStoreChatStream(@RequestParam(value = "message",
//            defaultValue = "Quais são os livros best sellers dos ultimos anos?") String message){
//        return chatClient.stream(new Prompt(message));
//    }

}
