package com.ayush.chatApplication.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage {
    private String message;
    private String sender;
    private MessageType type;

    public enum MessageType{
        CHAT,
        JOIN,
        LEFT
    }
}
