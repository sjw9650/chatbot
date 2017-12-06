package kr.or.connect.chatbotserver.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="number")
public class PhoneNumberOfUniversity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="idnumber")
    int idnumber;

    @Column(name="affiliation")
    String affiliation;
    @Column(name="position")
    String position;
    @Column(name="name")
    String name;
    @Column(name="assigned_task")
    String assignedTask;
    @Column(name="number")
    String number;

    public PhoneNumberOfUniversity(){
        idnumber=0;
        affiliation="";
        position="";
        name="";
        assignedTask="";
        number= "";
    }


    public int getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(int idnumber) {
        this.idnumber = idnumber;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAssignedTask() {
        return assignedTask;
    }

    public void setAssignedTask(String assignedTask) {
        this.assignedTask = assignedTask;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

}
