package kr.or.connect.chatbotserver.model;

import javax.persistence.*;

@Entity
@Table(name="lecture_information")
public class LectureInformation {
    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="lecture_information_id")
    int lectureInformationId;

    @Column(name="professor")
    String professor;

    @Column(name="lecture")
    String lecture;

    @Column(name="stars")
    int stars;

    @Column(name="review")
    String review;

    @Column(name="completed")
    int completed;

    @Column(name="users_convertid")
    String userId;



    public int getLectureInformationId() {
        return lectureInformationId;
    }

    public void setLectureInformationId(int lectureInformationId) {
        this.lectureInformationId = lectureInformationId;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getLecture() {
        return lecture;
    }

    public void setLecture(String lecture) {
        this.lecture = lecture;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getReview() {
        return review;
    }
    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }
    public void setReview(String review) {
        this.review = review;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }



}
