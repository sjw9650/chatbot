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

    @Column(name="socre")
    private int score;

    @Column(name="cafeteria_menus_cafeteria_menus_id")
    private int menuId;

    @Column(name="cafeteria_menus_cafeteria_managements_cafeteria_managements_id")
    private int managementId;

    @Column(name="users_convertid")
    private int userId;
}
