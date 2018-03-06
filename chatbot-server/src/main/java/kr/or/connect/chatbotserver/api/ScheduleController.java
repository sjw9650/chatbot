package kr.or.connect.chatbotserver.api;

import java.util.List;

import javax.servlet.http.HttpSession;

import kr.or.connect.chatbotserver.model.Schedule;
import kr.or.connect.chatbotserver.service.ScheduleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("user/schedules")
public class ScheduleController {

	@Autowired
	private ScheduleService scheduleService;

	@RequestMapping(value = "/start/{user_key}", produces = "text/html", method = RequestMethod.GET)
	public String startSchedule(HttpSession session, @PathVariable ("user_key") String user_key){
		String session_user_key = (String) session.getAttribute("userKey");
		if(session_user_key == null || session_user_key.equals("")){
			session.setAttribute("userKey", user_key);
			session_user_key = (String) session.getAttribute("userKey");
		}
		return "redirect:/";
	}
	
	@GetMapping
	public ResponseEntity<List<Schedule>> getAllSchedules(HttpSession session){
			String session_user_key = (String) session.getAttribute("userKey");
			if(session_user_key == null || session_user_key.equals("")){
				// 이후에 처리
			}
			Schedule schedule = new Schedule();
		schedule.setUserKey(session_user_key);
		List<Schedule> scheduleList = scheduleService.getAllSchedules(schedule);
		return new ResponseEntity<List<Schedule>>(scheduleList, HttpStatus.OK);
	}
	@PostMapping
	public ResponseEntity<Schedule> insertSchedule(HttpSession session, @RequestBody Schedule schedule){
		String user_key = (String) session.getAttribute("userKey");
		schedule.setUserKey(user_key);
		scheduleService.insertSchedule(schedule);
		return new ResponseEntity<Schedule>(schedule, HttpStatus.CREATED);
	}
	@DeleteMapping
	public ResponseEntity<Boolean> deleteSchedule(HttpSession session, @RequestBody Schedule schedule){
		String user_key = (String) session.getAttribute("userKey");
		schedule.setUserKey(user_key);
		scheduleService.deleteSchedule(schedule);
		return new ResponseEntity<Boolean>(true, HttpStatus.NO_CONTENT);
	}
}
