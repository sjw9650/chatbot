package kr.or.connect.chatbotserver.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="lost_things")
public class Lost implements Serializable {

    private static final long serialVersionUID = 1L;

    public Lost(){}

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="lost_things_id")
    private int id;

    @Column(name="date")
    private String date_;

    @Column(name="lost_things")
    private String content;

    @Column(name="put_place")
    private String put_place;

    @Column(name="get_place")
    private String get_place;

    @Column(name="picture")
    private String picture;

    @Column(name="users_user_key")
    private String user_key;


    public int getId() {
        return id;
    }
    public String getContent() {
        return content;
    }
    public String getDate_() {
        return date_;
    }

    public String getGet_place() {
        return get_place;
    }
    public String getPut_place() {
        return put_place;
    }

    public String getPicture() {
        return picture;
    }
    public String getUser_key() {
        return user_key;
    }

    public void setUser_key(String user_key) {
        this.user_key = user_key;
    }

    public void setGet_place(String get_place) {
        this.get_place = get_place;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate_(String date_) {
        this.date_ = date_;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setPut_place(String put_place) {
        this.put_place = put_place;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

}
