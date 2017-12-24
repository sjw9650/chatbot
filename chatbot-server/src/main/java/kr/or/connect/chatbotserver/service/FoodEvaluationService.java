package kr.or.connect.chatbotserver.service;

import kr.or.connect.chatbotserver.dao.FoodEvaluationDAO;
import kr.or.connect.chatbotserver.model.CafeteriaMenu;
import kr.or.connect.chatbotserver.model.FoodEvaluation;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FoodEvaluationService {

    @Autowired
    private FoodEvaluationDAO foodEvaluationDAO;

    public String selectMenu(CafeteriaMenu cafeteriaMenu,String contents,String userid){

        FoodEvaluation foodEvaluation = new FoodEvaluation();
        foodEvaluation.setMenuId(cafeteriaMenu.getCafeteria_menus_id());
        foodEvaluation.setManagementId(cafeteriaMenu.getCafeteria_managements_cafeteria_managements_id());
        foodEvaluation.setUserId(userid);

        String text= "\"contents\"에 대해서 알려주세요! \n맛있어요? 추천할만한가요?\n별론가요? 추천하고싶지않아요?\n알려주세요!!(최고)\n";
        foodEvaluationDAO.addFoodEvaluation(foodEvaluation);
        return text;
    }
    public String Evaluating(String contents,String userId){
        FoodEvaluation foodEvaluation = foodEvaluationDAO.getFoodEvaluation(foodEvaluationDAO.FindFoodEvaluation(userId));
        String text="";
        if(contents.equals("맛있어요!")){
            foodEvaluation.setScore(1);
            text = "오!! 맛있었어요?!!!(웃음)\n" +
                    "다른 학우분들에게 맛있다구 추천할게요!! \n" +
                    "감사합니다.(최고)\n" +
                    "초기화면으로 돌아갑니다.";
        }else if(contents.equals("별로에요!")){
            foodEvaluation.setScore(0);
            text = "헐! 별로에요? (울음) 속상했겠네요.ㅠㅠ\n" +
                    "다른 학우분들에게 이 사실을 알릴게요!!\n" +
                    "감사합니다.(최고)\n" +
                    "초기화면으로 돌아갑니다.";
        }
        foodEvaluationDAO.completeFoodEvaluation(foodEvaluation);
        return text;

    }
    public void foodEvaluationCancel(String userId){
        foodEvaluationDAO.deleteFoodEvaluation(foodEvaluationDAO.getFoodEvaluation(foodEvaluationDAO.FindFoodEvaluation(userId)));
    }
}
