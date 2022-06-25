package entities;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "Comment_Type")
@Table(name = "Comment", schema = "hellodemo")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentId")
    private int id;

  /*  @ManyToOne
    @JoinColumn(name = "personId")
    private PersonEntity person;

    @ManyToOne
    @JoinColumn(name = "professorId")
    private ProfessorEntity professor;
*/

    @Basic
    @Column(name = "Date", nullable = false, unique = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date timePosted;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /*public PersonEntity getPerson() {
        return person;
    }

    public void setPerson(PersonEntity madeBy) {
        this.person = madeBy;
    }

    public ProfessorEntity getProfessor() {
        return professor;
    }

    public void setProfessor(ProfessorEntity isAbout) {
        this.professor = isAbout;
    }*/

    public Date getTimePosted() {
        return timePosted;
    }

    public void setTimePosted(Date timePosted) {
        this.timePosted = timePosted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentEntity that = (CommentEntity) o;
        return id == that.id && Objects.equals(timePosted, that.timePosted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, timePosted);
    }
}

