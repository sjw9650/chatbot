package kr.or.connect.chatbotserver.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="food_evaluations")
public class Vote implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="food_evaluation_id")
    private int food_evaluation_id;

    @Column(name="score")
    private int score;
    @Column(name="cafeteria_menus_cafeteria_menus_id")
    private int cafeteria_menus_cafeteria_menus_id;
    @Column(name="cafeteria_menus_cafeteria_managements_cafeteria_managements_id")
    private int cafeteria_menus_cafeteria_managements_cafeteria_managements_id;

    @Column(name="users_convertId")
    private String uKey;

    public String getuKey() {
        return uKey;
    }

    public void setuKey(String uKey) {
        this.uKey = uKey;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getFood_evaluation_id() {
        return food_evaluation_id;
    }

    public void setFood_evaluation_id(int food_evaluation_id) {
        this.food_evaluation_id = food_evaluation_id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getCafeteria_menus_cafeteria_menus_id() {
        return cafeteria_menus_cafeteria_menus_id;
    }

    public void setCafeteria_menus_cafeteria_menus_id(int cafeteria_menus_cafeteria_menus_id) {
        this.cafeteria_menus_cafeteria_menus_id = cafeteria_menus_cafeteria_menus_id;
    }

    public int getCafeteria_menus_cafeteria_managements_cafeteria_managements_id() {
        return cafeteria_menus_cafeteria_managements_cafeteria_managements_id;
    }

    public void setCafeteria_menus_cafeteria_managements_cafeteria_managements_id(int cafeteria_menus_cafeteria_managements_cafeteria_managements_id) {
        this.cafeteria_menus_cafeteria_managements_cafeteria_managements_id = cafeteria_menus_cafeteria_managements_cafeteria_managements_id;
    }


}
