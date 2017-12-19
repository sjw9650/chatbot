package kr.or.connect.chatbotserver.dao;

import kr.or.connect.chatbotserver.model.LectureInformation;
import kr.or.connect.chatbotserver.sql.LectureInformationSqls;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Transactional
@Repository
public class LectureInformationDAO {
    @PersistenceContext
    private EntityManager entityManager;

    public void setLectureInformation(LectureInformation lectureInformation){
        entityManager.merge(lectureInformation);
    }

    public void addLectureInformation(LectureInformation lectureInformation){
        entityManager.persist(lectureInformation);
    }
    public LectureInformation getLectureInformation(int id){
        return entityManager.find(LectureInformation.class,id);
    }
    public int getId(String user_key){
        String hql = LectureInformationSqls.GETIDMAX;
        return (int) entityManager.createNativeQuery(hql).setParameter(1,user_key).getSingleResult();
    }

    @SuppressWarnings("unchecked")
    public List<LectureInformation> seekLectureInformation(String contents){

        String hql = LectureInformationSqls.SELECT_LECTURE;
        String LectureInfo = "%"+contents+"%";
        List<LectureInformation> result = entityManager.createQuery(hql).setParameter("lecture",LectureInfo).setParameter("professor",LectureInfo).getResultList();
        return result;
    }


    public void removeLost(LectureInformation lectureInformation){
        entityManager.remove(lectureInformation);
    }

}
