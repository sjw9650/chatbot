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

        String text= "\""+contents+"\"에 대해서 알려주세요! \n맛으면 별 다섯개!!\n그냥 그러면~ 별 세개!!\n별~로~면 별 한개!!\n\n알려주세요!!(최고)\n";
        foodEvaluationDAO.addFoodEvaluation(foodEvaluation);
        return text;
    }
    public String Evaluating(String contents,String userId){
        FoodEvaluation foodEvaluation = foodEvaluationDAO.getFoodEvaluation(foodEvaluationDAO.FindFoodEvaluation(userId));
        String text="";
        int lenghtScore = contents.length();
        foodEvaluation.setScore(lenghtScore);

        if(lenghtScore == 5){
            text = "오!! 맛있었어요?!!!(하하)\n" +
                    "다른 학우분들에게 맛있다구 추천할게요!! \n" +
                    "감사합니다.(최고)\n 다음에도 맛있는 거 먹어요!!" +
                    "초기화면으로 돌아갑니다.";
        }else if(lenghtScore == 3){
            text = "그냥~~ 그래요???(민망)\n" +
                    "다른 학우분들에게 그~냥~ 그런거 같아라구 알려줄게요!\n" +
                    "감사합니다.(최고) 다음엔 더 맛있는 걸로 먹어요!!\n" +
                    "초기화면으로 돌아갑니다.";
        }else if(lenghtScore == 1){
            text = "헐?! 별로인가요? ㅠㅠ(훌쩍)\n" +
                    "다른 학우분들에게 별로라구 알려줄게요 (속닥속닥)\n" +
                    "감사합니다.(최고) 다음에는 꼭 맛있는 거 먹어요!!\n" +
                    "초기화면으로 돌아갑니다.";
        }


        foodEvaluationDAO.completeFoodEvaluation(foodEvaluation);
        return text;

    }
    public void foodEvaluationCancel(String userId){
        foodEvaluationDAO.deleteFoodEvaluation(foodEvaluationDAO.getFoodEvaluation(foodEvaluationDAO.FindFoodEvaluation(userId)));
    }
}
