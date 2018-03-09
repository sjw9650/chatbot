package kr.or.connect.chatbotserver.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import antlr.debug.DebuggingInputBuffer;
import kr.or.connect.chatbotserver.model.Lost;
import kr.or.connect.chatbotserver.model.User;
import kr.or.connect.chatbotserver.sql.LostSqls;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class LostDAO {
    @PersistenceContext
    private EntityManager entityManager;


    @SuppressWarnings("unchecked")
    public List<Lost> getAllLost(){
        String hql ="";
        return (List<Lost>) entityManager.createQuery(hql).getResultList();
    }
//ㅅㄷ
    public int getId(String user_key){
        String hql = LostSqls.GETIDMAX;

        return (int) entityManager.createNativeQuery(hql).setParameter(1,user_key).getSingleResult();
    }

    public Lost getLost(int id){
        return entityManager.find(Lost.class,id);
    }

    public void addLost(Lost lost){
        entityManager.persist(lost);
    }

    public void setLost(Lost lost){
        entityManager.merge(lost);
    }

    public void removeLost(Lost lost){
        entityManager.remove(lost);
    }

    @SuppressWarnings("unchecked")
    public List<Lost> seek_lostofday(Lost lost){
        String hql = LostSqls.SELECT_LOST;
        String lostDate = lost.getDate_()+"%";
        List<Lost> result = entityManager.createQuery(hql).setParameter("date",lostDate).getResultList();
        return result;
    }

}
