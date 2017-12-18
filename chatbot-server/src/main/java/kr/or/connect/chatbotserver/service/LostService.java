package kr.or.connect.chatbotserver.service;

import kr.or.connect.chatbotserver.dao.LostDAO;
import kr.or.connect.chatbotserver.model.Lost;
import org.json.simple.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class LostService {

    @Autowired
    private LostDAO lostDAO;


    public LostService(){
    }

    public void lostCancel(String user_key, int depth){

        if(depth!=34&&depth!=33&&depth<40) {
            int id = lostDAO.getId(user_key);
            Lost lost= new Lost();
            lost = lostDAO.getLost(id);
            lostDAO.removeLost(lost);
        }
    }

    public JSONObject lost_(String user_key, int depth, String content) throws IOException {


        JSONObject response = new JSONObject();
        String content_="";
        JSONObject jobjText= new JSONObject();
        JSONObject jobjRes = new JSONObject();

        if(depth==33 ){
            if(content.equals("등록")) {
                jobjText.put("text", "분실물을 습득하셨네요~(우와)\n" +
                    "분실물에 대한 정보를 알기 위해 간단한 몇가지 질문을 하겠습니다.\n" +
                    "주우신 물건이 어떤(궁금) 건가요??\n" +
                    "자세히 묘사해주시면 감사하겠습니다.(반함)\n\n" +
                    "분실물 등록을 취소 하시려면 \"취소\"를 입력해주세요~\n");
                jobjRes.put("message", jobjText);

                response.put("depth",34);
                response.put("res",jobjRes);
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

                response.put("depth",40);
                response.put("res",jobjRes);
            }
        }
        else if(depth==40){
            if (validity_check_date(2,content)){

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

                List<Lost> seek_result= seek_lostofdate(lost);
                String output_= "";
                if(seek_result.isEmpty()){
                    output_=strDate+"에 접수된 분실물은 없습니다.(훌쩍) \n" +
                            "다른 날의 분실물을 보고 싶으시면 날짜를 입력하세요"+
                            "예)2017년 09월 03일에 발견하였다면\n " +
                            "\"20170903\"을 입력해주시면 됩니다.(좋아)\n" +
                            "혹은 \"오늘\",\"어제\"와 같이 입력해주셔도 됩니다.\n\n" +
                            "분실물 찾기를 취소 하시려면 \"취소\"를 입력해주세요~\n";
                    response.put("depth",depth);
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

                    response.put("depth",41);
                }

                jobjText.put("text", output_);
                jobjRes.put("message", jobjText);
                response.put("res",jobjRes);

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

                response.put("depth",depth);
                response.put("res",jobjRes);
            }
        }
        else if(depth==41){
            if(isStringInt(content)){
                Lost templost = new Lost();
                templost = getLost(Integer.parseInt(content));
                if(templost==null){
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
                        jobjText.put("photo", jobjPhoto);
                        jobjRes.put("message", jobjText);
                    }

                }
            }else {
                jobjText.put("text", "\n잘못입력하셨습니다.\n\n 초기화면으로 이동합니다.\n");
                jobjRes.put("message", jobjText);
            }

            response.put("depth",0);
            response.put("res",jobjRes);
        }
        else if(depth==34){
            addLost(user_key,content);
            jobjText.put("text", content+"을(를) 습득하셨네요~(우와)\n" +
                    content+"을(를) 어디서 발견하셨나요?? \n" +
                    "상세할 수록 좋습니다.(씨익)\n\n" + "분실물 등록을 취소 하시려면 \"취소\"를 입력해주세요~\n");
            jobjRes.put("message", jobjText);

            response.put("depth",35);
            response.put("res",jobjRes);

        }
        else if(depth==35){
            setLost(user_key,1,content);
            content_ = getLost(user_key).getContent();
            jobjText.put("text", content+"에서 습득하셨네요~(우와)\n" +
                    content_+"을(를) 언제 발견하셨나요?? \n" +
                    "예)2017년 09월 03일 17시~18시에 발견하였다면\n  \"20170903 17\"을 입력해주시면 됩니다.(좋아)\n" +
                    "혹은 \"오늘 17\",\"어제 17\"와 같이 입력해주셔도 됩니다. \n\n" + "분실물 등록을 취소 하시려면 \"취소\"를 입력해주세요~\n");
            jobjRes.put("message", jobjText);

            response.put("depth",36);
            response.put("res",jobjRes);
        }else if(depth==36){
            if(validity_check_date(1,content)) {
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
                setLost(user_key,2,date_);
                content_ = getLost(user_key).getContent();
                jobjText.put("text", date_+"시 쯤에 습득하셨네요~(우와)\n" +
                        content_+"을(를) 맡기신 곳이 있다면 어디에 맡기셨나요??\n" +
                        "혹은 가지고 계시다면 자신이 누구인지 알려주실 수 있으신가요??\n\n" +
                        "분실물 등록을 취소 하시려면 \"취소\"를 입력해주세요~\n");
                jobjRes.put("message", jobjText);

                response.put("depth",37);
                response.put("res",jobjRes);

            }
            else {
                // 입력값이 이상하면
                content_ = getLost(user_key).getContent();
                jobjText.put("text","입력된 시간 값이 올바르지 않습니다.(훌쩍)\n\n"+content_+"을(를) 언제 발견하셨나요?? \n" +
                        "예)2017년 09월 03일 17시~18시에 발견하였다면\n  \"20170903 17\"을 입력해주시면 됩니다.(좋아)\n" +
                        "혹은 \"오늘 17\",\"어제 17\"와 같이 입력해주셔도 됩니다. \n\n" + "분실물 등록을 취소 하시려면 \"취소\"를 입력해주세요~\n");
                jobjRes.put("message", jobjText);


                response.put("depth",depth);
                response.put("res",jobjRes);
            }
        }else if(depth==37){
            setLost(user_key,3,content);
            content_ =getLost(user_key).getContent();
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

            response.put("depth",38);
            response.put("res",jobjRes);
        }else if(depth==38) {
            if (content.equals("없음") || content.contains(".jpg")) {
                if (content.equals("없음")) content_ = "없음";
                else {
                    content_ = Integer.toString(getId(user_key));
                }
                setLost(user_key, 4, content);
                Lost templost = new Lost();
                templost = getLost(user_key);
                jobjText.put("text",
                        templost.getDate_()+"시 에 "+ templost.getGet_place()+"에서 발견하신\n"+
                                templost.getContent()+ "의 분실물 등록을 모두 하였습니다.(우와)\n" +
                                "분실하신 분께 " +templost.getPut_place()+"에서 보관하고 있다고 전달하겠습니다. \n"+
                                "\n등록하시느라 고생 많으셨습니다.(오케이)" +
                                "\n등록된 분실물은 주인을 찾을 수 있도록 노력하겠습니다." +
                                "\n감사합니다.(최고)(최고)(최고)\n");
                jobjRes.put("message", jobjText);

                response.put("depth",0);
                response.put("res",jobjRes);
            } else {
                content_ = getLost(user_key).getContent();
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


                response.put("depth",depth);
                response.put("res",jobjRes);
            }
        }

        if(jobjText.get("text")=="") {
            jobjText.put("text","서버에 오류가 발생하여 초기화면으로 이동합니다.");
            jobjRes.put("message", jobjText);

            response.put("depth",0);
            response.put("res",jobjRes);

            if(depth!=34&&depth!=33){
                removeLost(user_key);
            }
        }
        return response;
    }

    private boolean validity_check_date(int type,String content){
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


    public void addLost(String user_key,String thing){
        Lost lost = new Lost();
        lost.setContent(thing);
        lost.setUser_key(user_key);
        lostDAO.addLost(lost);
    }
    public void setLost(String user_key,int index,String content){
        int id = lostDAO.getId(user_key);
        Lost lost= new Lost();
        lost = lostDAO.getLost(id);
        if(index == 1){
            lost.setGet_place(content);
        }else if(index == 2){
            lost.setDate_(content);
        }
        else if(index ==3 ){
            lost.setPut_place(content);
        }
        else if(index ==4 ){
            lost.setCompleted(1);
            lost.setPicture(content);
        }
        lostDAO.setLost(lost);
    }

    public Lost getLost(String user_key){
        int id = lostDAO.getId(user_key);
        Lost lost= new Lost();
        lost = lostDAO.getLost(id);
        return lost;
    }

    public Lost getLost(int id){
        Lost lost= new Lost();
        lost = lostDAO.getLost(id);
        return lost;
    }
    public int getId(String user_key){
        int id = lostDAO.getId(user_key);
        return id;
    }

    public void removeLost(String user_key){
        int id = lostDAO.getId(user_key);
        Lost lost= new Lost();
        lost = lostDAO.getLost(id);
        lostDAO.removeLost(lost);
    }

    public static boolean isStringInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public List<Lost> seek_lostofdate(Lost lost){
        List <Lost> lostList = lostDAO.seek_lostofday(lost);


        System.out.println("여기는 돼 !!");

        int sizeoflist = lostList.size();

        List <Lost> returnList=new ArrayList<>();

        for(int i=0;i<sizeoflist;i++){
            Lost temp = lostList.get(i);
            if(temp.getCompleted()==1){
                returnList.add(temp);
            }
        }
        return returnList;
    }
}