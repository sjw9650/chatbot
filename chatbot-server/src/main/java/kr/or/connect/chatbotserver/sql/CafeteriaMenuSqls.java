package kr.or.connect.chatbotserver.sql;

<<<<<<< HEAD
import com.fasterxml.jackson.annotation.JsonIgnore;

public class CafeteriaMenuSqls {
    public static final String SELECT_ALL = "FROM CafeteriaMenu WHERE day like (:date)";
    public static final String DELETE_ALL = "delete from ";
=======
public class CafeteriaMenuSqls {
    public static final String SELECT_ALL = "SELECT * FROM cafeteria_menus WHERE day=?";
>>>>>>> 312813634d7dbd801cb5893f3d3f70dd21dc334b
}
