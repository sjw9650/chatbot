package kr.or.connect.chatbotserver.api;

import java.util.*;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import kr.or.connect.chatbotserver.dao.VoteDAO;
import kr.or.connect.chatbotserver.model.*;
import kr.or.connect.chatbotserver.service.CafeteriaMenuService;
import kr.or.connect.chatbotserver.service.LostService;
import kr.or.connect.chatbotserver.service.PhoneNumberOfUniversityService;
import kr.or.connect.chatbotserver.service.ScheduleService;
import kr.or.connect.chatbotserver.service.UserService;
import kr.or.connect.chatbotserver.service.*;

import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ChatbotController {
    @Autowired
    ScheduleService ScheduleService;
    @Autowired
    PhoneNumberOfUniversityService phoneNumberOfUniversityService;
    @Autowired
    UserService userService;
    @Autowired
    LostService lostService;
    @Autowired
    CafeteriaMenuService cafeteriaMenuService;
    @Autowired
    VoteDAO voteDAO;

    @Autowired
    FoodEvaluationService foodEvaluationService;

    @Autowired
    LectureInformationService lectureInformationService;

    // 키보드 초기화면에 대한 설정
    @RequestMapping(value = "/keyboard", method = RequestMethod.GET)
    public String keyboard() {
        System.out.println("/keyboard");
        return home().toJSONString();
    }

    // 메세지
    @RequestMapping(value = "/message", method = RequestMethod.POST, headers = "Accept=application/json")
    @ResponseBody
    public String message(@RequestBody JSONObject resObj) throws Exception{

        System.out.println("/message");
        System.out.println(resObj.toJSONString());
        String content;
        content = (String) resObj.get("content");
        JSONObject jobjRes = new JSONObject();
        JSONObject jobjText = new JSONObject();
        // 메시지 구현

        String user_key = (String)resObj.get("user_key");
        if(!userService.AddUser(user_key))
            System.out.println("\n-------------user Add Fail---------------\n");




        // User Key 값을 이용하여 user의 Depth를 추적
        // 30~50  분실물
        // 61~65 도서관
        // 51~59 = 강의 평가
        User user = new User();
        user = userService.getUserbykey(user_key);

        int depth = user.getDepth();
        user.setDepth(0);

        if(content.equals("취소")){
            if(depth >= 33 && depth <=50){
                lostService.lostCancel(user.getConvertId(),depth);
            }
            else  if(depth >= 51 && depth <=59){
                lectureInformationService.lectureInformationCancel(user.getConvertId(),depth);
            }else if(depth==90){
                foodEvaluationService.foodEvaluationCancel(user.getConvertId());
            }

            jobjText.put("text","취소를 누르셨습니다.(씨익)\n\n초기메뉴로 이동하겠습니다.\n" );
            jobjRes.put("message", jobjText);
        }else if(content.equals("교내전화번호")) {
            user.setDepth(100);
            jobjText.put("text", "찾으려는 교내 전화번호에 대한 검색어를 입력해주세요.(최고)\n");
            jobjRes.put("message", jobjText);
        }else if(content.equals("시설물예약")){
            JSONObject jsonMB = new JSONObject();
            jsonMB.put("label","시설물예약");
            String searchurl = "http://117.16.231.147:8080/quni/main.jsp?platform_chk=pc&sub_id=201201510&icu_mem_id=icu_public";
            jsonMB.put("url",searchurl);
            jobjText.put("text", "학교의 모든 실을 인터넷으로 예약할 수 있도록 시스템이 구성되어 있으나, 해당 대학에서 온라인 실예약 시스템을 운용하지 않을 경우에는 예약 가능한 건물목록 및 실목록이 검색창에 나타나지 않습니다.\n" +
                    "\n현재는 공동사용시설인 강당, 공연장, 소극장 및 체육시설등을 제한적으로 운영중입니다.)\n" +
                    "\n" +
                    "실 예약 시스템 운영 문의는 각 대학 교학실에 연락 주시고, 예약 시스템 사용 관련 문의는 시설팀 예약관리 담당자에게 연락 주시기 바랍니다. \n" +
                    "(콜) 시스템관련문의 : 070-4618-3017 \n (콜) 예약관련문의 : 032-835-9515");

            jobjText.put("message_button",jsonMB);
            jobjRes.put("message", jobjText);

        }else if(content.equals("공지사항")){
            jobjText.put("text","사용법은 다음과 같습니다. " +
                    "(굿)\n취업관련 사항은 \"취업\" " +
                    "장학금관련 사항은 \"장학금\" " +
                    "일반관련 사항은 \"일반\" " +
                    "행사관련 사항은 \"행사\"를 선택 해주세요." );
            jobjRes.put("message", jobjText);
            jobjRes.put("keyboard", noticeButton());
            user.setDepth(77);
        }else if(content.equals("분실물")) {
            jobjText.put("text", "분실물을 습득하신 분은 \"등록\"을\n" +
                    "분실물을 찾으시는 분들은 \"찾기\"를\n" +
                    "선택하여 주세요~(윙크)\n\n" + "이전으로 돌아가시려면 \"취소\"를 입력해주세요~\n");
            jobjRes.put("message", jobjText);

            JSONObject josonKeyboard = new JSONObject();
            josonKeyboard.put("type", "buttons");
            ArrayList<String> btns = new ArrayList<>();
            btns.add("등록");
            btns.add("찾기");
            btns.add("취소");
            josonKeyboard.put("buttons", btns);
            jobjRes.put("keyboard", josonKeyboard);
            user.setDepth(33);

        }else if(content.equals("도서관")){
            jobjText.put("text", "도서관 이용 방법에 대해서 알려드립니다.\n\n\n" +
                    "(방긋)도서관 열람실 정보를 확인을 위해서는\n" +
                    "\"열람실 좌석\"을 입력해주세요.\n" +
                    "\n(방긋)도서 검색을 위해서는\n" +
                    "\"도서검색\"을 입력해주세요.\n\n" +
                    "초기 메뉴로 돌아가시려면 \"취소\"를 입력하세요.\n\n");
            jobjRes.put("message", jobjText);

            JSONObject josonKeyboard = new JSONObject();
            josonKeyboard.put("type", "buttons");
            ArrayList<String> btns = new ArrayList<>();
            btns.add("열람실 좌석");
            btns.add("도서검색");
            btns.add("스터디룸 예약");
            btns.add("취소");
            josonKeyboard.put("buttons", btns);
            jobjRes.put("keyboard", josonKeyboard);

            user.setDepth(60);

        }else if(content.equals("일정")){
            JSONObject jsonMB = new JSONObject();
            jsonMB.put("label","일정");
            String url = "http://52.78.164.183:9090/user/schedules/start/" + user.getConvertId();
            jsonMB.put("url",url);
            jobjText.put("text","\"일정관리\"를하기 위해 해당 URL에서\n" +
                    "하실수 있습니다.(굿)\n"+url);
            jobjText.put("message_button",jsonMB);
            jobjRes.put("message", jobjText);

        } else if(content.equals("강의후기")){
            jobjText.put("text", "강의 후기에 대해서 등록하시는 건가요?? \n 검색하시려는 건가요??\n");
            jobjRes.put("message", jobjText);

            JSONObject josonKeyboard = new JSONObject();
            josonKeyboard.put("type", "buttons");
            ArrayList<String> btns = new ArrayList<>();
            btns.add("등록");
            btns.add("검색");
            btns.add("취소");
            josonKeyboard.put("buttons", btns);
            jobjRes.put("keyboard", josonKeyboard);

            user.setDepth(51);

        }else if(content.equals("학교식당")){
            jobjText.put("text","학교식당에 메뉴에 대해서 궁금하신가요?? (웃음)\n\n " +
                    "오늘 메뉴에 대해서 평가하실건가요?? (궁금)\n\n" );
            jobjRes.put("message", jobjText);
            JSONObject josonKeyboard = new JSONObject();
            josonKeyboard.put("type", "buttons");
            ArrayList<String> btns = new ArrayList<>();
            btns.add("학식메뉴");
            btns.add("학식평가");
            btns.add("취소");
            josonKeyboard.put("buttons", btns);
            jobjRes.put("keyboard", josonKeyboard);
            user.setDepth(87);
        }else if(depth == 87){
          if  (content.equals("학식메뉴")){
              JSONObject josonKeyboard = new JSONObject();
              josonKeyboard.put("type", "buttons");
              ArrayList<String> btns = new ArrayList<>();
              btns.add("학식메뉴");
              btns.add("학식평가");
              btns.add("취소");
              josonKeyboard.put("buttons", btns);
              jobjText.put("text",getAllCafeteriaMenu());
              jobjRes.put("message", jobjText);
              jobjRes.put("keyboard", josonKeyboard);
              user.setDepth(87);
          } else if(content.equals("학식평가")){
              String text = foodEvaluationService.resultFoodEvaluation();
              text+="\n투표하시려면 투표하고 싶은 메뉴를 선택해주세요!\n";
              jobjText.put("text",text);
              jobjRes.put("message", jobjText);
              jobjRes.put("keyboard",voteButton());
              user.setDepth(89);
          }
        }

        else if(content.equals("기타")){
            jobjText.put("text","기타 사항은 현재 준비중인 서비스 입니다.\n\n 초기메뉴로 이동하겠습니다.\n" );
            jobjRes.put("keyboard", home());
            jobjRes.put("message", jobjText);
        }
        else if(depth >= 33 && depth <=50){    // 33~50 = 분실물
            JSONObject result = lostService.lost_(user.getConvertId(),depth,content);
            jobjRes= (JSONObject) result.get("res");
            if((int)result.get("depth")== 0 ){
                jobjRes.put("keyboard", home());
            }
            user.setDepth((int)result.get("depth"));
        }
        else if(depth>= 51 && depth <= 59){
            JSONObject result = lectureInformationService.lectureInformationDepth(user.getConvertId(),depth,content);
            jobjRes= (JSONObject) result.get("res");
            if((int)result.get("depth")== 0 ){
                jobjRes.put("keyboard", home());
            }
            user.setDepth((int)result.get("depth"));
        }
        else if(depth==60){
            if(content.equals("도서검색")){
                jobjText.put("text", "도서 검색 서비스입니다.(찡긋)\n" +
                        "찾으시려는 책을 입력해주세요.\n\n\n"+
                        "(하하)초기 메뉴로 돌아가시려면 \"취소\"를 입력하세요.\n\n");
                jobjRes.put("message", jobjText);
                user.setDepth(61);
            }else if(content.equals("열람실 좌석")) {
                jobjText.put("text","선택 해주세요\n");
                jobjRes.put("message",jobjText);
                jobjRes.put("keyboard", libraryButton());
                user.setDepth(62);
            }
            else if(content.equals("스터디룸 예약")){

                jobjRes.put("message", jobjText);
                jobjRes.put("keyboard", home());

                JSONObject jsonMB = new JSONObject();
                jsonMB.put("label","스터디룸 예약");
                String searchurl = "http://mlib.inu.ac.kr/studyroom/main";

                jsonMB.put("url",searchurl);
                jobjText.put("text", "스터디룸 이용안내\n" +
                        "\n" +
                        "(별) 사용시간\n" +
                        "-월 최대 20시간(최소 1시간이상 이였을 때 신청가능)\n" +
                        "\n" +
                        "(별) 사용방법\n" +
                        "-사용 15일 이전에 대표이용자가 도서관 홈페이지를 통해 사전 예약\n" +
                        "-대표이용자 외 공동이용자 함께 등록 (최소 예약 인원보다 적은 인원으로 사용하는 경우 퇴실조치 및 예약취소)\n" +
                        "-예약 시 ID를 도용하면 도서관 이용에 제한을 받을 수 있음\n" +
                        "\n" +
                        "(별) 유의사항\n" +
                        "-예약된 시간에 이용이 불가능할 경우 다음 이용자들 위해 사전에 예약을 취소할 것\n" +
                        "-대표이용자의 예약 시간 제한 (월 20시간으로 제한)\n" +
                        "-예약 후 취소 없이 이용하지 않을 경우 예약제한을 받을 수 있음\n" +
                        "-예약완료(승인완료) 후에는 예약일에 각 해당 스터디룸 자료실에 방문하여 예약 확인 후 이용\n" +
                        "-스터디룸 이용당일에는 반드시 이용자 모두 학생증을 지참하여야 함 (본인확인 후 이용가능)\n" +
                        "\n" +
                        "(별) 예약방법\n" +
                        "1. 학산도서관 홈페이지 로그인 후 왼쪽하단 스터디룸 예약 선택\n" +
                        "2. 호실 및 날짜 선택\n" +
                        "3. 희망시간 선택(최소1시간 최대20시간)\n" +
                        "4. 신청한 이용자의 세부사항(전화번호, 소속/학과, 이메일)을 확인\n" +
                        "5. 주요내용에 참가인원의 수만큼 학번과 성명을 반드시 기재\n" +
                        "6. 예약완료\n" +
                        "7. 관리자 승인 확인 후 이용가능\n" +
                        "※ 공동 프로젝트가 아닌 단순 스터디나 모임의 목적으로 활용시 이용에 제한을 받을 수 있습니다.");
                jobjText.put("message_button",jsonMB);
                jobjRes.put("message", jobjText);

            }
        }else if(depth==61){
            JSONObject jsonMB = new JSONObject();
            jsonMB.put("label",content+" 검색 결과입니다.");
            String searchurl = "http://mlib.inu.ac.kr/search/tot/result?si=TOTAL&st=KWRD&q=";
            searchurl+=content;
            jsonMB.put("url",searchurl);
            jobjText.put("text", content+"에 대한 도서 검색 결과입니다.\n" +
                    "다른 도서를 검색하시려면 검색 키워드를 입력해주세요\n\n\n"+
                    "초기 메뉴로 돌아가시려면 \"취소\"를 입력하세요.\n\n");
            jobjText.put("message_button",jsonMB);
            jobjRes.put("message", jobjText);
            user.setDepth(61);
        }
        else if(depth==62){
            libraryCrawling(content,jobjText);
            jobjRes.put("message", jobjText);
            jobjRes.put("keyboard", libraryButton());
            user.setDepth(62);
        }else if(depth==89){
            CafeteriaMenu cafeteriaMenu = new CafeteriaMenu();
            cafeteriaMenu = cafeteriaMenuService.FindMenu(content);

            jobjText.put("text",foodEvaluationService.selectMenu(cafeteriaMenu,content,user.getConvertId()));
            JSONObject josonKeyboard = new JSONObject();
            josonKeyboard.put("type", "buttons");
            ArrayList<String> btns = new ArrayList<>();
            btns.add("취소");
            btns.add("★★★★★");
            btns.add("★★★");
            btns.add("★");
            josonKeyboard.put("buttons", btns);
            jobjRes.put("keyboard", josonKeyboard);
            jobjRes.put("message", jobjText);
            user.setDepth(90);

        }else if(depth==90){
            jobjText.put("text",foodEvaluationService.Evaluating(content,user.getConvertId()));
            jobjRes.put("message", jobjText);
        }
        else if(depth==100){
            JSONObject result = phoneNumberOfUniversityService.infomPhoneNumber(content);
            jobjRes= (JSONObject) result.get("res");

            user.setDepth((int)result.get("depth"));
            userService.setDepth(user);
        }
        else if(depth==77){
            noticeCrawling(content,jobjText);
            jobjRes.put("message", jobjText);
            jobjRes.put("keyboard", noticeButton());
            user.setDepth(77);
        }
        else {
            // 분실물 등록시 위치별 다른 형태의 버튼을 출력하기 위해서 jobjRes를 받아올 수 있게
            // 하기위해 그렇지 않은건 jobjRes 형식을 message로 통일시키고 추후에 변경 예정
            if(content.contains("안녕")){
                jobjText.put("text","오냐~~~");
            } else if(content.contains("사랑해")){
                jobjText.put("text","나도 너무너무 사랑해");
            } else if(content.contains("잘자")){
                jobjText.put("text","꿈 속에서도 너를 볼꺼야");
            } else if(content.contains("설문조사")){
                jobjText.put("text","등록된 설문조사가 없습니다.(화남)");
            }
            else {
                jobjText.put("text","지정하지 않은 답변입니다. 사용 법을 알고 싶  으면 \"시작하기\"를 입력하세요.");
            }
            jobjRes.put("message", jobjText);
        }


        if(user.getDepth() == 0){
            jobjRes.put("keyboard", home());
        }
        userService.setDepth(user);
        System.out.println(jobjRes.toJSONString());
        return  jobjRes.toJSONString();
    }

    //사용자가 옐로아이디를 친구추가했을 때 호출되는 API

    @RequestMapping(value = "/friend", method = RequestMethod.POST, headers = "Accept=application/json")
    public String addKakaoFriend(@RequestBody JSONObject resObj) throws Exception
    {
        System.out.println("/friend");
        String user_key;
        user_key = (String) resObj.get("user_key");
        // 초기 등록시에 USER 키와 Depth를 삽입해서 넣어준다.
        if(!userService.AddUser(user_key))
            System.out.println("\n-------------user Add Fail---------------\n");

        System.out.println(resObj.toJSONString());
        return resObj.toJSONString();
    }

    public JSONObject home(){
        JSONObject jobjBtn = new JSONObject();
        jobjBtn.put("type", "buttons");
        ArrayList<String> btns = new ArrayList<>();
        btns.add("공지사항");
        btns.add("학교식당");
        btns.add("도서관");
        btns.add("분실물");
        btns.add("일정");
        btns.add("강의후기");
        btns.add("시설물예약");
        btns.add("교내전화번호");
        btns.add("기타");
        jobjBtn.put("buttons",btns);
        return jobjBtn;
    }
    public JSONObject noticeButton(){
        JSONObject jobjBtn = new JSONObject();
        jobjBtn.put("type", "buttons");
        ArrayList<String> btns = new ArrayList<>();
        btns.add("취업");
        btns.add("장학금");
        btns.add("일반");
        btns.add("행사");
        btns.add("취소");
        jobjBtn.put("buttons",btns);
        return jobjBtn;
    }
    public JSONObject libraryButton(){
        JSONObject jobjBtn = new JSONObject();
        jobjBtn.put("type", "buttons");
        ArrayList<String> btns = new ArrayList<>();
        btns.add("자유열람실1");
        btns.add("자유열람실2");
        btns.add("자유열람실3");
        btns.add("노트북코너");
        btns.add("취소");
        jobjBtn.put("buttons",btns);
        return jobjBtn;
    }

    public void noticeCrawling(String subject,JSONObject jobjTest) throws  Exception{
        String URL="";
        if(subject.equals("취업")){
            URL = "http://www.inu.ac.kr/user/boardList.do?boardId=49235&siteId=mobile&id=mobile_060202000000";
        }else if(subject.equals("장학금")){
            URL = "http://www.inu.ac.kr/user/boardList.do?boardId=49227&siteId=mobile&id=mobile_060205000000";
        }else if(subject.equals("일반")){
            URL = "http://www.inu.ac.kr/user/boardList.do?boardId=49219&siteId=mobile&id=mobile_060206000000";
        }else if(subject.equals("행사")){
            URL = "http://www.inu.ac.kr/user/boardList.do?boardId=49211&siteId=mobile&id=mobile_060207000000";
        }
        Document doc = Jsoup.connect(URL).get();
        Elements links = doc.select("div.tab_cont ul li");
        String data="";
        for (Element link : links) {
            Elements attr = link.select("a[href]");
            Elements img = link.select("img[src]");
            if (!(img.isEmpty())){
                data+= attr.text()+'\n'+"http://www.inu.ac.kr/user/" + attr.attr("href")+"\n\n";
            }
        }
        if(data.equals("")){
            data+="최신정보가 없습니다.\n";
            data+="홈페이지를 통해서 확인해주세요.(훌쩍)\n";
            data+=URL;
        }
        jobjTest.put("text",data);
    }
    public void libraryCrawling(String subject,JSONObject jobjTest) throws  Exception{
        JSONObject messageButton = new JSONObject();
        String URL="";
        if(subject.equals("자유열람실1")){
            URL = "http://117.16.225.214:8080/SeatMate.php?classInfo=1";
        }else if(subject.equals("자유열람실2")){
            URL = "http://117.16.225.214:8080/SeatMate.php?classInfo=2";
        }else if(subject.equals("자유열람실3")){
            URL = "http://117.16.225.214:8080/SeatMate.php?classInfo=3";
        }else if(subject.equals("노트북코너")){
            URL = "http://117.16.225.214:8080/SeatMate.php?classInfo=4";
        }
        messageButton.put("label","좌석표 보기");
        messageButton.put("url",URL);
        Document doc = Jsoup.connect(URL).get();
        Elements links = doc.select("b"); //b태그 안에 있는 사용중 좌석 수, 사용가능 좌석 수 Crawring
        StringBuilder data = new StringBuilder();
        int idx = 0;
        for (Element link : links) {
            if(idx==0){
                data.append("사용중좌석 : "+link.text()+'\n');
            }
            else{
                data.append("사용가능좌석 : "+link.text()+'\n');
            }
            idx++;
        }
        jobjTest.put("text",data.toString());
        jobjTest.put("message_button",messageButton);
    }

    public String getAllCafeteriaMenu() {
        List<CafeteriaMenu> test = cafeteriaMenuService.getAllCafeteriaMenu();
        StringBuilder[] temp = new StringBuilder[5];
        for(int j=0;j<5;j++){
            temp[j] = new StringBuilder("");
        }
        for(CafeteriaMenu data : test){
            if(data.getCafeteria_managements_cafeteria_managements_id()==1){
                temp[0].append(data.getMenu()+" / ");
            }
            if(data.getCafeteria_managements_cafeteria_managements_id()==2){
                temp[1].append(data.getMenu()+" / ");
            }
            if(data.getCafeteria_managements_cafeteria_managements_id()==3){
                temp[2].append(data.getMenu()+" / ");
            }
            if(data.getCafeteria_managements_cafeteria_managements_id()==4){
                temp[3].append(data.getMenu()+" / ");
            }
            if(data.getCafeteria_managements_cafeteria_managements_id()==5){
                temp[4].append(data.getMenu()+" / ");
            }
        }
        StringBuilder text = new StringBuilder("");
        for(int i=0;i<5;i++){
            text.append("(밥)"+(i+1)+"코너(밥)\n"+temp[i]+'\n');
        }
        return text.toString();
    }

    public JSONObject voteButton(){
        JSONObject jobjBtn = new JSONObject();
        List<CafeteriaMenu> temp = cafeteriaMenuService.getAllCafeteriaMenu();
        jobjBtn.put("type", "buttons");
        ArrayList<String> btns = new ArrayList<>();
        for(CafeteriaMenu data: temp) {
            if(data.getCafeteria_managements_cafeteria_managements_id()>3 || data.getMenu().contains("오늘은 쉽니다")) continue;
            btns.add(data.getMenu());
        }
        btns.add("취소");
        jobjBtn.put("buttons", btns);
        return jobjBtn;
    }



    public String getRankedData(){
        List<Rank> test = voteDAO.getRankedData();
        StringBuilder text = new StringBuilder();
        int idx = 0;
        for(Rank data : test){
            if(idx>2) break;
            text.append((idx+1)+"위 : "+data.getMenu()+' '+data.getScore()+"점"+'\n');
            idx++;
        }

        return text.toString();
    }

}

