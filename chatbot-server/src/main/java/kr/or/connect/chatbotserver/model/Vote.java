package kr.or.connect.chatbotserver.model;

import java.io.Serializable;

public class Vote implements Serializable {

    private static final long serialVersionUID = 1L;

    private String menu;

    private int score;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
