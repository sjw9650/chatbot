package kr.or.connect.chatbotserver.sql;

public class LectureInformationSqls {

    public static final String SELECT_LECTURE ="FROM LectureInformation Where ( lecture like (:lecture) OR professor like (:professor) ) AND  completed = 1";
    public static final String GETIDMAX = "Select MAX(lecture_information_id) FROM lecture_information where users_convertid = ?";
}
