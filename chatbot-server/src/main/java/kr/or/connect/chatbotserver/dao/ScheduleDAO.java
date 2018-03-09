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
	public List<Schedule> getAllSchedules(Schedule schedule){
		String hql = ScheduleSqls.SELECT_ALL;
		return (List<Schedule>) entityManager.createQuery(hql).setParameter("userKeys", schedule.getUserKey()).getResultList();
	}

	public void insertSchedule(Schedule schedule){
		entityManager.persist(schedule);
	}
	
	public void deleteSchedule(Schedule schedule){
		//#remove는 현재 transaction/context에 존재하는 entity만 작동한다. 지난 transaction의 것을 돌려받은 뒤 최신 http session에 저장한다.
		entityManager.remove(entityManager.contains(schedule) ? schedule : entityManager.merge(schedule));
	}
}
