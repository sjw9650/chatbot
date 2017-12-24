package kr.or.connect.chatbotserver.sql;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CafeteriaMenuSqls {
    public static final String SELECT_ALL = "FROM CafeteriaMenu WHERE day like (:date)";
    public static final String DELETE_ALL = "delete from ";
    public static final String FIND_MENU = "FROM CafeteriaMenu where day like (:date) and menu = (:menu)";
}
