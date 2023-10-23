/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.login;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebListener;

/**
 *
 * @author Artyom
 */
@WebListener
public class UserModel {
    private String login;
    private String pass;
    private List<String> filesPath;
    private int userId;

    //TODO записывать сессию
    private String lastSessionID;

    public UserModel(){
        login = null;
        pass = null;
        filesPath = new ArrayList<>();
    }
    
    public UserModel(String log, String pass, int userId) {
        this.login = log;
        this.pass = pass;
        this.filesPath = new ArrayList<>();
        this.userId = userId;
    }
        
    public UserModel(String log, String pass, List<String> filesPath, int userId) {
        this.login = log;
        this.pass = pass;
        this.filesPath = filesPath;
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLog(String log) {
        this.login = log;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
    
    public int getFileCount() {
        return filesPath.size();
    }
    
    public void addFile(String fileName) {
        filesPath.add(fileName);
    }
    
    public String getFileName(int index) {
        return filesPath.get(index);
    }
    
    public List<String> getFileNames() {
        return filesPath;
    }
    
    public void setFileName(String fileName, int index) {
        this.filesPath.set(index, fileName);
    }
    
    public void setFiles(List<String> filesPath) {
        this.filesPath = filesPath;
    }

    public int getID() {
        return userId;
    }

    public void setID(int ID) {
        this.userId = ID;
    }
    
    public void deleteFile(int index) throws Exception {
        if (index > this.filesPath.size()) {
            throw new Exception();
        }
        filesPath.remove(index);
    }
    
    public void deleteFile(String fileName) throws Exception {
        final var index = filesPath.indexOf(fileName);
        if (index == -1) {
            throw new Exception("file does not exist");
        } else {
            filesPath.remove(index);
        }
    }
    
    public boolean hasValue() {
        if (this.login.isEmpty()
                && this.pass.isEmpty()
                && this.userId == 0
            ) {
            return true;
        }
        return false;
    }
}
