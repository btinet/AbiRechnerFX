# Softwaregenerierte SQL-Abfragen


## Personenverzeichnis

### Generierte SQL-Abfrage

````roomsql
SELECT 
    tutor.firstname AS tutorFirstname,
    tutor.lastname AS tutorLastname,
    
    SUM(exam.points * 4) AS summedExamPoints,
    
    pupil.id AS id,
    pupil.tutor_id AS tutorId,
    pupil.firstname AS firstname,
    pupil.lastname AS lastname,
    pupil.birth_date AS birthDate,
    pupil.exam_date AS examDate,
    pupil.course_points AS coursePoints 
    
FROM pupil

LEFT JOIN tutor
    ON (tutor.id = pupil.tutor_id)
    
LEFT JOIN exam
    ON (exam.pupil_id = pupil.id)
    
GROUP BY id
ORDER BY examDate DESC
````

### Zugrundeliegende Klasse

````java
public class Pupil extends Entity {

    @Join(entity = Tutor.class, column = "firstname", on = "tutorId")
    protected String tutorFirstname;
    
    @Join(entity = Tutor.class, column = "lastname", on = "tutorId")
    protected String tutorLastname;
    
    @InverseJoin(entity = Exam.class, column = "SUM(exam.points * 4)", on = "pupilId")
    protected Integer summedExamPoints;

    @ORM
    protected int id;
    
    @ManyToOne(entity = Tutor.class, origin = "id")
    protected Integer tutorId;
    
    @ORM
    protected String firstname;
    
    @ORM
    protected String lastname;
    
    @ORM
    protected String birthDate;
    
    @ORM
    protected String examDate;
    
    @ORM
    protected Integer coursePoints;
    
}
````

### Benutzerdefinierte Repository-Query

````java
public class PupilRepository extends Repository<Pupil> {

    public ArrayList<Pupil> findAllGroupedByPupil() {

        HashMap<String, String> order = new HashMap<>();
        order.put("examDate", "DESC");

        return this.createQueryBuilder()
                .selectOrm()
                .groupBy("id")
                .orderBy(order)
                .getQuery()
                .getResult()
                ;
    }
    
}
````

## Liste der Tutoren

````roomsql
SELECT
    tutor.id AS id,
    tutor.firstname AS firstname,
    tutor.lastname AS lastname 
    
FROM tutor
````

## Lister der Prüfungsfächer

````roomsql
SELECT
    school_subject.id AS id,
    school_subject.label AS label 

FROM school_subject

ORDER BY label ASC
````

## Ausgabe der Prüfungsübersicht

````roomsql
SELECT
    school_subject.label AS schoolSubject,
    
    exam.id AS id,
    exam.pupil_id AS pupilId,
    exam.school_subject_id AS schoolSubjectId,
    exam.exam_number AS examNumber,
    exam.points AS points 
    
FROM exam 

LEFT JOIN school_subject
    ON (school_subject.id = exam.school_subject_id)
    
WHERE exam.pupil_id = ?

ORDER BY examNumber ASC
````