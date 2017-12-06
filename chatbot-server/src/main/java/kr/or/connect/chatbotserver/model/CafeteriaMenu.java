package kr.or.connect.chatbotserver.model;

<<<<<<< HEAD
import com.sun.javafx.beans.IDProperty;

=======
>>>>>>> 312813634d7dbd801cb5893f3d3f70dd21dc334b
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="cafeteria_menus")
public class CafeteriaMenu implements Serializable{
    private static final long serialVersionUID = 1L;

<<<<<<< HEAD

=======
>>>>>>> 312813634d7dbd801cb5893f3d3f70dd21dc334b
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

<<<<<<< HEAD
    public String getDay() {
        return day;
    }

    public String getMenu() {
        return menu;
    }

    public int getCafeteria_managements_cafeteria_managements_id() {
        return cafeteria_managements_cafeteria_managements_id;
    }

=======
>>>>>>> 312813634d7dbd801cb5893f3d3f70dd21dc334b
    public void setCafeteria_menus_id(int cafeteria_menus_id) {
        this.cafeteria_menus_id = cafeteria_menus_id;
    }

<<<<<<< HEAD
=======
    public String getDay() {
        return day;
    }

>>>>>>> 312813634d7dbd801cb5893f3d3f70dd21dc334b
    public void setDay(String day) {
        this.day = day;
    }

<<<<<<< HEAD
=======
    public String getMenu() {
        return menu;
    }

>>>>>>> 312813634d7dbd801cb5893f3d3f70dd21dc334b
    public void setMenu(String menu) {
        this.menu = menu;
    }

<<<<<<< HEAD
=======
    public int getCafeteria_managements_cafeteria_managements_id() {
        return cafeteria_managements_cafeteria_managements_id;
    }

>>>>>>> 312813634d7dbd801cb5893f3d3f70dd21dc334b
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
