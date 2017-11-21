package kr.or.connect.chatbotserver.sql;

public class LostSqls {
    public static final String GETIDMAX = "Select MAX(lost_things_id) FROM lost_things where users_convertid = ?";
    public static final String SELECT_LOST = "Select * FROM lost_things where date like (:date)";
    public static final String SELECT_ALL = "FROM Schedule WHERE userKey = (:userKeys)";
}
