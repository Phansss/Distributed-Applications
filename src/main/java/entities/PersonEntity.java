package entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Person", schema = "hellodemo")
public class PersonEntity implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "personId")
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
    @ManyToMany(mappedBy = "followedByPersons", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<CourseEntity> subscribedCourses;

    @OneToMany
    @JoinTable(name = "Person_Posted_Comments", joinColumns = @JoinColumn(name = "personId"), inverseJoinColumns =
    @JoinColumn(name = "commentId"))
    private List<CommentEntity> comments;

    public List<CourseEntity> getSubscribedCourses() {
        return subscribedCourses;
    }

    public void setSubscribedCourses(List<CourseEntity> followingCourses) {
        this.subscribedCourses = followingCourses;
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

    public List<CommentEntity> getComments() {
        return comments;
    }

    public void setComments(List<CommentEntity> madeComments) {
        this.comments = madeComments;
    }


    /**
     * @param course
     * @post  following courses contains a copy of a pointer to where course points.
     */
    public  void addCourse(CourseEntity course) {
        this.subscribedCourses.add(course);
    }

    /**
     * @param course
     * @post if the list contained the element, it is removed from the list. Otherwise, nothing happens.
     */
    public  void removeCourse(CourseEntity course) {
        boolean removed = this.subscribedCourses.remove(course);
        if (!removed) {
            throw new EntityNotFoundException("The course '" + course.getName() + "' could not be removed because " +
                    "it was not found in the list of subscribedCourses of the Person: '" + this.getName() +"'.");
        }
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
