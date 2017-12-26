package kr.or.connect.chatbotserver.dao;

import kr.or.connect.chatbotserver.model.FoodEvaluation;
import kr.or.connect.chatbotserver.model.Rank;
import kr.or.connect.chatbotserver.sql.FoodEvaluationSqls;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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

    public Double getScore(int menuId){
        String hql = FoodEvaluationSqls.GETAVG;
        return (Double)(entityManager.createNativeQuery(hql).setParameter("menusId",menuId).getSingleResult());
    }
}
