package kr.or.connect.chatbotserver.service;

import kr.or.connect.chatbotserver.dao.PhoneNumberOfUniversityDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhoneNumberOfUniversityService {


    @Autowired
    private PhoneNumberOfUniversityDAO universityOfNumberDAO;

    public PhoneNumberOfUniversityService(){
    }


}
