package kr.or.connect.chatbotserver.model;

public class Rank {
    private String menu;
    private Double score;

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Rank{" +
                "menu='" + menu + '\'' +
                ", score=" + score +
                '}';
    }
}
