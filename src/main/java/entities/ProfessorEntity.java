package entities;

import ejb.SingletonCacheBean;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Professor", schema = "hellodemo")
public class ProfessorEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "professorId")
    private int id;
    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "surname")
    private String surname;

    @Column(name = "rating")
    private int rating;

    @Column(name = "amountOfRatings")
    private int amountOfRatings;

    @ManyToMany(mappedBy = "givenByProfessors")
    private List<CourseEntity> givesCourses;

    @OneToMany
    @JoinTable(name = "Professor_Has_Comments", joinColumns = @JoinColumn(name = "professorId"), inverseJoinColumns =
                    @JoinColumn(name = "commentId"))
    private List<CommentEntity> commentsAbout;

    @Lob
    private byte[] picture;

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

    public List<CourseEntity> getGivesCourses() {
        return givesCourses;
    }

    public void setGivesCourses(List<CourseEntity> profGivesCourses) {
        this.givesCourses = profGivesCourses;
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
        return id == that.id && rating == that.rating && amountOfRatings == that.amountOfRatings && Objects.equals(name, that.name) && Objects.equals(givesCourses, that.givesCourses) && Objects.equals(commentsAbout, that.commentsAbout);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, rating, amountOfRatings, givesCourses, commentsAbout);
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public byte[] getPicture() {
        SingletonCacheBean singleton = new SingletonCacheBean();
        return (byte[]) singleton.getFromCache((long) id);
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }
}
