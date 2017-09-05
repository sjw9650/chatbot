package kr.or.connect.chatbotserver.service;

import kr.or.connect.chatbotserver.dao.CafeteriaMenuDAO;
import kr.or.connect.chatbotserver.model.CafeteriaMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CafeteriaMenuService {

    @Autowired
    private CafeteriaMenuDAO cafeteriaMenuDAO;

    public void insertCafeteriaMenu(CafeteriaMenu cafeteriaMenu) {
        cafeteriaMenuDAO.insertCafeteriaMenu(cafeteriaMenu);
    }
    public List<CafeteriaMenu> getAllCafeteriaMenu(CafeteriaMenu cafeteriaMenu){
        return cafeteriaMenuDAO.getAllCafeteriaMenu(cafeteriaMenu);
    }

}
