package kr.or.connect.chatbotserver.lost;

import kr.or.connect.chatbotserver.model.User;
import kr.or.connect.chatbotserver.service.LostService;
import kr.or.connect.chatbotserver.service.UserService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Lost_API {

    @Autowired
    UserService userService;
    @Autowired
    LostService lostService;

    JSONObject jobjText;
    JSONObject jobjRes;

    private String content;
    private User user;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Lost_API(){
        jobjText = new JSONObject();
        jobjRes = new JSONObject();
    }

    public Lost_API(String content,User user){
        this.content=content;
        this.user=user;
        jobjText = new JSONObject();
        jobjRes = new JSONObject();
    }

    public JSONObject request_lost(){
        jobjText.put("text", "분실물을 습득하셨네요~(우와)\n" +
                "분실물에 대한 정보를 알기 위해 간단한 몇가지 질문을 하겠습니다.\n" +
                "주우신 물건이 어떤 건가요??\n" +
                "자세히 묘사해주시면 감사하겠습니다.(반함)\n\n" +
                "분실물 등록을 취소 하시려면 \"취소\"를 입력해주세요~\n");
        jobjRes.put("message", jobjText);
        user.setDepth(34);
        userService.setDepth(user);
        return jobjRes;
    }
    public JSONObject request_getplace(){
        lostService.addLost(user.getConvertId(),content);
        jobjText.put("text", content+"을(를) 습득하셨네요~(우와)\n" +
                content+"을(를) 어디서 발견하셨나요?? \n" +
                "상세할 수록 좋습니다.(씨익)\n\n" + "분실물 등록을 취소 하시려면 \"취소\"를 입력해주세요~\n");
        jobjRes.put("message", jobjText);
        user.setDepth(35);
        userService.setDepth(user);
        return jobjRes;
    }
    public JSONObject request_date(){
        lostService.setLost(user.getConvertId(),1,content);
        String content_ = lostService.getContent(user.getConvertId());
        jobjText.put("text", content+"에서 습득하셨네요~(우와)\n" +
                content_+"을(를) 언제 발견하셨나요?? \n" +
                "예)2017년 09월 03일 17시~18시에 발견하였다면\n  \"20170903 17\"을 입력해주시면 됩니다.(좋아)\n" +
                "혹은 \"오늘 17\",\"어제 17\"와 같이 입력해주셔도 됩니다. \n\n" + "분실물 등록을 취소 하시려면 \"취소\"를 입력해주세요~\n");
        jobjRes.put("message", jobjText);
        user.setDepth(36);
        userService.setDepth(user);
        return jobjRes;
    }
    public JSONObject request_putplace(){
        if(validity_check_date()) {
            String[] strArr = content.split(" ");
            String date_="";

                Calendar cal = new GregorianCalendar(Locale.KOREA);
                cal.setTime(new Date());
                SimpleDateFormat fm = new SimpleDateFormat("yyyyMMdd");
                if(strArr[0].equals("어제")){
                    cal.add(Calendar.DAY_OF_MONTH, -1); // 1일을 뺀다.
                    String strDate = fm.format(cal.getTime());
                    date_=strDate+" "+strArr[1];
                    System.out.println(strDate);
                }
                else if (strArr[0].equals("오늘")){
                    String strDate = fm.format(cal.getTime());
                    date_=strDate+" "+strArr[1];
                    System.out.println(strDate);
                }else {
                    date_=content;
                }
                lostService.setLost(user.getConvertId(),2,date_);
                String content_ = lostService.getContent(user.getConvertId());
                jobjText.put("text", date_+"시 쯤에 습득하셨네요~(우와)\n" +
                        content_+"을(를) 맡기신 곳이 있다면 어디에 맡기셨나요??\n" +
                        "혹은 가지고 계시다면 자신이 누구인지 알려주실 수 있으신가요??\n\n" +
                        "분실물 등록을 취소 하시려면 \"취소\"를 입력해주세요~\n");
                jobjRes.put("message", jobjText);
                user.setDepth(37);
                userService.setDepth(user);

        }
        else {
                // 입력값이 이상하면
            String content_ = lostService.getContent(user.getConvertId());
            jobjText.put("text","입력된 시간 값이 올바르지 않습니다.(훌쩍)\n\n"+content_+"을(를) 언제 발견하셨나요?? \n" +
                    "예)2017년 09월 03일 17시~18시에 발견하였다면\n  \"20170903 17\"을 입력해주시면 됩니다.(좋아)\n" +
                    "혹은 \"오늘 17\",\"어제 17\"와 같이 입력해주셔도 됩니다. \n\n" + "분실물 등록을 취소 하시려면 \"취소\"를 입력해주세요~\n");
            jobjRes.put("message", jobjText);
        }
        return jobjRes;
       }

    public JSONObject request_img(){
        lostService.setLost(user.getConvertId(),3,content);
        String content_ = lostService.getContent(user.getConvertId());
        jobjText.put("text", content+"에서(가) 보관하고 있군요!!(우와)\n" +
                content_+"을(를) 에 대한 사진이 있으시다면 사진을 보내주세요!\n" +
                "사진이 없으면 \"없음\"을 선택해주세요. \n\n" +
                "분실물 등록을 취소 하시려면 \"취소\"를 입력해주세요~\n");
        jobjRes.put("message",jobjText);
        JSONObject josonKeyboard =new JSONObject();

        josonKeyboard.put("type", "buttons");
        ArrayList<String> btns = new ArrayList<>();
        btns.add("없음");
        btns.add("취소");
        josonKeyboard.put("buttons",btns);
        jobjRes.put("keyboard",josonKeyboard);
        user.setDepth(38);
        userService.setDepth(user);
        return jobjRes;
    }

    public JSONObject complete_add() throws IOException {
        String content_ = "";
        String user_key=user.getConvertId();
        if (content.equals("없음") || content.contains(".jpg")) {
            if (content.equals("없음")) content_ = "없음";
            else {
                content_ = Integer.toString(lostService.getId(user_key));
                ImageGet imageGet = new ImageGet(content, content_);
                imageGet.saveImage();
            }
            lostService.setLost(user_key,4,content_);
            content_ = lostService.getContent(user_key);
            jobjText.put("text", content_+"의 분실물 등록을 모두 하였습니다.(우와)\n" +
                    "\n등록하시느라 고생 많으셨습니다.(오케이)" +
                    "\n등록된 분실물은 주인을 찾을 수 있도록 노력하겠습니다." +
                    "\n감사합니다.(최고)(최고)(최고)"+
                    "\n\n\"시작하기\"를 입력해주세요(최고)");
            jobjRes.put("message",jobjText);
            user.setDepth(0);
            userService.setDepth(user);
        } else{
            content_ = lostService.getContent(user_key);
            jobjText.put("text", "잘못입력하셨습니다.\n"+content+"에서(가) 보관하고 있군요!!(우와)\n" +
                    content_+"을(를) 에 대한 사진이 있으시다면 사진을 보내주세요!\n" +
                    "사진이 없으면 \"없음\"을 선택해주세요. \n\n" +
                    "분실물 등록을 취소 하시려면 \"취소\"를 입력해주세요~\n");
            jobjRes.put("message",jobjText);
            jobjRes.put("type", "buttons");
            ArrayList<String> btns = new ArrayList<>();
            btns.add("없음");
            btns.add("취소");
            jobjRes.put("buttons",btns);
        }
        return jobjRes;
    }

    public boolean validity_check_date(){
        String date_="";
        if(!content.contains(" ")) return false;
        String[] strArr = content.split(" ");
        if(isStringInt(strArr[1])) return false;
        int time_ =Integer.parseInt(strArr[1]);
        if(time_ <0 &&time_>24) return false;
        if(!strArr[0].equals("어제")||!strArr[0].equals("오늘")||!isStringInt(strArr[0])) return false;
        if(!strArr[0].equals("어제")&&!strArr[0].equals("오늘")) {
            int days_ = 0;
            int day_ = 0;
            int year_ = 0;
            int month_ = 0;
            int flags_ = 0;
            days_ = Integer.parseInt(strArr[0]);
            if (days_ >= 20000000 && days_ <= 30000000) {
                year_ = days_ / 10000;
                month_ = (days_ % 10000) / 100;
                day_ = (days_ % 100);

                Calendar cal = new GregorianCalendar(Locale.KOREA);
                cal.setTime(new Date());

                SimpleDateFormat yy = new SimpleDateFormat("yyyy");
                SimpleDateFormat MM = new SimpleDateFormat("MM");
                SimpleDateFormat dd = new SimpleDateFormat("dd");

                int year = Integer.parseInt(yy.format(cal.getTime())) % 10000;
                int month = Integer.parseInt(MM.format(cal.getTime())) % 10000;
                int day = Integer.parseInt(dd.format(cal.getTime())) % 10000;

                System.out.println(year + "년" + month + "월" + day + "일");
                System.out.println(year_ + "년" + month_ + "월" + day_ + "일");

                if (year < year_ || (year == year_ && month < month_) || (year == year_ && month == month_ && day < day_))
                    return false;
                if (year_ < 2000 || month_ < 1 || month_ > 12 || day_ > 31 || day_ < 0) return false;
            } else return false;
        }
            return true;
    }

    public JSONObject lost_() throws IOException {

        int depth=user.getDepth();
        String user_key =user.getConvertId();
        String content_="";
        if(content.equals("취소")){
            jobjText=lostCancel(user);
            if(depth!=34&&depth!=33){
                lostService.removeLost(user_key);
            }
            jobjRes.put("message", jobjText);
        }
        else if(depth==33 ){
            if(content.equals("등록")) {
                request_lost();
            }
            else if(content.equals("찾기")){
                jobjText.put("text", "물건을 분실하셨나요?(훌쩍)\n" +
                        "분실물에 대한 정보를 알기 위해 간단한 몇가지 질문을 하겠습니다.\n" +
                        "언제 잃어 버리셨나요??\n" +
                        "예)2017년 09월 03일에 발견하였다면\n " +
                        "\"20170903\"을 입력해주시면 됩니다.(좋아)\n" +
                        "혹은 \"오늘\",\"어제\"와 같이 입력해주셔도 됩니다.\n\n" +
                        "분실물 찾기를 취소 하시려면 \"취소\"를 입력해주세요~\n");
                jobjRes.put("message", jobjText);
                user.setDepth(40);
                userService.setDepth(user);
            }
        }
        else if(depth==40){
            if (validity_check_date()){
                jobjText.put("text", content+"에 분실하셨구나(훌쩍)\n" +
                        content+"에 분실물의 발견 장소와 항목입니다.\n\n" +
                        "분실물 찾기를 취소 하시려면 \"취소\"를 입력해주세요~\n");
                jobjRes.put("message", jobjText);
                user.setDepth(40);
                userService.setDepth(user);
            }
            else {

            }
        }
        else if(depth==34){
            request_getplace();
        }
        else if(depth==35){
            request_date();
        }else if(depth==36){
            request_putplace();
        }else if(depth==37){
            request_img();
        }else if(depth==38) {
            complete_add();
        }
        return jobjRes;
    }
    public static boolean isStringInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    private JSONObject lostCancel(User user){
        JSONObject jobjText = new JSONObject();
        jobjText.put("text","분실물 등록을 취소하였습니다~(우와)\n\n" +
                "다른 서비스에 대해서 안내 받으시려면" +
                " \"시작하기\"를 입력해주세요(최고)");
        user.setDepth(0);
        userService.setDepth(user);
        return jobjText;
    }
}
