package kr.or.connect.chatbotserver.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import kr.or.connect.chatbotserver.model.Schedule;
import kr.or.connect.chatbotserver.sql.ScheduleSqls;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class ScheduleDAO {
	@PersistenceContext	
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public List<Schedule> getAllSchedules(){
		String hql = ScheduleSqls.SELECT_ALL;
		return (List<Schedule>) entityManager.createQuery(hql).getResultList();
	}
}
