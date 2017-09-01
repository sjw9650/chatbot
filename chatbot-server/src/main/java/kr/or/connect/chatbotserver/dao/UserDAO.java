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

        String hql = "FROM User as user WHERE user.user_key = ? and user.depth = ?";

        int count = entityManager.createQuery(hql).setParameter(1, user_key)
                .setParameter(2, depth).getResultList().size();

        System.out.println("debug3");
        return count > 0 ? true : false;
    }

}
