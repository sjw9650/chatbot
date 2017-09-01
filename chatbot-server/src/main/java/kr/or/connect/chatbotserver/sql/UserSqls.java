package kr.or.connect.chatbotserver.sql;

public class UserSqls {
    public static final String SELECT_DEPTH = "FROM User Where user_key = ";
    public static final String UserExists = "FROM User as user WHERE user.user_key = ?";
}
