package kr.or.connect.chatbotserver.sql;

public class VoteSqls {
    public static final String SELECT_ALL =  "select menu,sum(score) as score " +
            "from food_evaluations F, cafeteria_menus C " +
            "where F.cafeteria_menus_cafeteria_menus_id=C.cafeteria_menus_id " +
            "and C.day like (:date) " +
            "group by F.cafeteria_menus_cafeteria_menus_id " +
            "order by score desc";
}
