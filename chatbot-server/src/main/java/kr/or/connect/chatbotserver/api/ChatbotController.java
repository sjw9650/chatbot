package kr.or.connect.chatbotserver.api;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import kr.or.connect.chatbotserver.lost.ImageGet;
import kr.or.connect.chatbotserver.model.Schedule;
import kr.or.connect.chatbotserver.model.User;

import kr.or.connect.chatbotserver.service.LostService;
import kr.or.connect.chatbotserver.service.ScheduleService;
import kr.or.connect.chatbotserver.service.UserService;

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
	UserService userService;
    @Autowired
    LostService lostService;
	
	
    // 키보드 초기화면에 대한 설정
    @RequestMapping(value = "/keyboard", method = RequestMethod.GET)
    public String keyboard() {
        System.out.println("/keyboard");
        JSONObject jobjBtn = new JSONObject();
        jobjBtn.put("type", "buttons");
        ArrayList<String> btns = new ArrayList<>();
        btns.add("공지사항");
        btns.add("일정");
        btns.add("분실물");
        jobjBtn.put("buttons",btns);
        return jobjBtn.toJSONString();
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


        // 현재 추가되 있는 사람들을 위한 소스로 최종 빌드시에 삭제 해야함
        if(!userService.AddUser(user_key))
            System.out.println("\n-------------user Add Fail---------------\n");
        // User Key 값을 이용하여 user의 Depth를 추적
        User user = new User();
        user = userService.getUserbykey(user_key);
        int depth = user.getDepth();

        // 33 = 분실물
        // 34 = 분실물 등록
        // 35 = 분실물 등록취소 (DB 반영 전)
        // 35 = 분실물 등록취소 (DB 반영 후)
        // 36 = 분실물 등록 - 물건 이름 등록

        if(depth >= 33){
            jobjText = lost_(content,user);
        }
        else if(content.equals("공지사항")){
            jobjText.put("text","사용법은 다음과 같습니다. " +
                    "(굿)\n취업관련 사항은 \"취업\" " +
                    "장학금관련 사항은 \"장학금\" " +
                    "일반관련 사항은 \"일반\" " +
                    "행사관련 사항은 \"행사\"를 선택 해주세요." );
            jobjText.put("type","buttons");
            ArrayList<String> btns = new ArrayList<>();
            btns.add("취업");
            btns.add("장학금");
            btns.add("일반");
            btns.add("행사");
            jobjText.put("buttons",btns);
        } else if(content.equals("취업")){
            noticeCrawling("취업",jobjText);
        } else if(content.equals("장학금")){
            noticeCrawling("장학금",jobjText);
        } else if(content.equals("일반")){
            noticeCrawling("일반",jobjText);
        } else if(content.equals("행사")){
            noticeCrawling("행사",jobjText);
        }else if(content.equals("분실물")){
            jobjText.put("text","분실물을 습득하신 분은 \"등록\"을\n"+
                                "분실물을 찾으시는 분들은 \"찾기\"를\n"+
                                "선택하여 주세요~(윙크)\n");
            user.setDepth(33);
            userService.setDepth(user);
        }else if(content.equals("일정")){
            jobjText.put("text","사용법은 다음과 같습니다. (굿)");
        } else if(content.contains("안녕")){
            jobjText.put("text","초면에 반말이시네요!!");
        } else if(content.contains("사랑해")){
            jobjText.put("text","나도 너무너무 사랑해d");
        } else if(content.contains("잘자")){
            jobjText.put("text","꿈 속에서도 너를 볼꺼야");
        } else if(content.equals("일정 가져와")){

          String contents = "";

          List<Schedule> list = ScheduleService.getAllSchedules();
          for(Schedule schedule : list){
            contents += schedule.getContent() + "\n";
          }
          jobjText.put("text", contents);
          
        } else if(content.contains("설문조사")){
            jobjText.put("text","등록된 설문조사가 없습니다.(화남)");
        }
        else {
            jobjText.put("text","지정하지 않은 답변입니다. 사용 법을 알고 싶으면 \"시작하기\"를 입력하세요.");

        }

        jobjRes.put("message", jobjText);
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

    private JSONObject lost_(String content, User user) throws IOException {

        JSONObject jobjText = new JSONObject();
        int depth = user.getDepth();
        String user_key = user.getUser_key();
        String content_="";
        if(content.equals("취소")){
            jobjText=lostCancel(user);
            if(depth!=34){
                lostService.removeLost(user_key);
            }
        }
        else if(depth==33 ){
            if(content.equals("등록")) {
                jobjText.put("text", "분실물을 습득하셨네요~(우와)\n" +
                        "분실물에 대한 정보를 알기 위해 간단한 몇가지 질문을 하겠습니다.\n" +
                        "주우신 물건이 어떤 건가요??\n" +
                        "자세히 묘사해주시면 감사하겠습니다.(반함)\n\n" +
                        "분실물 등록을 취소 하시려면 \"취소\"를 입력해주세요~\n");
                user.setDepth(34);
                userService.setDepth(user);
            }
            else if(content.equals("찾기")){
                jobjText.put("text", "준비중입니다.");
                user.setDepth(0);
                userService.setDepth(user);
            }
        }
        else if(depth==34){
            lostService.addLost(user_key,content);
            jobjText.put("text", content+"을(를) 습득하셨네요~(우와)\n" +
                    content+"을(를) 어디서 발견하셨나요?? \n" +
                    "상세할 수록 좋습니다.(씨익)\n\n" + "분실물 등록을 취소 하시려면 \"취소\"를 입력해주세요~\n");
            user.setDepth(35);
            userService.setDepth(user);

        }
        else if(depth==35){
            lostService.setLost(user_key,1,content);
            content_ = lostService.getContent(user_key);
            jobjText.put("text", content+"에서 습득하셨네요~(우와)\n" +
                    content_+"을(를) 언제 발견하셨나요?? \n" +
                    "예)2017년 09월 03일 17시~18시 \"20170903 17\"을 입력해주시면 됩니다.(좋아)\n" +
                    "혹은 \"오늘 17\",\"어제 17\"와 같이 입력해주셔도 됩니다. \n\n" + "분실물 등록을 취소 하시려면 \"취소\"를 입력해주세요~\n");
            user.setDepth(36);
            userService.setDepth(user);
        }else if(depth==36){
            String date_="";
            if(content.contains("어제")){

            }
            else if (content.contains("오늘")){

            }else {

            }
            lostService.setLost(user_key,2,date_);
            content_ = lostService.getContent(user_key);
            jobjText.put("text", date_+"시 쯤에 습득하셨네요~(우와)\n" +
                    content_+"을(를) 맡기신 곳이 있다면 어디에 맡기셨나요??\n" +
                    "혹은 가지고 계시다면 자신이 누구인지 알려주실 수 있으신가요??\n\n" +
                    "분실물 등록을 취소 하시려면 \"취소\"를 입력해주세요~\n");
            user.setDepth(37);
            userService.setDepth(user);
        }else if(depth==37){
            lostService.setLost(user_key,3,content);
            content_ = lostService.getContent(user_key);
            jobjText.put("text", content+"에서(가) 보관하고 있군요!!(우와)\n" +
                    content_+"을(를) 에 대한 사진이 있으시다면 사진을 보내주세요!\n" +
                    "사진이 없으면 \"없음\"을 선택해주세요. \n\n" +
                    "분실물 등록을 취소 하시려면 \"취소\"를 입력해주세요~\n");
            user.setDepth(38);
            userService.setDepth(user);
        }else if(depth==38){
            String img = content;
            if(content.equals("없음")){
                content_="없음";
            }
            else {
                content_=Integer.toString(lostService.getId(user_key));
                ImageGet imageGet = new ImageGet(content,content_);
                imageGet.saveImage();
            }
            lostService.setLost(user_key,4,content_);
            content_ = lostService.getContent(user_key);
            jobjText.put("text", content_+"의 분실물 등록을 모두 하였습니다.(우와)\n" +
                    "\n등록하시느라 고생 많으셨습니다. (박수)" +
                    "\n등록된 분실물은 주인을 찾을 수 있도록 노력하겠습니다." +
                    "\n감사합니다.(최고)(최고)(최고)");
            user.setDepth(0);
            userService.setDepth(user);
        }


        return jobjText;
    }
    private JSONObject lostCancel(User user){
        JSONObject jobjText = new JSONObject();
        jobjText.put("text","분실물 등록을 취소하였습니다~ \n\n");
        user.setDepth(0);
        userService.setDepth(user);
        return jobjText;
    }
}


