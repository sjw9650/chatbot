package kr.or.connect.chatbotserver;
<<<<<<< HEAD

import kr.or.connect.chatbotserver.model.CafeteriaManagement;
=======
>>>>>>> 312813634d7dbd801cb5893f3d3f70dd21dc334b
import kr.or.connect.chatbotserver.model.CafeteriaMenu;
import kr.or.connect.chatbotserver.service.CafeteriaMenuService;
import kr.or.connect.chatbotserver.service.ScheduleService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
<<<<<<< HEAD
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
=======
import java.text.SimpleDateFormat;
import java.util.*;

>>>>>>> 312813634d7dbd801cb5893f3d3f70dd21dc334b

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChatbotServerApplicationTests {

	@Autowired
	ScheduleService scheduleService;
<<<<<<< HEAD

=======
>>>>>>> 312813634d7dbd801cb5893f3d3f70dd21dc334b
	@Autowired
	CafeteriaMenuService cafeteriaMenuService;
	@Test
	public void contextLoads() throws IOException {
<<<<<<< HEAD

        Calendar cal = new GregorianCalendar(Locale.KOREA);
        cal.setTime(new Date());
        SimpleDateFormat fm = new SimpleDateFormat("yyyyMMdd");
        System.out.println(cal);

=======
		String URL[] = new String[5];
		URL[0] = "http://www.inu.ac.kr/com/cop/mainWork/foodList1.do?siteId=inu&id=inu_050110010000&command=week";
		URL[1] = "http://www.inu.ac.kr/com/cop/mainWork/foodList2.do?siteId=inu&id=inu_050110030000&command=week";
		URL[2] = "http://www.inu.ac.kr/com/cop/mainWork/foodList3.do?siteId=inu&id=inu_050110040000&command=week";
		URL[3] = "http://www.inu.ac.kr/com/cop/mainWork/foodList4.do?siteId=inu&id=inu_050110050000&command=week";
		URL[4] = "http://www.inu.ac.kr/com/cop/mainWork/foodList5.do?siteId=inu&id=inu_050110060000&command=week";
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
			Elements corners = tbody.select("th");
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
				if(k-j*5<2) {
					temp = menus[k].split("\\/");
				}
				else{
					temp = menus[k].split("\\,");
				}
				for(int L=0;L<temp.length;L++) {
					menuex.setDay(krDays[j]);
					menuex.setCafeteria_managements_cafeteria_managements_id(k-j*5+1);
					menuex.setMenu(temp[L]);
					cafeteriaMenuService.insertCafeteriaMenu(menuex);
					menuex = new CafeteriaMenu();
				}
			}
		}

		//날짜 구하기
		Date d = new Date();
		SimpleDateFormat dataFormat = new SimpleDateFormat("dd");
		String date = dataFormat.format(d);

		//요일 구하기
		String day="";
		Calendar cal = Calendar.getInstance() ;
		cal.setTime(d);
		int dayNum = cal.get(Calendar.DAY_OF_WEEK) ;
		switch(dayNum){
			case 1:
				day = "일";
				break ;
			case 2:
				day = "월";
				break ;
			case 3:
				day = "화";
				break ;
			case 4:
				day = "수";
				break ;
			case 5:
				day = "목";
				break ;
			case 6:
				day = "금";
				break ;
			case 7:
				day = "토";
				break ;

		}
		String now = date+'('+day+')';
		menuex = new CafeteriaMenu();
		menuex.setDay(now);
		List<CafeteriaMenu> test;
		System.out.println(now);
		test = cafeteriaMenuService.getAllCafeteriaMenu(menuex);
		for(CafeteriaMenu a : test){
			System.out.println(a.getMenu());
		}
>>>>>>> 312813634d7dbd801cb5893f3d3f70dd21dc334b
	}

}
