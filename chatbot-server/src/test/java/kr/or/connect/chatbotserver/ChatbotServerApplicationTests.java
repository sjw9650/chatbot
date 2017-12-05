package kr.or.connect.chatbotserver;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChatbotServerApplicationTests {

	@Test
	public void contextLoads() {
        Calendar cal = new GregorianCalendar(Locale.KOREA);
        cal.setTime(new Date());
        SimpleDateFormat fm = new SimpleDateFormat("yyyyMMdd");
        System.out.println(cal);
	}

}
