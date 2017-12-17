package kr.or.connect.chatbotserver;

import kr.or.connect.chatbotserver.service.CafeteriaMenuService;
import kr.or.connect.chatbotserver.service.PhoneNumberOfUniversityService;
import kr.or.connect.chatbotserver.service.ScheduleService;
<<<<<<< HEAD
=======
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
>>>>>>> e1d6e35f2dc5bda391c19dd888e664724d070a26
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

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

	}

	@Test
	public void testNumber(){
		JSONObject result = phoneNumberOfUniversityService.infomPhoneNumber("컴퓨터");
		System.out.println( result.get("res").toString());
		System.out.println( result.get("depth").toString());
	}
 
}
