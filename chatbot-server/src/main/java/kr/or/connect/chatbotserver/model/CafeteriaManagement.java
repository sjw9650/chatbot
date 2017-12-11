package kr.or.connect.chatbotserver.model;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name="cafeteria_managements")
public class CafeteriaManagement implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="cafeteria_managements_id")
    private int cafeteria_managements_id;
    @Column(name="place")
    private String place;
    @Column(name="corner")
    private String corner;
    @Column(name="spare")
    private String spare;


    public int getCafeteria_managements_id() {
        return cafeteria_managements_id;
    }

    public void setCafeteria_managements_id(int cafeteria_managements_id) {
        this.cafeteria_managements_id = cafeteria_managements_id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getCorner() {
        return corner;
    }

    public void setCorner(String corner) {
        this.corner = corner;
    }

    public String getSpare() {
        return spare;
    }

    public void setSpare(String spare) {
        this.spare = spare;
    }

    @Override
    public String toString() {
        return "CafeteriaMenu{" +
                "cafeteria_managements_id=" + cafeteria_managements_id +
                ", place='" + place + '\'' +
                ", corner='" + corner + '\'' +
                ", spare='" + spare + '\'' +
                '}';
    }
}
