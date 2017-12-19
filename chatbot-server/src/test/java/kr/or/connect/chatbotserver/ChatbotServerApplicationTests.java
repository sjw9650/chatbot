package kr.or.connect.chatbotserver;

import kr.or.connect.chatbotserver.dao.LostDAO;
import kr.or.connect.chatbotserver.model.CafeteriaManagement;
import kr.or.connect.chatbotserver.model.CafeteriaMenu;
import kr.or.connect.chatbotserver.service.CafeteriaMenuService;
import kr.or.connect.chatbotserver.service.LectureInformationService;
import kr.or.connect.chatbotserver.service.PhoneNumberOfUniversityService;
import kr.or.connect.chatbotserver.service.ScheduleService;
import org.json.simple.JSONObject;
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
	LostDAO lostDAO;

	@Autowired
	LectureInformationService lectureInformationService;
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

	@Test
	public void textError() throws IOException {
		JSONObject result = lectureInformationService.lectureInformationDepth("1bm523dju2",59,"12345");
		System.out.println( result.get("res"));
		System.out.println(result.get("depth"));
	}
 
}
