package kr.or.connect.chatbotserver.dao;


import kr.or.connect.chatbotserver.model.CafeteriaMenu;
import kr.or.connect.chatbotserver.sql.CafeteriaMenuSqls;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Transactional
@Repository
public class CafeteriaMenuDAO {
    @PersistenceContext
    private EntityManager entityManager;
    public void insertCafeteriaMenu(CafeteriaMenu cafeteriaMenu){ entityManager.persist(cafeteriaMenu);}


    public List<CafeteriaMenu> getAllCafeteriaMenu(CafeteriaMenu cafeteriaMenu){
        String hql = CafeteriaMenuSqls.SELECT_ALL;
        return (List<CafeteriaMenu>) entityManager.createNativeQuery(hql).setParameter(1,cafeteriaMenu.getDay()).getResultList();
        //return (List<CafeteriaMenu>) entityManager.createQuery(hql).setParameter("day",cafeteriaMenu.getDay()).getResultList();
    }
}
