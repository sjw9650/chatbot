package kr.or.connect.chatbotserver.dao;


import kr.or.connect.chatbotserver.model.CafeteriaMenu;
import kr.or.connect.chatbotserver.model.Rank;
import kr.or.connect.chatbotserver.model.Vote;
import kr.or.connect.chatbotserver.sql.VoteSqls;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.*;

@Transactional
@Repository
public class VoteDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public List<Rank> getRankedData(){
        String hql = VoteSqls.SELECT_MENUS;
        String[] weekDay = {"일", "월", "화", "수", "목", "금", "토"};
        Calendar cal = Calendar.getInstance();
        int num = cal.get(Calendar.DAY_OF_WEEK)-1;
        String date = "%"+weekDay[num]+"%";
        return (List<Rank>) entityManager.createQuery(hql).setParameter("date",date).getResultList();
    }


}
