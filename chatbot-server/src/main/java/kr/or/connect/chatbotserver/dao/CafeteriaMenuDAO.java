package kr.or.connect.chatbotserver.dao;


import kr.or.connect.chatbotserver.model.CafeteriaMenu;
<<<<<<< HEAD
import kr.or.connect.chatbotserver.model.Schedule;
import kr.or.connect.chatbotserver.sql.CafeteriaMenuSqls;
import kr.or.connect.chatbotserver.sql.LostSqls;
import kr.or.connect.chatbotserver.sql.ScheduleSqls;
=======
import kr.or.connect.chatbotserver.sql.CafeteriaMenuSqls;
import org.springframework.data.jpa.repository.JpaRepository;
>>>>>>> 312813634d7dbd801cb5893f3d3f70dd21dc334b
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
<<<<<<< HEAD
import java.util.Calendar;
=======
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
>>>>>>> 312813634d7dbd801cb5893f3d3f70dd21dc334b
import java.util.List;

@Transactional
@Repository
public class CafeteriaMenuDAO {
    @PersistenceContext
    private EntityManager entityManager;
    public void insertCafeteriaMenu(CafeteriaMenu cafeteriaMenu){ entityManager.persist(cafeteriaMenu);}

<<<<<<< HEAD
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

=======

    public List<CafeteriaMenu> getAllCafeteriaMenu(CafeteriaMenu cafeteriaMenu){
        String hql = CafeteriaMenuSqls.SELECT_ALL;
        return (List<CafeteriaMenu>) entityManager.createNativeQuery(hql).setParameter(1,cafeteriaMenu.getDay()).getResultList();
        //return (List<CafeteriaMenu>) entityManager.createQuery(hql).setParameter("day",cafeteriaMenu.getDay()).getResultList();
    }
>>>>>>> 312813634d7dbd801cb5893f3d3f70dd21dc334b
}
