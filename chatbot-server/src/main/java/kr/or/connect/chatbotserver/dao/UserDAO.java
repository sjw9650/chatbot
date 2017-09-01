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

    @SuppressWarnings("unchecked")
    public User getUserbyKey(String user_key){
        return entityManager.find(User.class,user_key);
    }
    @SuppressWarnings("unchecked")
    public void addUser(User user){
        entityManager.persist(user);
    }

    @SuppressWarnings("unchecked")
    public boolean UserExists(String user_key, int depth) {
        //        String hql = UserSqls.UserExists;
        System.out.println(user_key);
        User user= entityManager.find(User.class,user_key);
        System.out.println("debug3");
        if (user.getUser_key().equals(""))
            return true;
        else return false;
    }

}
