package kr.or.connect.chatbotserver.model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="cafeteria_menus")
public class CafeteriaMenu implements Serializable{
    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="cafeteria_menus_id")
    private int cafeteria_menus_id;

    @Column(name="day")
    private String day;

    @Column(name="menu")
    private String menu;

    @Column(name="cafeteria_managements_cafeteria_managements_id")
    private int cafeteria_managements_cafeteria_managements_id;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getCafeteria_menus_id() {
        return cafeteria_menus_id;
    }

    public String getDay() {
        return day;
    }

    public String getMenu() {
        return menu;
    }

    public int getCafeteria_managements_cafeteria_managements_id() {
        return cafeteria_managements_cafeteria_managements_id;
    }

    public void setCafeteria_menus_id(int cafeteria_menus_id) {
        this.cafeteria_menus_id = cafeteria_menus_id;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public void setCafeteria_managements_cafeteria_managements_id(int cafeteria_managements_cafeteria_managements_id) {
        this.cafeteria_managements_cafeteria_managements_id = cafeteria_managements_cafeteria_managements_id;
    }

    @Override
    public String toString() {
        return "CafeteriaMenu{" +
                "cafeteria_menus_id=" + cafeteria_menus_id +
                ", day='" + day + '\'' +
                ", menu='" + menu + '\'' +
                ", cafeteria_managements_cafeteria_managements_id=" + cafeteria_managements_cafeteria_managements_id +
                '}';
    }
}
