package entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@DiscriminatorValue("T")
@Table(name = "textComment")
public class textComment extends CommentEntity {
    @Basic
    @Column(name = "Comment_Text")
    private String text;

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
        textComment that = (textComment) o;
        return Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), text);
    }
}
