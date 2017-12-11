package kr.or.connect.chatbotserver.service;

import kr.or.connect.chatbotserver.dao.PhoneNumberOfUniversityDAO;
import kr.or.connect.chatbotserver.model.PhoneNumberOfUniversity;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhoneNumberOfUniversityService {


    @Autowired
    private PhoneNumberOfUniversityDAO universityOfNumberDAO;

    public PhoneNumberOfUniversityService(){
    }

    public JSONObject infomPhoneNumber(String contetns){

        JSONObject jsonObject = new JSONObject();
        JSONObject jobjText= new JSONObject();
        JSONObject jobjRes = new JSONObject();

        List<PhoneNumberOfUniversity> searching_result;
        searching_result=universityOfNumberDAO.searchingPhoneNumber(contetns);
        int lenght_number = searching_result.size();

        String response_result="";
        if(lenght_number ==0){
            response_result="\""+contetns+"\"에 관련된 전화번호 정보가 없습니다.(곤란)\n\n\n" +
                    "다시 검색하시려면 검색어를 입력해주세요.. \n\n" +
                    "이전 메뉴로 돌아가고 싶으면 취소를 입력해주세요.";
            jsonObject.put("depth",100);
        }else{
            response_result = "\""+contetns +"\"에 관련된 전화번호 정보가 "+lenght_number+"건 있습니다.(방긋)\n\n\n";
            for(int i=0;i<lenght_number;i++){
                response_result += searching_result.get(i).getAffiliation()+" "+searching_result.get(i).getPosition()+" "+searching_result.get(i).getName()+"\n-----------------------\n"+searching_result.get(i).getAssignedTask()+"+\n-----------------------\n"+searching_result.get(i).getNumber()+"\n\n";
            }
            jsonObject.put("depth",0);
        }
        jobjText.put("text",response_result);
        jobjRes.put("message",jobjText);
        jsonObject.put("res",jobjRes);
        return jsonObject;
    }

}
