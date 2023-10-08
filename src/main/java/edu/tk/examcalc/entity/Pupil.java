package edu.tk.examcalc.entity;

import edu.tk.db.model.Condition;
import edu.tk.db.model.Entity;
import edu.tk.db.model.ORM;
import edu.tk.examcalc.repository.ExamRepository;
import edu.tk.examcalc.repository.TutorRepository;

import java.util.ArrayList;

public class Pupil extends Entity {

    public String tutorFirstname;
    public String tutorLastname;

    @ORM(label="id")
    protected int id;
    @ORM(label="tutorId")
    protected Integer tutorId;
    @ORM(label="firstname")
    protected String firstname;
    @ORM(label="lastname")
    protected String lastname;
    @ORM(label="birthDate")
    protected String birthDate;
    @ORM(label="examDate")
    protected String examDate;
    @ORM(label="coursePoints")
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
