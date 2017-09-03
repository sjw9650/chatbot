package kr.or.connect.chatbotserver.sql;

public class LostSqls {
    public static final String GETIDMAX = "Select MAX(id) FROM Lost where users_user_key = ?";
}
