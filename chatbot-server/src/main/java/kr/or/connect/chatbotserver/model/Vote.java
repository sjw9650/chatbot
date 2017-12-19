package kr.or.connect.chatbotserver.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="food_evaluations")
public class Vote implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="food_evaluations_id")
    private int food_evaluations_id;

    @Column(name="score")
    private int score;
    @Column(name="cafeteria_menus_cafeteria_menus_id")
    private int cafeteria_menus_cafeteria_menus_id;
    @Column(name="cafeteria_menus_cafeteria_managements_cafeteria_managements_id")
    private int cafeteria_menus_cafeteria_managements_cafeteria_managements_id;

    @Column(name="users_convertid")
    private String uKey;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getFood_evaluations_id() {
        return food_evaluations_id;
    }

    public void setFood_evaluations_id(int food_evaluations_id) {
        this.food_evaluations_id = food_evaluations_id;
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

    public String getuKey() {
        return uKey;
    }

    public void setuKey(String uKey) {
        this.uKey = uKey;
    }
}
