package entities;

import jakarta.persistence.*;

import javax.naming.Name;
import java.util.Objects;

@Entity
@DiscriminatorValue("RT")
@Table(name = "ratingTextComment")
public class ratingTextComment extends CommentEntity {
    @Basic
    @Column(name = "Comment_Rating")
    private int rating;
    @Basic
    @Column(name = "Comment_Text")
    private String text;

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ratingTextComment that = (ratingTextComment) o;
        return rating == that.rating && Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), rating, text);
    }
}
