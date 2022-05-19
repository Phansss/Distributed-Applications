package entities;

import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;

enum Year {
    FIRST_YEAR,
    SECOND_YEAR,
    THIRD_YEAR,
    MASTER;
}

@Entity
@Table(name = "Course", schema = "hellodemo")
public class CourseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int courseId;
    @Basic
    @Column(name = "Name", nullable = false, unique = true)
    private String name;
    @ManyToMany
    @JoinTable(name = "jnd_course_person", joinColumns = @JoinColumn(name = "course_fk"), inverseJoinColumns = @JoinColumn(name = "person_fk"))
    private List<PersonEntity> studentsInClass;
    @ManyToMany
    @JoinTable(name = "jnd_course_professor", joinColumns = @JoinColumn(name = "course_fk"), inverseJoinColumns = @JoinColumn(name = "professor_fk"))
    private List<ProfessorEntity> courseGivenBy;
    @Enumerated
    private Year yearOfCourse;

    public int getId() {
        return courseId;
    }

    public void setId(int id) {
        this.courseId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public List<PersonEntity> getStudentsInClass() {
        return studentsInClass;
    }

    public void setStudentsInClass(List<PersonEntity> studentsInClass) {
        this.studentsInClass = studentsInClass;
    }

    public List<ProfessorEntity> getCourseGivenBy() {
        return courseGivenBy;
    }

    public void setCourseGivenBy(List<ProfessorEntity> courseGivenBy) {
        this.courseGivenBy = courseGivenBy;
    }

    public Year getYearOfCourse() {
        return yearOfCourse;
    }

    public void setYearOfCourse(Year yearOfCourse) {
        this.yearOfCourse = yearOfCourse;
    }

    public int getEnumAsInt(){
        switch (yearOfCourse){

            case FIRST_YEAR:
                return 1;
            case SECOND_YEAR:
                return 2;
            case THIRD_YEAR:
                return 3;
            case MASTER:
                return 4;
        }
        return 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseEntity that = (CourseEntity) o;
        return courseId == that.courseId && Objects.equals(name, that.name) && Objects.equals(studentsInClass, that.studentsInClass) && Objects.equals(courseGivenBy, that.courseGivenBy) && yearOfCourse == that.yearOfCourse;
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, name, studentsInClass, courseGivenBy, yearOfCourse);
    }
}
