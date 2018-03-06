package kr.or.connect.chatbotserver.service;

import java.util.List;
import java.util.Random;

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
            
            String convertId = "";
            do{
            	convertId = generateRandomNum(10).toString();
            }while(userDAO.convertIdExists(convertId));
            
            user.setConvertId(convertId);
            userDAO.addUser(user);
            return true;
        }
    }
    public void setDepth(User user){
        userDAO.SetDepth(user);
    }
    
    //랜덤 난수 생성
    private StringBuffer generateRandomNum(int length){
    	Random rnd =new Random();
		StringBuffer sb =new StringBuffer();

		for(int i = 0; i < length; i++){
		    if(rnd.nextBoolean()){
		        sb.append((char)((int)(rnd.nextInt(26))+97));
		    }else{
		        sb.append((rnd.nextInt(10))); 
		    }
		}
		return sb;
    }
}
