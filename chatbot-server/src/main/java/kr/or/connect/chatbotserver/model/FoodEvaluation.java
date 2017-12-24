package kr.or.connect.chatbotserver.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="food_evaluations")
public class FoodEvaluation implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="food_evaluations_id")
    private int foodEvaluationId;

    @Column(name="score")
    private int score;

    @Column(name="cafeteria_menus_cafeteria_menus_id")
    private int menuId;

    @Column(name="cafeteria_menus_cafeteria_managements_cafeteria_managements_id")
    private int managementId;

    @Column(name="users_convertid")
    private String userId;

    public int getFoodEvaluationId() {
        return foodEvaluationId;
    }

    public void setFoodEvaluationId(int foodEvaluationId) {
        this.foodEvaluationId = foodEvaluationId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public int getManagementId() {
        return managementId;
    }

    public void setManagementId(int managementId) {
        this.managementId = managementId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
