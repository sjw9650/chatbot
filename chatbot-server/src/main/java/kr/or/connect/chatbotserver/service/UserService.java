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

    //User_key를 이용하여 User 객체를 탐색
    public User getUserbykey(String user_key){
        return userDAO.getUserbyKey(user_key);
    }

    //user_key를 이용하여 database에 insert
    public synchronized boolean AddUser(String user_key){

        if (userDAO.UserExists(user_key)) {
            // 아이디가 존재하면
            return false;
        } else {
            // 존재하지않으면
            User user = new User();
            user.setDepth(0);
            user.setUser_key(user_key);
            userDAO.addUser(user);
            return true;
        }
    }
    public void setDepth(User user){
        userDAO.SetDepth(user);
    }
}
