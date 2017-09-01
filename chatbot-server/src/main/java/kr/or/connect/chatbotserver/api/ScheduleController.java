package kr.or.connect.chatbotserver.api;

import java.util.List;

import kr.or.connect.chatbotserver.model.Schedule;
import kr.or.connect.chatbotserver.service.ScheduleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("user")
public class ScheduleController {

	@Autowired
	private ScheduleService scheduleService;
	
	@GetMapping("schedules")
	public ResponseEntity<List<Schedule>> getAllSchedules(){
		List<Schedule> scheduleList = scheduleService.getAllSchedules();
		return new ResponseEntity<List<Schedule>>(scheduleList, HttpStatus.OK);
	}
}
