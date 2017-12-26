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

        List<String> menuList = (List<String>) entityManager.createNativeQuery(hql).setParameter("date",date).getResultList();
        String hql2 = VoteSqls.SELECT_AVG;

        int length = menuList.size();
        List<Rank> resultRank = new ArrayList<Rank>();

        for(int i =0;i<length;i++){
            Rank rank = new Rank();
            String menu = menuList.get(i);
            rank.setMenu(menu);
            Double score = (Double) entityManager.createNativeQuery(hql2).setParameter("date",date).setParameter("menu",menu).getSingleResult();
            rank.setScore(score);

            System.out.println(rank.getMenu()+ " "+rank.getScore());
            resultRank.add(rank);
        }

        return resultRank;

    }


}
