package kr.or.connect.chatbotserver.sql;

public class PhoneNumberOfUniversitySqls {

    public static final String selectPhonNumber = "select * from number where (affiliation like ? OR assigned_task like ? or name like ?)and number;";
}
