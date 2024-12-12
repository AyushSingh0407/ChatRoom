package com.ayush.chatApplication.controller;

import com.ayush.chatApplication.model.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        // If the message type is JOIN or LEFT, set a custom message
        if (chatMessage.getType() == ChatMessage.MessageType.JOIN) {
            chatMessage.setMessage(chatMessage.getSender() + " has joined!");
        } else if (chatMessage.getType() == ChatMessage.MessageType.LEFT) {
            chatMessage.setMessage(chatMessage.getSender() + " has left!");
        }
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        // Store the username in the session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());

        // Set a custom message for JOIN type
        chatMessage.setMessage(chatMessage.getSender() + " has joined!");
        chatMessage.setType(ChatMessage.MessageType.JOIN);  // Ensure the message type is JOIN
        return chatMessage;
    }

    @MessageMapping("/chat.removeUser")
    @SendTo("/topic/public")
    public ChatMessage removeUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        // Set a custom message for LEFT type
        chatMessage.setMessage(chatMessage.getSender() + " has left!");
        chatMessage.setType(ChatMessage.MessageType.LEFT);  // Ensure the message type is LEFT
        return chatMessage;
    }
}

