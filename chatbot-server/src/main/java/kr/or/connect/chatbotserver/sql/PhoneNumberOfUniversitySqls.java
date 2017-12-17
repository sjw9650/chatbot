package kr.or.connect.chatbotserver.sql;

public class PhoneNumberOfUniversitySqls {

    public static final String selectPhonNumber = "From PhoneNumberOfUniversity where (affiliation like (:affiliation) OR assignedTask like (:assignedTask) or name like (:name)) AND  length(number) > 1";
}
