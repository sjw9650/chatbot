package kr.or.connect.chatbotserver;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import kr.or.connect.chatbotserver.dao.CafeteriaMenuDAO;
import kr.or.connect.chatbotserver.model.CafeteriaManagement;
import kr.or.connect.chatbotserver.model.CafeteriaMenu;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainHandler implements RequestStreamHandler {

    ApplicationContext ac;

    public MainHandler(){
        ac = new ChatbotServerApplication().getApplicationContext();
    }
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context)
        throws IOException {
        CafeteriaMenuDAO cafeteriaMenuDAO = ac.getBean(CafeteriaMenuDAO.class);
        cafeteriaMenuDAO.deleteALLSchedule();

        String URL[] = new String[5];
        URL[0] = "http://www.inu.ac.kr/com/cop/mainWork/foodList1.do?siteId=inu&id=inu_050110010000&command=week";
        URL[1] = "http://www.inu.ac.kr/com/cop/mainWork/foodList2.do?siteId=inu&id=inu_050110030000&command=week";
        URL[2] = "http://www.inu.ac.kr/com/cop/mainWork/foodList3.do?siteId=inu&id=inu_050110040000&command=week";
        URL[3] = "http://www.inu.ac.kr/com/cop/mainWork/foodList4.do?siteId=inu&id=inu_050110050000&command=week";
        URL[4] = "http://www.inu.ac.kr/com/cop/mainWork/foodList5.do?siteId=inu&id=inu_050110060000&command=week";
        CafeteriaManagement manageex = new CafeteriaManagement();
        manageex.setPlace("학생1식당");
        Document doc = Jsoup.connect(URL[0]).get();
        Elements links = doc.select("table");
        Elements days = links.select("thead");
        Elements tbodys = links.select("tbody");
        String krDays[] = new String[7];
        String menus[] = new String[35];
        int i = 0;
        for(Element day:days) {
            krDays[i] = day.select("th").text();
            i++;
        }
        i = 0;
        for(Element tbody:tbodys) {
            Elements menudata = tbody.select("td");
            for(Element data:menudata){
                if(!data.text().isEmpty()) {
                    menus[i] = data.text();
                    i++;
                }
            }
        }
        CafeteriaMenu menuex = new CafeteriaMenu();
        for(int j=0;j<7;j++){
            for(int k=j*5;k<j*5+5;k++){
                String temp[];
                if(k-j*5<3) {
                    temp = menus[k].split("\\/");
                }
                else{
                    temp = menus[k].split("\\,");
                }
                for(int L=0;L<temp.length;L++) {
                    menuex.setDay(krDays[j]);
                    menuex.setCafeteria_managements_cafeteria_managements_id(k-j*5+1);
                    menuex.setMenu(temp[L].trim());
                    cafeteriaMenuDAO.insertCafeteriaMenu(menuex);
                    menuex = new CafeteriaMenu();
                }
            }
        }
    }
}
