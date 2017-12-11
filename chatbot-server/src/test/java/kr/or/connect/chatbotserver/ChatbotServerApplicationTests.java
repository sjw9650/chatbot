package kr.or.connect.chatbotserver;

import kr.or.connect.chatbotserver.model.CafeteriaManagement;
import kr.or.connect.chatbotserver.model.CafeteriaMenu;
import kr.or.connect.chatbotserver.service.CafeteriaMenuService;
import kr.or.connect.chatbotserver.service.PhoneNumberOfUniversityService;
import kr.or.connect.chatbotserver.service.ScheduleService;
import org.json.simple.JSONObject;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChatbotServerApplicationTests {

	@Autowired
	ScheduleService scheduleService;

	@Autowired
	CafeteriaMenuService cafeteriaMenuService;

	@Autowired
	PhoneNumberOfUniversityService phoneNumberOfUniversityService;
	@Test
	public void contextLoads() throws IOException {

        Calendar cal = new GregorianCalendar(Locale.KOREA);
        cal.setTime(new Date());
        SimpleDateFormat fm = new SimpleDateFormat("yyyyMMdd");
        System.out.println(cal);

	}

	@Test
	public void testNumber(){
		JSONObject result = phoneNumberOfUniversityService.infomPhoneNumber("컴퓨터");
		System.out.println( result.get("res").toString());
		System.out.println( result.get("depth").toString());
	}
 
}
