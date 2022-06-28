package entities;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.FacesConverter;
import jakarta.faces.convert.Converter;
import jakarta.persistence.*;

import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.List;
import java.util.Objects;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

enum Year {
    FIRST_YEAR,
    SECOND_YEAR,
    THIRD_YEAR,
    MASTER
}


@Entity
@Table(name = "Course", schema = "hellodemo")
@FacesConverter("CourseConverter")
public class CourseEntity implements Serializable, Converter, Comparable<CourseEntity> {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "courseId")
    private int courseId;
    @Basic
    @Column(name = "Name", nullable = false, unique = true)
    private String name;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "Persons_Follow_Courses", joinColumns = @JoinColumn(name = "courseId"), inverseJoinColumns = @JoinColumn(name = "personId"))
    private List<PersonEntity> followedByPersons;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "Professors_Teach_Courses", joinColumns = @JoinColumn(name = "courseId"), inverseJoinColumns = @JoinColumn(name = "professorId"))
    private List<ProfessorEntity> givenByProfessors;
    @Enumerated
    private Year yearOfCourse;

    @OneToMany
    @JoinTable(name = "Course_Has_Comments", joinColumns = @JoinColumn(name = "courseId"), inverseJoinColumns = @JoinColumn(name = "commentId"))
    private List<CommentEntity> comments;

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

    public List<PersonEntity> getFollowedByPersons() {
        return followedByPersons;
    }

    public List<ProfessorEntity> getGivenByProfessors() {
        return givenByProfessors;
    }

    public void setGivenByProfessors(List<ProfessorEntity> courseGivenBy) {
        this.givenByProfessors = courseGivenBy;
    }

    public Year getYearOfCourse() {
        return yearOfCourse;
    }

    public void setYearOfCourse(Year yearOfCourse) {
        this.yearOfCourse = yearOfCourse;
    }

    public List<CommentEntity> getComments() {
        return this.comments;
    }
    public void setComments(List<CommentEntity> comments) {
        this.comments = comments;
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
    public int compareTo(CourseEntity o) {
        return 0;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return value;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return "CourseEntity{" +
                "courseId=" + courseId +
                ", name='" + name + '\'' +
                ", personsInCourse=" + followedByPersons +
                ", courseGivenBy=" + givenByProfessors +
                ", yearOfCourse=" + yearOfCourse +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CourseEntity)) return false;
        CourseEntity that = (CourseEntity) o;
        return getCourseId() == that.getCourseId() && Objects.equals(getName(), that.getName()) && getYearOfCourse() == that.getYearOfCourse();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCourseId(), getName(), getYearOfCourse());
    }

}

