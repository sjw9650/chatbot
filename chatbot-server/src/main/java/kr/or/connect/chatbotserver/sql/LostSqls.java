package kr.or.connect.chatbotserver.sql;

public class LostSqls {
    public static final String GETIDMAX = "Select MAX(lost_things_id) FROM lost_things where users_user_key = ?";
}
