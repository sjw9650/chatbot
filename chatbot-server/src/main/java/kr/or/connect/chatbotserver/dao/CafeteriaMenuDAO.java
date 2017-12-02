package kr.or.connect.chatbotserver.dao;


import kr.or.connect.chatbotserver.model.CafeteriaMenu;
import kr.or.connect.chatbotserver.model.Schedule;
import kr.or.connect.chatbotserver.sql.CafeteriaMenuSqls;
import kr.or.connect.chatbotserver.sql.LostSqls;
import kr.or.connect.chatbotserver.sql.ScheduleSqls;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Calendar;
import java.util.List;

@Transactional
@Repository
public class CafeteriaMenuDAO {
    @PersistenceContext
    private EntityManager entityManager;
    public void insertCafeteriaMenu(CafeteriaMenu cafeteriaMenu){ entityManager.persist(cafeteriaMenu);}

    @SuppressWarnings("unchecked")
    public List<CafeteriaMenu> getAllCafeteriaMenu(){
        String hql = CafeteriaMenuSqls.SELECT_ALL;
        String[] weekDay = {"일", "월", "화", "수", "목", "금", "토"};
        Calendar cal = Calendar.getInstance();
        int num = cal.get(Calendar.DAY_OF_WEEK)-1;
        String date = "%"+weekDay[num]+"%";
        return (List<CafeteriaMenu>) entityManager.createQuery(hql).setParameter("date",date).getResultList();
    }
    
    public void deleteALLSchedule(){
        String hql = CafeteriaMenuSqls.DELETE_ALL;
        entityManager.createNativeQuery("DELETE from cafeteria_menus").executeUpdate();
    }

}
