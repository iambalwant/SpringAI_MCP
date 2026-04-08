package com.springai.mcp.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai")
public class AiController {


    private ChatClient chatClient;

    private Logger logger = LoggerFactory.getLogger(AiController.class);

    public AiController(ChatClient.Builder chatClientBuilder, ToolCallbackProvider toolCallbackProvider){
        this.chatClient = chatClientBuilder
                .defaultToolCallbacks(toolCallbackProvider)
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }

    @GetMapping
    public ResponseEntity<String> getAiResponce(
            @RequestParam("query") String query
    ){

        String Responce = chatClient.prompt(query)
                .call()
                .content();

        return new ResponseEntity<>(Responce, HttpStatus.OK);
    }


}
