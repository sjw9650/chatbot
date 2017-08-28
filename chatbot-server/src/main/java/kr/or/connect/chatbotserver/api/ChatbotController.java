package kr.or.connect.chatbotserver.api;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.*;


/**
 * Created by annakim on 7/6/17.
 */
@RestController
public class ChatbotController {

    // 키보드
    @RequestMapping(value = "/keyboard", method = RequestMethod.GET)
    public String keyboard() {

        System.out.println("/keyboard");

        JSONObject jobjBtn = new JSONObject();
        jobjBtn.put("type", "text");

        JSONObject input = new JSONObject();
        input.put("content","시작하기");
        message(input);
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
            jobjText.put("text","흠... 아직 지정해 두지 않은 말인걸.");
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
