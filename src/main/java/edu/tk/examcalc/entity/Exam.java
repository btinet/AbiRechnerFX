package edu.tk.examcalc.entity;

import edu.tk.db.model.Entity;
import edu.tk.examcalc.repository.SchoolSubjectRepository;

public class Exam extends Entity {

    public String schoolSubject;

    protected int id;
    protected Integer pupilId;
    protected Integer schoolSubjectId;
    protected Integer examNumber;
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

}
