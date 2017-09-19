package kr.or.connect.chatbotserver.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import kr.or.connect.chatbotserver.model.User;
import kr.or.connect.chatbotserver.sql.UserSqls;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class UserDAO {
    @PersistenceContext
    private EntityManager entityManager;

    // user_key의 값으로 User 객체를 탐색
    public User getUserbyKey(String user_key){
        return entityManager.find(User.class, user_key);
    }

    //User를 insert함
    public void addUser(User user){
        entityManager.persist(user);
    }

    //user_key를 이용하여 이미 등록되어 있는 지 확인
    public boolean UserExists(String user_key) {

        User user= entityManager.find(User.class, user_key);
        if (user==null)
            return false;
        else return true;
    }


    //convertId를 이용하여 이미 등록되어 있는 지 확인
    public boolean convertIdExists(String convertId) {

        User user= entityManager.find(User.class, convertId);
        if (user==null)
            return false;
        else return true;
    }
    public void SetDepth(User user){
            entityManager.merge(user);
    }

}
