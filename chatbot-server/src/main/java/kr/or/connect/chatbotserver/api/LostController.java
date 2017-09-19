package kr.or.connect.chatbotserver.api;

import kr.or.connect.chatbotserver.lost.ImageGet;
import kr.or.connect.chatbotserver.model.Lost;
import kr.or.connect.chatbotserver.model.User;
import kr.or.connect.chatbotserver.service.LostService;
import kr.or.connect.chatbotserver.service.UserService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class LostController {

    @Autowired
    LostService lostService;

    @Autowired
    UserService userService;

    JSONObject jobjText;
    JSONObject jobjRes;
    private String content;
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public LostController(){
        jobjText = new JSONObject();
        jobjRes = new JSONObject();

    }

    public LostController(String content, User user, UserService userService, LostService lostService){
        this.content=content;
        this.user=user;
        this.userService = userService;
        this.lostService = lostService;
        jobjText = new JSONObject();
        jobjRes = new JSONObject();
    }
    public void request_lost(){
        System.out.println("1111");
        jobjText.put("text", "분실물을 습득하셨네요~(우와)\n" +
                "분실물에 대한 정보를 알기 위해 간단한 몇가지 질문을 하겠습니다.\n" +
                "주우신 물건이 어떤(궁금) 건가요??\n" +
                "자세히 묘사해주시면 감사하겠습니다.(반함)\n\n" +
                "분실물 등록을 취소 하시려면 \"취소\"를 입력해주세요~\n");
        jobjRes.put("message", jobjText);
        user.setDepth(34);
        userService.setDepth(user);
    }
    public void request_getplace(){
        lostService.addLost(user.getConvertId(),content);
        jobjText.put("text", content+"을(를) 습득하셨네요~(우와)\n" +
                content+"을(를) 어디서 발견하셨나요?? \n" +
                "상세할 수록 좋습니다.(씨익)\n\n" + "분실물 등록을 취소 하시려면 \"취소\"를 입력해주세요~\n");
        jobjRes.put("message", jobjText);
        user.setDepth(35);
        userService.setDepth(user);

    }
    public void request_date(){
        lostService.setLost(user.getConvertId(),1,content);
        String content_ = lostService.getLost(user.getConvertId()).getContent();
        jobjText.put("text", content+"에서 습득하셨네요~(우와)\n" +
                content_+"을(를) 언제 발견하셨나요?? \n" +
                "예)2017년 09월 03일 17시~18시에 발견하였다면\n  \"20170903 17\"을 입력해주시면 됩니다.(좋아)\n" +
                "혹은 \"오늘 17\",\"어제 17\"와 같이 입력해주셔도 됩니다. \n\n" + "분실물 등록을 취소 하시려면 \"취소\"를 입력해주세요~\n");
        jobjRes.put("message", jobjText);
        user.setDepth(36);
        userService.setDepth(user);

    }
    public void request_putplace(){
        if(validity_check_date(1)) {
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
                String content_ = lostService.getLost(user.getConvertId()).getContent();
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
            String content_ = lostService.getLost(user.getConvertId()).getContent();
            jobjText.put("text","입력된 시간 값이 올바르지 않습니다.(훌쩍)\n\n"+content_+"을(를) 언제 발견하셨나요?? \n" +
                    "예)2017년 09월 03일 17시~18시에 발견하였다면\n  \"20170903 17\"을 입력해주시면 됩니다.(좋아)\n" +
                    "혹은 \"오늘 17\",\"어제 17\"와 같이 입력해주셔도 됩니다. \n\n" + "분실물 등록을 취소 하시려면 \"취소\"를 입력해주세요~\n");
            jobjRes.put("message", jobjText);
        }
    }

    public void request_img(){
        lostService.setLost(user.getConvertId(),3,content);
        String content_ = lostService.getLost(user.getConvertId()).getContent();
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
    }

    public void complete_add() throws IOException {
        String content_ = "";
        String user_key = user.getConvertId();
        if (content.equals("없음") || content.contains(".jpg")) {
            if (content.equals("없음")) content_ = "없음";
            else {
                content_ = Integer.toString(lostService.getId(user_key));
                ImageGet imageGet = new ImageGet(content, content_);
                imageGet.saveImage();
            }
            lostService.setLost(user_key, 4, content);
            Lost templost = new Lost();
            templost = lostService.getLost(user_key);
            jobjText.put("text",
                            templost.getDate_()+"시 에 "+ templost.getGet_place()+"에서 발견하신\n"+
                                    templost.getContent()+ "의 분실물 등록을 모두 하였습니다.(우와)\n" +
                                    "분실하신 분께 " +templost.getPut_place()+"에서 보관하고 있다고 전달하겠습니다. \n"+
                    "\n등록하시느라 고생 많으셨습니다.(오케이)" +
                    "\n등록된 분실물은 주인을 찾을 수 있도록 노력하겠습니다." +
                    "\n감사합니다.(최고)(최고)(최고)\n");
            jobjRes.put("message", jobjText);
            set_main_btn();
            user.setDepth(0);
            userService.setDepth(user);
        } else {
            content_ = lostService.getLost(user_key).getContent();
            jobjText.put("text", "잘못입력하셨습니다.\n" + content + "에서(가) 보관하고 있군요!!(우와)\n" +
                    content_ + "을(를) 에 대한 사진이 있으시다면 사진을 보내주세요!\n" +
                    "사진이 없으면 \"없음\"을 선택해주세요. \n\n" +
                    "분실물 등록을 취소 하시려면 \"취소\"를 입력해주세요~\n");
            jobjRes.put("message", jobjText);
            jobjRes.put("type", "buttons");
            ArrayList<String> btns = new ArrayList<>();
            btns.add("없음");
            btns.add("취소");
            jobjRes.put("buttons", btns);
        }
    }

    private boolean validity_check_date(int type){
        String date_="";
        if (!content.contains(" ")&& type==1) {
            return false;
        }
        String[] strArr = content.split(" ");
        if(type ==1 )// 등록시에
        {

            if (!isStringInt(strArr[1])) {
                return false;
            }
            int time_ = Integer.parseInt(strArr[1]);
            if (time_ < 0 && time_ > 24) {
                return false;
            }
        }
        if(!strArr[0].equals("어제")&&!strArr[0].equals("오늘")&&!isStringInt(strArr[0])){return false;}
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
            lostCancel();
            if(depth!=34&&depth!=33&&depth<40) {
                lostService.removeLost(user_key);
            }
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
            if (validity_check_date(2)){

                Calendar cal = new GregorianCalendar(Locale.KOREA);
                    cal.setTime(new Date());
                    SimpleDateFormat fm = new SimpleDateFormat("yyyyMMdd");
                    Lost lost = new Lost();
                    lost.setUser_key(user_key);
                    String strDate="";
                    if(content.equals("어제")){
                        cal.add(Calendar.DAY_OF_MONTH, -1); // 1일을 뺀다.
                        strDate = fm.format(cal.getTime());
                }
                else if(content.equals("오늘")) {
                    strDate = fm.format(cal.getTime());
                }else{
                    strDate=content;
                }
                lost.setDate_(strDate);
                //분실물을 DB에 추가한다. 그 이유는 이후에 사용하려고
                //앞으로 해야할 작업들은 controller를 간소화하고 service에 코딩하는 것이다.

                List<Lost> seek_result= lostService.seek_lostofdate(lost);
                String output_= "";
                if(seek_result.isEmpty()){
                    output_=strDate+"에 접수된 분실물은 없습니다.(훌쩍) \n" +
                            "다른 날의 분실물을 보고 싶으시면 날짜를 입력하세요"+
                            "예)2017년 09월 03일에 발견하였다면\n " +
                            "\"20170903\"을 입력해주시면 됩니다.(좋아)\n" +
                            "혹은 \"오늘\",\"어제\"와 같이 입력해주셔도 됩니다.\n\n" +
                            "분실물 찾기를 취소 하시려면 \"취소\"를 입력해주세요~\n";
                }else{
                    output_ = strDate+"에 분실하셨구나(훌쩍)\n" +
                        content+"에 분실물의 발견 장소와 항목입니다.\n\n";
                    int size_list = seek_result.size();
                    for(int i=0;i<size_list;i++){
                        output_+=Integer.toString(seek_result.get(i).getId())+"번 ";
                        output_+=seek_result.get(i).getGet_place()+ "에서 발견된";
                        output_+=seek_result.get(i).getContent()+" 입니다. \n";
                    }
                    output_ +="찾으시는 물건이 있다면 해당되는 번호를 입력해주세요.\n" +
                            "입력하시면 사진과 함께 어디에 보관되어 있는지 알려드리겠습니다.\n\n"+
                            "분실물 찾기를 취소 하시려면 \"취소\"를 입력해주세요~\n";
                }

                jobjText.put("text", output_);
                jobjRes.put("message", jobjText);
                user.setDepth(41);
                userService.setDepth(user);
            }
            else {
                jobjText.put("text", "날짜 정보를 잘못 입력 하셨습니다.\n\n\n"+"물건을 분실하셨나요?(훌쩍)\n" +
                        "분실물에 대한 정보를 알기 위해 간단한 몇가지 질문을 하겠습니다.\n" +
                        "언제 잃어 버리셨나요??\n" +
                        "예)2017년 09월 03일에 발견하였다면\n " +
                        "\"20170903\"을 입력해주시면 됩니다.(좋아)\n" +
                        "혹은 \"오늘\",\"어제\"와 같이 입력해주셔도 됩니다.\n\n" +
                        "분실물 찾기를 취소 하시려면 \"취소\"를 입력해주세요~\n");
                jobjRes.put("message", jobjText);
            }
        }
        else if(depth==41){
            if(isStringInt(content)){
                Lost templost = new Lost();
                templost = lostService.getLost(Integer.parseInt(content));
                if(templost.getContent()==""){
                    jobjText.put("text", "\n잘못입력하셨습니다.\n\n 초기화면으로 이동합니다.\n");
                    jobjRes.put("message", jobjText);
                }
                else{
                    String out_ = "";
                    out_ +=templost.getContent()+"는 현재 \n"
                            +templost.getPut_place()+"에서 보관중입니다.";
                    if (templost.getPicture().equals("없음")){
                        out_ += "\n\n아쉽게도 사진과 함께 올라오진 않았습니다.";
                        jobjText.put("text",out_);
                        jobjRes.put("message", jobjText);

                    }
                    else {
                        jobjText.put("text",out_);
                        JSONObject jobjPhoto = new JSONObject();
                        jobjPhoto.put("url",templost.getPicture());
                        jobjPhoto.put("width",640);
                        jobjPhoto.put("height",480);
                        jobjRes.put("message", jobjText);
                        jobjRes.put("photo", jobjPhoto);
                    }

                }
            }else {
                jobjText.put("text", "\n잘못입력하셨습니다.\n\n 초기화면으로 이동합니다.\n");
                jobjRes.put("message", jobjText);

            }
            user.setDepth(0);
            userService.setDepth(user);
            set_main_btn();
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

        if(jobjText.get("text")=="") {
            error_server();
            if(depth!=34&&depth!=33){
                lostService.removeLost(user_key);
            }
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
    private void error_server(){
        jobjText.put("text","서버 연결에 대한 지연으로 초기화면으로 이동합니다.(우와)\n\n" +
                "다른 서비스에 대해서 안내 받으시려면" +
                " \"시작하기\"를 입력해주세요(최고)");
        jobjRes.put("message",jobjText);
        set_main_btn();
        user.setDepth(0);
        userService.setDepth(user);
    }
    private void set_main_btn(){

        JSONObject josonKeyboard =new JSONObject();
        josonKeyboard.put("type", "buttons");
        ArrayList<String> btns = new ArrayList<>();
        btns.add("공지사항");
        btns.add("일정");
        btns.add("학교식당");
        btns.add("도서관");
        btns.add("강의평가");
        btns.add("분실물");
        btns.add("기타");
        josonKeyboard.put("buttons",btns);
        jobjRes.put("keyboard",josonKeyboard);

    }
    private void lostCancel(){
        jobjText.put("text","분실물 등록을 취소하였습니다~(허걱)\n\n" +
                "다른 서비스에 대해서 안내 받으시려면" +
                " \"시작하기\"를 입력해주세요(최고)");
        jobjRes.put("message",jobjText);
        set_main_btn();
        user.setDepth(0);
        userService.setDepth(user);
    }
}
