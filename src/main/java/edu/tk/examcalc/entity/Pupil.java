package edu.tk.examcalc.entity;

import edu.tk.db.model.*;
import edu.tk.examcalc.repository.ExamRepository;
import edu.tk.examcalc.repository.TutorRepository;

import java.util.ArrayList;

public class Pupil extends Entity {

    @Join(entity=Tutor.class, column = "firstname", on = "tutorId")
    public String tutorFirstname;
    @Join(entity=Tutor.class, column = "lastname", on = "tutorId")
    public String tutorLastname;

    @ORM(column ="id")
    protected int id;
    @ManyToOne(entity=Tutor.class, origin = "id")
    protected Integer tutorId;
    @ORM(column ="firstname")
    protected String firstname;
    @ORM(column ="lastname")
    protected String lastname;
    @ORM(column ="birthDate")
    protected String birthDate;
    @ORM(column ="examDate")
    protected String examDate;
    @ORM(column ="coursePoints")
    protected Integer coursePoints;
    private final TutorRepository tutorRepository = new TutorRepository();

    public String toString() {
        return this.firstname + " " + this.lastname;
    }

    public int getId() {
        return id;
    }

    public Integer getTutorId() {
        return tutorId;
    }

    public void setTutorId(Integer tutorId) {
        this.tutorId = tutorId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }

    public Integer getCoursePoints() {
        return coursePoints;
    }

    public void setCoursePoints(Integer coursePoints) {
        this.coursePoints = coursePoints;
    }

    public Tutor getTutor() {
        if(tutorId != null) {
            return tutorRepository.find(tutorId);
        }
        return null;
    }

    public ArrayList<Exam> getExams() {
        ExamRepository examRepository = new ExamRepository();
        return examRepository.findBy(new Condition("pupil_id",String.valueOf(id)).getMap());
    }

}
