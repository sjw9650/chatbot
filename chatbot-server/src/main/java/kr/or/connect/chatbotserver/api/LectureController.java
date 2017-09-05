package kr.or.connect.chatbotserver.api;

import kr.or.connect.chatbotserver.model.User;
import kr.or.connect.chatbotserver.service.LostService;
import kr.or.connect.chatbotserver.service.ScheduleService;
import kr.or.connect.chatbotserver.service.UserService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


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

    public LectureController(String content, User user, UserService userService, LostService lostService) {
        this.content = content;
        this.user = user;
        this.userService = userService;
        this.lostService = lostService;
        jobjText = new JSONObject();
        jobjRes = new JSONObject();
    }


    public JSONObject eval_() throws IOException {
        int depth = user.getDepth();
        if (content.equals("취소")) {
            jobjText.put("text", "취소하였습니다");
        } else if (depth == 51) {
            if (content.equals("보기")) {
                jobjText.put("text", "보고싶어?");
            } else if (content.equals("평가")) {
                jobjText.put("text", "평가 하고싶어? 강의명을 입력해~");
                jobjRes.put("message", jobjText);
                user.setDepth(52);
                userService.setDepth(user);
            }
        } else if (depth == 40) {
            jobjText.put("text", content + "에 분실하셨구나(훌쩍)\n" +
                    content + "에 분실물의 발견 장소와 항목입니다.\n\n" +
                    "분실물 찾기를 취소 하시려면 \"취소\"를 입력해주세요~\n");
        }
        return jobjRes;

    }
}
