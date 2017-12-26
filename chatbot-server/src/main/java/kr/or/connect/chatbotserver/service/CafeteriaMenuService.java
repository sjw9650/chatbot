package kr.or.connect.chatbotserver.service;

import kr.or.connect.chatbotserver.dao.CafeteriaMenuDAO;
import kr.or.connect.chatbotserver.model.CafeteriaMenu;
import kr.or.connect.chatbotserver.model.Schedule;
import kr.or.connect.chatbotserver.sql.CafeteriaMenuSqls;
import kr.or.connect.chatbotserver.sql.ScheduleSqls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class CafeteriaMenuService {

    @Autowired
    private CafeteriaMenuDAO cafeteriaMenuDAO;
    public List<CafeteriaMenu> getAllCafeteriaMenu(){
        return cafeteriaMenuDAO.getAllCafeteriaMenu();
    }

    public CafeteriaMenu FindMenu(String menu){
        return cafeteriaMenuDAO.FindMenu(menu);
    }

}
