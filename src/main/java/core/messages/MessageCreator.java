/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.messages;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Пажылой ай3
 */
public class MessageCreator {
    static private List<String> messages;
    private static MessageCreator instance = new MessageCreator();
    
    private MessageCreator() {
        messages = new ArrayList<>();
    }
    
    public static MessageCreator getInstance() {
        return instance;
    }
    
    public void addMessage(HttpSession session, String message) {
        messages.add(message);
        setMessages(session);
    }
    
    public void clearMessages() {
        messages.clear();
    }

    private static void setMessages(HttpSession session) {
        String finalErrorMessage = "";
        for (int i = 0; i< messages.size(); i++) {
            finalErrorMessage += messages.get(i) + System.lineSeparator();
        }
        session.setAttribute("Message", finalErrorMessage);
    }
}