package kr.or.connect.chatbotserver.service;

import kr.or.connect.chatbotserver.dao.LectureInformationDAO;
import kr.or.connect.chatbotserver.model.LectureInformation;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class LectureInformationService {
    @Autowired
    private LectureInformationDAO lectureInformationDAO;


    public JSONObject lectureInformationDepth(String user_key, int depth, String content) throws IOException {

        JSONObject response = new JSONObject();
        JSONObject jobjText= new JSONObject();
        JSONObject jobjRes = new JSONObject();
        String text= "";
        int tempDepth =depth;

        if(depth==51){
            if(content.equals("등록")) {
                text="강의정보입력을 등록하시려구요?!(최고)\n" +
                        "다른 학우에게 도움이 되어주셔서 감사합니다.\n\n" +
                        "강의명이 어떻게 되나요?(궁금)(궁금)\n"+
                        "취소하시려면 \"취소\" 를 입력해주세요.";

                tempDepth=52;
            }
            else if(content.equals("검색")){
                text = "원하는 강의 정보에 키워드를 입력해주세요.(찡긋)\n\n" +
                        "예시) 테니스 강좌에 대한 후기가 궁금하시면 \"테니스\"를 입력해주세요.\n\n"+
                        "취소하시려면 \"취소\" 를 입력해주세요.";

                tempDepth=57;
            }
        }
        else if(depth==52){
            LectureInformation lectureInformation = new LectureInformation();
            lectureInformation.setUserId(user_key);
            lectureInformation.setCompleted(0);
            lectureInformation.setLecture(content);
            lectureInformationDAO.addLectureInformation(lectureInformation);

            text = content+"를(을) 수강하셨군요(놀람)\n" +
                    "교수님의 성함을 입력해주시겠어요??\n\n" +
                    "취소하시려면 \"취소\" 를 입력해주세요.";

            tempDepth=53;

        }else if(depth==53){
            LectureInformation lectureInformation = getLectureInformation(user_key);
            lectureInformation.setProfessor(content);
            lectureInformationDAO.setLectureInformation(lectureInformation);
            text= content+"교수님의 "+ lectureInformation.getLecture()+"수업을 들으셨군요!!\n\n" +
                    "이 강의 어떠셨어요?? 추천하는 정도에 따라 별점을 주세요!!\n" +
                    "추천하지 않는다 : ★\n" +
                    "보통이다. : ★★★\n" +
                    "추천한다. : ★★★★★\n" +
                    "취소하시려면 \"취소\" 를 입력해주세요.";

            JSONObject josonKeyboard = new JSONObject();

            josonKeyboard.put("type", "buttons");
            ArrayList<String> btns = new ArrayList<>();
            btns.add("★");
            btns.add("★★★");
            btns.add("★★★★★");
            btns.add("취소");
            josonKeyboard.put("buttons",btns);
            jobjRes.put("keyboard",josonKeyboard);

            tempDepth=54;
        }else if(depth==54){
            LectureInformation lectureInformation = getLectureInformation(user_key);
            lectureInformation.setStars(content.length());
            lectureInformationDAO.setLectureInformation(lectureInformation);

            text = lectureInformation.getProfessor()+"교수님의 "+ lectureInformation.getLecture()+"의\n별점은 : "+ content+"이군요!!\n\n" +
                    "이제 마지막으로 강의에 대한 간단한 후기를 알려주세요. \n\n" + "예시) 이 수업은 기말고사의 범위가 너무 넓어서 힘들었어요ㅠㅠ\n\n"+
                    "취소하시려면 \"취소\" 를 입력해주세요.";

            tempDepth=55;
        }else if(depth==55){

            LectureInformation lectureInformation = getLectureInformation(user_key);
            lectureInformation.setReview(content);
            lectureInformation.setCompleted(1);
            lectureInformationDAO.setLectureInformation(lectureInformation);

            text= "마지막 후기까지 정말 감사합니다.(하트)\n" +
                    "당신같은 사람이 있어서 학교가 발전한다고 생각해요!!\n" +
                    "학우들에게 소중한 정보가 될것입니다.\n\n" +
                    "모든 등록을 마치고 초기 메뉴로 이동합니다!";

            tempDepth=0;
        }else if(depth==57) {
            List<LectureInformation> listLectureInformation = lectureInformationDAO.seekLectureInformation(content);
            int listSize = listLectureInformation.size();
            if (listSize == 0) {
                text=content + "에 해당하는 강의 정보목록 없습니다.(눈물)\n" +
                        "다른 키워드를 입력해주시겠어요??\n\n" +
                        "취소하시려면 \"취소\" 를 입력해주세요.";
            } else {
                text = content + "에 해당하는 강의 정보목록은 총 " + listSize + "건 입니다.\n";
                for (int i = 0; i < listSize; i++) {
                    text += listLectureInformation.get(i).getUserId() + "번" + listLectureInformation.get(i).getProfessor() + "교수님의" + listLectureInformation.get(i).getLectureInformationId() + "\n\n";
                }
                text += "\n 보고 싶은 후기의 번호를 입력해주세요. ex) 1" +
                        "취소하시려면 \"취소\" 를 입력해주세요.";

                tempDepth = 59;
            }
        }else if(depth==58){

        }else if(depth==59){
            tempDepth=0;
            if(!isStringInt(content)){
                text= "잘못된 정보를 입력하였습니다.(놀람) \n 초기 메뉴로 이동합니다.\n";

            }else {
                LectureInformation lectureInformation = lectureInformationDAO.getLectureInformation(Integer.parseInt(content));
                if(lectureInformation==null){
                    text= "잘못된 정보를 입력하였습니다.(놀람) \n 초기 메뉴로 이동합니다.\n";

                }
                else{
                    text = "선택하신 정보인"+lectureInformation.getProfessor()+"교수님의 강의 "+lectureInformation.getLecture()+"강의의 별점은"+lectureInformation.getStars()+"점 입니다.\n" +
                            "후기 내용은 다음과 같습니다.\n" +
                            "\""+lectureInformation.getReview()+"\"\n\n" +
                            "초기 메뉴로 이동합니다.\n";
                }
            }
        }

        jobjText.put("text", text);
        jobjRes.put("message", jobjText);

        response.put("depth",tempDepth);
        response.put("res",jobjRes);
        return response;
    }

    public LectureInformation getLectureInformation(String user_key){
        int id = lectureInformationDAO.getId(user_key);
        LectureInformation lectureInformation=lectureInformationDAO.getLectureInformation(id);
        return lectureInformation;
    }

    public static boolean isStringInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        } 
    }


}
