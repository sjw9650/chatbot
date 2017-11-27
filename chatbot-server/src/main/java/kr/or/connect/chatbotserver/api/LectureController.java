package kr.or.connect.chatbotserver.api;

import kr.or.connect.chatbotserver.model.User;
import kr.or.connect.chatbotserver.service.LostService;
import kr.or.connect.chatbotserver.service.UserService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;


public class LectureController {

    @Autowired
    kr.or.connect.chatbotserver.service.ScheduleService ScheduleService;

    @Autowired
    UserService userService;
    @Autowired
    LostService lostService;

    private String content;
    private User user;
    JSONObject jobjText;//보여주는 부분
    JSONObject jobjRes;//입력 받는 부분

    public LectureController() {
        jobjText = new JSONObject();
        jobjRes = new JSONObject();

    }

    public LectureController(User user) {
        this.user = user;
        jobjText = new JSONObject();
        jobjRes = new JSONObject();
    }


    public JSONObject eval_() throws IOException {

        if (content.equals("취소")) {
            jobjText.put("text", "취소하였습니다");
        } else if (user.getDepth() == 51) {
            if (content.equals("보기")) {
                jobjText.put("text", "보고싶어?");
            } else if (content.equals("평가")) {
                jobjText.put("text", "평가 하고싶어? 강의명을 입력해~~~");
                jobjRes.put("message", jobjText);
                user.setDepth(52);
                userService.setDepth(user);
            }
        }

        return jobjRes;

    }
}
