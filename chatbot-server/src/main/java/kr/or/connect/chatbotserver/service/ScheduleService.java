package kr.or.connect.chatbotserver.service;

import java.util.List;

import kr.or.connect.chatbotserver.dao.ScheduleDAO;
import kr.or.connect.chatbotserver.model.Schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

	@Autowired
	private ScheduleDAO scheduleDAO;

	public List<Schedule> getAllSchedules(){
		return scheduleDAO.getAllSchedules();
	}
}
