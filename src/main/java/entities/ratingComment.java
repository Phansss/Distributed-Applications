package entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@DiscriminatorValue("R")
@Table(name = "ratingComment")
public class ratingComment extends CommentEntity {
    @Basic
    @Column(name = "Comment_Rating")
    private int rating;

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ratingComment that = (ratingComment) o;
        return rating == that.rating;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), rating);
    }
}
