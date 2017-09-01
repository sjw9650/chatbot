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
        return userDAO.getUserbyKey(user_key);
    }

    public synchronized boolean AddUser(User user){
        if (userDAO.UserExists(user.getUser_key(),user.getDepth())) {
            return false;
        } else {
            userDAO.addUser(user);
            return true;
        }
    }
}
