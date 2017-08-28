package kr.or.connect.chatbotserver.api;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.*;



@RestController
public class ChatbotController {

    // 키보드 초기화면에 대한 설정
    @RequestMapping(value = "/keyboard", method = RequestMethod.GET)
    public String keyboard() {

        System.out.println("/keyboard");    
        JSONObject jobjBtn = new JSONObject();
        jobjBtn.put("type", "buttons");
		jobjBtn.put("buttons",new String[] {"시작하기", "환경설정"});

        return jobjBtn.toJSONString();
    }

    // 메세지
    @RequestMapping(value = "/message", method = RequestMethod.POST, headers = "Accept=application/json")
    @ResponseBody
    public String message(@RequestBody JSONObject resObj) {
		
        System.out.println("/message");
        System.out.println(resObj.toJSONString());

        String content;
        content = (String) resObj.get("content");
        JSONObject jobjRes = new JSONObject();
        JSONObject jobjText = new JSONObject();

        // 사용자 구현
        if(content.contains("안녕")){
            jobjText.put("text","안녕 하세요");
        } else if(content.contains("사랑")){
            jobjText.put("text","나도 너무너무 사랑해");
        } else if(content.contains("잘자")){
            jobjText.put("text","꿈 속에서도 너를 볼꺼야");
        } else if(content.contains("졸려")){
            jobjText.put("text","졸리면 언능 세수하러 가용!");
        } else if(content.contains("시간")||content.contains("몇 시")){
            jobjText.put("text","섹시");
        }else if(content.contains("시작하기")){
            jobjText.put("text","초기에 시작하는 방법을 안내");
        }else
        {
            jobjText.put("text","지정하지 않은 답변입니다. 사용 법을 알고 싶으면 \"시작하기\"를 입력하세요.");
        }

        jobjRes.put("message", jobjText);
        System.out.println(jobjRes.toJSONString());

        return  jobjRes.toJSONString();
    }
	
	//사용자가 옐로아이디를 친구추가했을 때 호출되는 API
	@RequestMapping(value = "/friend", method = RequestMethod.POST, headers = "Accept=application/json")
	public String addKakaoFriend(@RequestBody JSONObject resObj) 
	{
		System.out.println(resObj.toJSONString());
		return resObj.toJSONString();
	}
}
