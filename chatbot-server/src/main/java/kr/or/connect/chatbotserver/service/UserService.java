package kr.or.connect.chatbotserver.service;

import java.util.List;

import kr.or.connect.chatbotserver.dao.UserDAO;
import kr.or.connect.chatbotserver.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    public User getUserbykey(String user_key){
        System.out.println("debug4");
        return userDAO.getUserbyKey(user_key);
    }


    public synchronized boolean AddUser(User user){

        System.out.println("debug3");

        if (userDAO.UserExists(user.getUser_key(),user.getDepth())) {
            System.out.println("debug4");
            return false;
        } else {
            System.out.println("debug5");
            userDAO.addUser(user);
            return true;
        }
    }
}
