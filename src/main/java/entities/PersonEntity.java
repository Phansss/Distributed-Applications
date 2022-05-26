package entities;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Person", schema = "hellodemo")
public class PersonEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "lastName")
    private String lastName;
    @Basic
    @Column(name = "password")
    private String password;
    @ManyToMany(mappedBy = "studentsInClass")
    private List<CourseEntity> followingCourses;
    @OneToMany(mappedBy = "madeBy")
    private List<CommentEntity> madeComments;

    public List<CourseEntity> getFollowingCourses() {
        return followingCourses;
    }

    public void setFollowingCourses(List<CourseEntity> followingCourses) {
        this.followingCourses = followingCourses;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<CommentEntity> getMadeComments() {
        return madeComments;
    }

    public void setMadeComments(List<CommentEntity> madeComments) {
        this.madeComments = madeComments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonEntity)) return false;
        PersonEntity that = (PersonEntity) o;
        return getId() == that.getId() && Objects.equals(getName(), that.getName()) && Objects.equals(getEmail(), that.getEmail()) && Objects.equals(getLastName(), that.getLastName()) && Objects.equals(getPassword(), that.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getEmail(), getLastName(), getPassword());
    }
}
