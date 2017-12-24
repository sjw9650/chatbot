package kr.or.connect.chatbotserver.dao;

import kr.or.connect.chatbotserver.model.FoodEvaluation;
import kr.or.connect.chatbotserver.sql.FoodEvaluationSqls;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Transactional
@Repository
public class FoodEvaluationDAO {
    @PersistenceContext
    private EntityManager entityManager;

    public void addFoodEvaluation(FoodEvaluation foodEvaluation){
        entityManager.persist(foodEvaluation);
    }

    public void completeFoodEvaluation(FoodEvaluation foodEvaluation){
        entityManager.merge(foodEvaluation);
    }

    public void deleteFoodEvaluation(FoodEvaluation foodEvaluation){
        entityManager.remove(foodEvaluation);
    }


    public FoodEvaluation getFoodEvaluation(int id){
        return entityManager.find(FoodEvaluation.class,id);
    }

    public int FindFoodEvaluation(String userId){
        String hql = FoodEvaluationSqls.GETIDMAX;
        return (int) entityManager.createNativeQuery(hql).setParameter("userId",userId).getSingleResult();
    }
}
