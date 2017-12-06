package kr.or.connect.chatbotserver.service;

import kr.or.connect.chatbotserver.dao.CafeteriaMenuDAO;
import kr.or.connect.chatbotserver.model.CafeteriaMenu;
<<<<<<< HEAD
import kr.or.connect.chatbotserver.model.Schedule;
import kr.or.connect.chatbotserver.sql.CafeteriaMenuSqls;
import kr.or.connect.chatbotserver.sql.ScheduleSqls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
=======
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

>>>>>>> 312813634d7dbd801cb5893f3d3f70dd21dc334b
import java.util.List;

@Service
public class CafeteriaMenuService {

    @Autowired
    private CafeteriaMenuDAO cafeteriaMenuDAO;

    public void insertCafeteriaMenu(CafeteriaMenu cafeteriaMenu) {
        cafeteriaMenuDAO.insertCafeteriaMenu(cafeteriaMenu);
    }
<<<<<<< HEAD

    public List<CafeteriaMenu> getAllCafeteriaMenu(){
        return cafeteriaMenuDAO.getAllCafeteriaMenu();
    }
    public void deleteALLSchedule(){
        cafeteriaMenuDAO.deleteALLSchedule();
=======
    public List<CafeteriaMenu> getAllCafeteriaMenu(CafeteriaMenu cafeteriaMenu){
        return cafeteriaMenuDAO.getAllCafeteriaMenu(cafeteriaMenu);
>>>>>>> 312813634d7dbd801cb5893f3d3f70dd21dc334b
    }

}
