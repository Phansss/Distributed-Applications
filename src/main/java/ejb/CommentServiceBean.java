package ejb;

import entities.*;
import jakarta.inject.Inject;
import jakarta.jms.JMSException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@jakarta.ejb.Stateless(name = "CommentEJB")
public class CommentServiceBean {

    @PersistenceContext(unitName = "DADemoPU")
    EntityManager em;


    public CommentServiceBean() {
    }

    public textComment createTextComment(String text) {
        System.out.println("Creatinng text comment in DB: " + text);
        textComment newComment = new textComment();
        newComment.setText(text);
        em.persist(newComment);
        return newComment;
    }

    public ratingComment createRatingComment(int rating) {

        ratingComment newComment = new ratingComment();
        newComment.setRating(rating);
        em.persist(newComment);
        return newComment;
    }

    public ratingTextComment createRatingTextComment(int rating, String text) {

        ratingTextComment newComment = new ratingTextComment();
        newComment.setRating(rating);
        newComment.setText(text);
        em.persist(newComment);
        return newComment;
    }

    public void addCommentToProfessor(CommentEntity comment, ProfessorEntity professor) {
        em.find(ProfessorEntity.class, professor.getId());
        professor.getCommentsAbout().add(comment);

    }

}
