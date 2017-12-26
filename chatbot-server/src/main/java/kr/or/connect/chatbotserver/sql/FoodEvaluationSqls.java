package kr.or.connect.chatbotserver.sql;

public class FoodEvaluationSqls {
    public static final String findEvaluation = "From food_evaluations_id where menuId = (:menuId)";
    public static final String GETIDMAX = "Select MAX(food_evaluations_id) FROM food_evaluations where users_convertid = (:userId)";

    public static final String GETAVG = "Select AVG(score) FROM food_evaluations where cafeteria_menus_cafeteria_menus_id = (:menusId)";


}
