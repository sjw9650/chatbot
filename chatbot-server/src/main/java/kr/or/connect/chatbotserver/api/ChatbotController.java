package kr.or.connect.chatbotserver.api;

import java.util.*;

import kr.or.connect.chatbotserver.model.User;
import kr.or.connect.chatbotserver.service.CafeteriaMenuService;
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
	@Autowired
    CafeteriaMenuService cafeteriaMenuService;
	
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
        btns.add("학식메뉴");
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
        System.out.println(Integer.toString(depth));

        // 33~50 = 분실물

        if(depth >= 33 && depth <=50){
            LostController lost_api = new LostController(content,user,userService,lostService);
            jobjRes = lost_api.lost_();
        }
        else {
            // 분실물 등록시 위치별 다른 형태의 버튼을 출력하기 위해서 jobjRes를 받아올 수 있게
            // 하기위해 그렇지 않은건 jobjRes 형식을 message로 통일시키고 추후에 변경 예정
            if(content.equals("공지사항")){
                jobjText.put("text","사용법은 다음과 같습니다. " +
                        "(굿)\n취업관련 사항은 \"취업\" " +
                        "장학금관련 사항은 \"장학금\" " +
                        "일반관련 사항은 \"일반\" " +
                        "행사관련 사항은 \"행사\"를 선택 해주세요." );
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
                        "선택하여 주세요~(윙크)\n\n"+"이전으로 돌아가시려면 \"취소\"를 입력해주세요~\n");
                user.setDepth(33);
                userService.setDepth(user);
            }else if(content.equals("일정")){
            	String url = "http://13.124.220.140:9090/user/schedules/start/" + user.getConvertId();
                jobjText.put("text","\"일정관리\"를하기 위해 해당 URL에서\n" +
                                    "하실수 있습니다.(굿)\n" + url);
                
                
            } else if(content.equals("학식메뉴")){
                jobjText.put("text","사용법은 다음과 같습니다. " +
                        "(굿)\n학생식당메뉴는  \"학생식당\" " +
                        "를 선택 해주세요." );
            }else if(content.equals("학식식당")){
                jobjText.put("text","사용법은 다음과 같습니다. " +
                        "(굿)\n학생식당메뉴는  \"학생식당\" " +
                        "를 선택 해주세요." );
            }
            else if(content.contains("안녕")){
                jobjText.put("text","초면에 반말이시네요!!");
            } else if(content.contains("사랑해")){
                jobjText.put("text","나도 너무너무 사랑해");
            } else if(content.contains("잘자")){
                jobjText.put("text","꿈 속에서도 너를 볼꺼야");
            } else if(content.contains("설문조사")){
                jobjText.put("text","등록된 설문조사가 없습니다.(화남)");
            }
            else {
                jobjText.put("text","지정하지 않은 답변입니다. 사용 법을 알고 싶으면 \"시작하기\"를 입력하세요.");
            }
            jobjRes.put("message", jobjText);
        }

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


}


