package edu.tk.examcalc.entity;

import edu.tk.db.model.Join;
import edu.tk.db.model.ManyToOne;
import edu.tk.db.model.ORM;
import edu.tk.db.model.Entity;
import edu.tk.examcalc.repository.ExamRepository;
import edu.tk.examcalc.repository.SchoolSubjectRepository;

public class Exam extends Entity {

    @Join(entity=SchoolSubject.class, column = "label", on = "schoolSubjectId")
    public String schoolSubject;

    @ORM
    protected int id;
    @ManyToOne(entity=Pupil.class, origin = "id")
    protected Integer pupilId;
    @ManyToOne(entity=SchoolSubject.class, origin = "id")
    protected Integer schoolSubjectId;
    @ORM
    protected Integer examNumber;
    @ORM
    protected Integer points;
    public int getId() {
        return id;
    }

    public Integer getPupilId() {
        return pupilId;
    }

    public void setPupilId(Integer pupilId) {
        this.pupilId = pupilId;
    }

    public Integer getSchoolSubjectId() {
        return schoolSubjectId;
    }

    public void setSchoolSubjectId(Integer schoolSubjectId) {
        this.schoolSubjectId = schoolSubjectId;
    }

    public Integer getExamNumber() {
        return examNumber;
    }

    public void setExamNumber(Integer examNumber) {
        this.examNumber = examNumber;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public SchoolSubject getSchoolSubject() {
        SchoolSubjectRepository subjectRepository = new SchoolSubjectRepository();
        return subjectRepository.find(schoolSubjectId);
    }

    public Integer getSummedExamPoints() {
        ExamRepository examRepository = new ExamRepository();
        return examRepository.sumExamPoints(this.pupilId);
    }
}
