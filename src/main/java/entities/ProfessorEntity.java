package entities;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Professor", schema = "hellodemo")
public class ProfessorEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "rating")
    private int rating;
    @Basic
    @Column(name = "amountOfRatings")
    private int amountOfRatings;
    @ManyToMany(mappedBy = "courseGivenBy")
    private List<CourseEntity> profGivesCourses;

    @OneToMany
    private List<CommentEntity> commentsAbout;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getAmountOfRatings() {
        return amountOfRatings;
    }

    public void setAmountOfRatings(int amountOfRatings) {
        this.amountOfRatings = amountOfRatings;
    }

    public List<CourseEntity> getProfGivesCourses() {
        return profGivesCourses;
    }

    public void setProfGivesCourses(List<CourseEntity> profGivesCourses) {
        this.profGivesCourses = profGivesCourses;
    }

    public List<CommentEntity> getCommentsAbout() {
        return commentsAbout;
    }

    public void setCommentsAbout(List<CommentEntity> commentsAbout) {
        this.commentsAbout = commentsAbout;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfessorEntity that = (ProfessorEntity) o;
        return id == that.id && rating == that.rating && amountOfRatings == that.amountOfRatings && Objects.equals(name, that.name) && Objects.equals(profGivesCourses, that.profGivesCourses) && Objects.equals(commentsAbout, that.commentsAbout);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, rating, amountOfRatings, profGivesCourses, commentsAbout);
    }
}
