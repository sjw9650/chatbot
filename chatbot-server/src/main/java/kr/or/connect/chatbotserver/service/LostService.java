package kr.or.connect.chatbotserver.service;

import kr.or.connect.chatbotserver.dao.LostDAO;
import kr.or.connect.chatbotserver.model.Lost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LostService {

    @Autowired
    private LostDAO lostDAO;
    //User_key를 이용하여 User 객체를 탐색

    public void addLost(String user_key,String thing){
        Lost lost = new Lost();
        lost.setContent(thing);
        lost.setUser_key(user_key);
        lostDAO.addLost(lost);
    }
    public void setLost(String user_key,int index,String content){
        int id = lostDAO.getId(user_key);
        Lost lost= new Lost();
        lost = lostDAO.getLost(id);
        if(index == 1){
            lost.setGet_place(content);
        }else if(index == 2){
            lost.setDate_(content);
        }
        else if(index ==3 ){
            lost.setPut_place(content);
        }
        else if(index ==4 ){
            lost.setCompleted(1);
            lost.setPicture(content);
        }
        lostDAO.setLost(lost);
    }
    public String getContent(String user_key){
        int id = lostDAO.getId(user_key);
        Lost lost= new Lost();
        lost = lostDAO.getLost(id);
        return lost.getContent();
    }
    public int getId(String user_key){
        int id = lostDAO.getId(user_key);
        return id;
    }

    public void removeLost(String user_key){
        int id = lostDAO.getId(user_key);
        Lost lost= new Lost();
        lost = lostDAO.getLost(id);
        lostDAO.removeLost(lost);
    }
}
