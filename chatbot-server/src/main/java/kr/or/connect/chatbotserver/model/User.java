package kr.or.connect.chatbotserver.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="user_key")
    private String user_key;

    @Column(name="depth")
    private int depth;


    public String getUser_key() {
        return user_key;
    }

    public void setUser_key(String user_key) {
        this.user_key = user_key;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int Depth) {
        this.depth = depth;
    }

}
