package entities;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "Comment_Type")
@Table(name = "Comments", schema = "hellodemo")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentId")
    private int id;

    @ManyToOne
    @JoinColumn(name = "MadeById")
    private PersonEntity madeBy;

    @ManyToOne
    @JoinColumn(name = "isAboutId")
    private ProfessorEntity isAbout;

    @Basic
    @Column(name = "Name", nullable = false, unique = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date timePosted;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PersonEntity getMadeBy() {
        return madeBy;
    }

    public void setMadeBy(PersonEntity madeBy) {
        this.madeBy = madeBy;
    }

    public ProfessorEntity getIsAbout() {
        return isAbout;
    }

    public void setIsAbout(ProfessorEntity isAbout) {
        this.isAbout = isAbout;
    }

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
        return id == that.id && Objects.equals(madeBy, that.madeBy) && Objects.equals(isAbout, that.isAbout) && Objects.equals(timePosted, that.timePosted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, madeBy, isAbout, timePosted);
    }
}

