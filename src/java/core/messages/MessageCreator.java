/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.messages;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Пажылой ай3
 */
public class MessageCreator {
    static HttpServletRequest request;
    static private List<String> messages;
    
    public MessageCreator(HttpServletRequest request) {
        MessageCreator.request = request;
        messages = new ArrayList<>();
    }
    
    public static void addMessage(String message) {
        messages.add(message);
        setMessages();
    }
    
    public static void clearMessages() {
        messages.clear();
    }

    private static void setMessages() {
        String finalErrorMessage = "";
        for (int i = 0; i< messages.size(); i++) {
            finalErrorMessage += messages.get(i) + System.lineSeparator();
        }
        request.getSession().setAttribute("Message", finalErrorMessage);
    }
}