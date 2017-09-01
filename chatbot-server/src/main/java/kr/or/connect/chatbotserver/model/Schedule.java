package kr.or.connect.chatbotserver.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="schedules")
public class Schedule implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="schdule_index")
    private int schduleId;
	
	@Column(name="user_key")
    private String userKey;
	
	@Column(name="content")
    private String content;
	
	@Column(name="date")	
	private String date;

	public int getSchduleId() {
		return schduleId;
	}

	public void setSchduleId(int schduleId) {
		this.schduleId = schduleId;
	}

	public String getUserKey() {
		return userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
}
