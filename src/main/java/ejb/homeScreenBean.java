package ejb;

import entities.*;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.PostActivate;
import jakarta.ejb.Stateful;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.*;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.*;
import jakarta.transaction.RollbackException;
import org.primefaces.event.RateEvent;
import org.primefaces.model.menu.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Logger;

// TODO: MAKE QUERYING ALL COURSES INTO A STATELESS BEAN

@Named
@SessionScoped
public class  homeScreenBean implements Serializable {

    @Inject

    /*Returns the javax.transaction.UserTransaction interface to demarcate transactions.
    Only session beans with bean-managed transaction (BMT) can use this method.
    ALTERNATIE = CMT*/
    UserTransaction ut;

    ArrayList<ratingComment> RCommentsToShow;
    ArrayList<textComment> TCommentsToShow;
    ArrayList<ratingTextComment> RTCommentsToShow;

    private ArrayList<String> selectedCoursesAsString;
    private List<String> allProfessorNames;
    private List<ProfessorEntity> allProfessors;
    private List<CourseEntity> selectedCoursesAsCourses;
    private List<String> coursesAsString;
    private List<CourseEntity> coursesAsCourses;
    private MenuModel menumodel;
    private List<CommentEntity> commentsToShow;
    private PersonEntity loggedInUser;
    private int userId;
    int count = 0; // PLEASE DELETE
    private String text1;
    private Integer rating;
    private String chosenProfessor;
    private String[] selectedCoursesAsStringArray;

    private Integer test = 1;

    public homeScreenBean() {
        System.out.println("Print Creating a homeScreenBean");

        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        userId = (int) session.getAttribute("user");

        startup();
    }

    //Opgeroepen van zodra op de add knop wordt gedrukt. Rating = 1_5, text is comment
    public void printRating()  {


        System.out.println("Print Rating: " + rating + ". Text1: " + text1 + ". Professor: " + chosenProfessor);

        String commentType;
        Integer chosenProfId = 0;

        if(rating == null){
            commentType = "T";
        }
        else if (Objects.equals(text1, "")) {
            commentType = "R";
        } else {
            commentType = "RT";
        }

        for (ProfessorEntity p: allProfessors
        ) {
            if(p.getName().equals(chosenProfessor)){
                chosenProfId = p.getId();
            }
        }

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("DADemoPU");
        EntityManager new_em = factory.createEntityManager();

        try {
            ut.begin();

            new_em.joinTransaction();
            new_em.createNativeQuery("INSERT INTO Comments (Comment_Type, Name, isAboutId, MadeById) VALUES (?,?,?,?)")
                    .setParameter(1, commentType)
                    .setParameter(2, LocalDateTime.now())
                    .setParameter(3, chosenProfId)
                    .setParameter(4, userId)
                    .executeUpdate();

            new_em.createNativeQuery("INSERT INTO Professor_Comments (ProfessorEntity_id, commentsAbout_commentId) VALUES (?, (SELECT MAX(commentId) FROM Comments))")
                    .setParameter(1, chosenProfId)
                    .executeUpdate();

            String query;
            switch (commentType){
                case "RT":
                    new_em.createNativeQuery("INSERT INTO ratingTextComment(commentId, Comment_Rating, Comment_Text) VALUES ((SELECT MAX(commentId) FROM Comments),?,?)")
                            .setParameter(1, rating)
                            .setParameter(2, text1)
                            .executeUpdate();
                    break;
                case "T":
                    new_em.createNativeQuery("INSERT INTO textComment(commentId, Comment_Text) VALUES ((SELECT MAX(commentId) FROM Comments),?)")
                            .setParameter(1, text1)
                            .executeUpdate();
                    break;
                case "R":
                    new_em.createNativeQuery("INSERT INTO ratingComment(commentId, Comment_Rating) VALUES ((SELECT MAX(commentId) FROM Comments),?)")
                            .setParameter(1, rating)
                            .executeUpdate();
                    break;
            }
            ut.commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void startup() {
        coursesAsString = new ArrayList<String>();
        coursesAsCourses = new ArrayList<CourseEntity>();
        selectedCoursesAsCourses = new ArrayList<CourseEntity>();
        selectedCoursesAsString = new ArrayList<String>();


        EntityManagerFactory factory = Persistence.createEntityManagerFactory("DADemoPU");
        EntityManager new_em = factory.createEntityManager();

        // Retrieve all professors
        allProfessors = new_em.createQuery("Select P from ProfessorEntity P", ProfessorEntity.class).getResultList();

        // Retrieve all courses and put them in lists as CourseEntity and their names as Strings (this has to do with the PrimeFaces library)
        for (CourseEntity c : new_em.createQuery("SELECT c FROM CourseEntity c", CourseEntity.class).getResultList()) {
            coursesAsString.add(c.getName());
            coursesAsCourses.add(c);
        }
        //System.out.print("Print CoursesAsString after query: " + coursesAsString);

        // Have the EntityManager find the loggedin user
        loggedInUser = new_em.find(PersonEntity.class, userId);

        // For this user, select all the courses that they are following and again store them as CourseEntities and their names as Strings
        for (CourseEntity c : loggedInUser.getFollowingCourses()) {
            selectedCoursesAsString.add(c.getName());
            selectedCoursesAsCourses.add(c);
        }
        //System.out.print("Print selectedCoursesAsString after query: " + selectedCoursesAsString + ". SelectedCoursesAsCourses: " + selectedCoursesAsCourses);

        // Call the other functions that change on the followed courses changing
        functionOnChange();
    }

    public String testFunction() {
        if (test == 1) {
            test = 0;
            return "even";
        }
        else {
            test = 1;
            return "odd";
        }
    }

    public void functionOnChange(){
        // TODO: Check of we dit niet kunnen verbeteren, eventueel door iets efficienter te schrijven om te checken of door primefaces toch met een List<CourseEntity> te doen werken
        // PrimeFaces library seems to sometimes only work with either List<String> or sometimes List<Entity> so we have to write weird checks such as this
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("DADemoPU");
        EntityManager new_em = factory.createEntityManager();
        try {
            List<CourseEntity> oldCourses = new ArrayList<>(selectedCoursesAsCourses);
            System.out.println("Print oldCourses 1" + oldCourses);
            CourseEntity changedCourse;
            boolean trueforaddfalseforremove;
            selectedCoursesAsCourses.clear();
            System.out.println("Print oldCourses 2" + oldCourses);
            System.out.println("Print selectedCourses" + selectedCoursesAsString);
            for (CourseEntity c : coursesAsCourses
            ) {
                if(selectedCoursesAsString.contains(c.getName())) {
                    selectedCoursesAsCourses.add(c);

                    if(!oldCourses.contains(c)){
                        changedCourse = c;
                        System.out.println("Print Created Relation between: " + changedCourse.getCourseId() + " and " + userId);
                        ut.begin();
                        new_em.joinTransaction();
                        new_em.createNativeQuery("INSERT INTO jnd_course_person (course_fk, person_fk) VALUES (?,?)")
                                .setParameter(1, changedCourse.getCourseId())
                                .setParameter(2, userId)
                                .executeUpdate();
                        ut.commit();
                    }

                }

                //stel courses weggegaan. Courses niet in huidige selectie (Primefaces) maar wel in vorige selectie (query uit databank).
                else if (oldCourses.contains(c)) {
                    changedCourse = c;
                    System.out.println("Print Deleted Relation between: " + changedCourse.getCourseId() + " and " + userId);
                    ut.begin();
                    new_em.joinTransaction();
                    new_em.createNativeQuery("DELETE FROM jnd_course_person WHERE (course_fk = ?) AND (person_fk = ?)")
                            .setParameter(1, changedCourse.getCourseId())
                            .setParameter(2, userId)
                            .executeUpdate();
                    ut.commit();
                }
            }
            System.out.println("Print Selectedcourses as courses: " + selectedCoursesAsCourses);

            gatherProfessors();
            makeCourseMenu();
        } catch (NotSupportedException e) {
            throw new RuntimeException(e);
        } catch (SystemException e) {
            throw new RuntimeException(e);
        } catch (HeuristicRollbackException e) {
            throw new RuntimeException(e);
        } catch (HeuristicMixedException e) {
            throw new RuntimeException(e);
        } catch (RollbackException e) {
            throw new RuntimeException(e);
        }
    }

    public void gatherProfessors() {
        allProfessorNames = new ArrayList<String>(); //wie geeft dit vak, welke professoren zijn dit?)
        RCommentsToShow = new ArrayList<ratingComment>();
        TCommentsToShow = new ArrayList<textComment>();
        RTCommentsToShow = new ArrayList<ratingTextComment>();

        // TODO: Zie of dit niet efficienter kan dan gelijk 20 ifs and for loops?
        // For each selected course, look at which professors teach that course.
        // For each professor, if they haven't been checked already, get all the comments that are about them
        // For each comment, check which type of comment it is, and store it appropriately
        for (CourseEntity c : selectedCoursesAsCourses
        ) {
            for (ProfessorEntity p : c.getCourseGivenBy()
            ) {
                //System.out.println("¨Print Courses given by prof: " + p.getName());
                if (!allProfessorNames.contains(p.getName())) // TODO: Dit zorgt ervoor dat proffen met dezelfde naam er maar 1 keer inkomen. Misschien oplossen?
                {
                    if (p.getCommentsAbout().size() > 0) {
                        for (CommentEntity d : p.getCommentsAbout()
                        ) {
                            switch (d.getClass().getSimpleName()) {
                                case "ratingComment":
                                    RCommentsToShow.add((ratingComment) d);
                                    break;
                                case "textComment":
                                    TCommentsToShow.add((textComment) d);
                                    break;
                                case "ratingTextComment":
                                    RTCommentsToShow.add((ratingTextComment) d);
                                    break;
                            }
                        }
                    }
                    allProfessorNames.add(p.getName());
                }
            }
        }
        //System.out.println("Print allProfessorNames: " + allProfessorNames);
    }

    public void makeCourseMenu () {

        //System.out.println("Print selectedCoursesAsString while making menu: " + selectedCoursesAsString);

        MenuModel menu = new DefaultMenuModel();

        // Create labels for each different type of course in the submenu
        DefaultSubMenu firstSubmenu = DefaultSubMenu.builder()
                .label("1st Year")
                .build();

        DefaultSubMenu secondSubmenu = DefaultSubMenu.builder()
                .label("2nd Year")
                .build();

        DefaultSubMenu thirdSubmenu = DefaultSubMenu.builder()
                .label("3rd Year")
                .build();

        DefaultSubMenu fourthSubmenu = DefaultSubMenu.builder()
                .label("Master")
                .build();

        DefaultMenuItem item;

        // For each selected course, make a new menu element and bind it to the proper submenu based on its enum value
        for (CourseEntity c : selectedCoursesAsCourses
        ) {
            item = DefaultMenuItem.builder()
                    .value(c.getName())
                    .build();

            switch (c.getEnumAsInt()) {
                case 1:
                    firstSubmenu.getElements().add(item);
                    break;
                case 2:
                    secondSubmenu.getElements().add(item);
                    break;
                case 3:
                    thirdSubmenu.getElements().add(item);
                    break;
                case 4:
                    fourthSubmenu.getElements().add(item);
                    break;

            }
        }

        // If an item is bound to a submenu element, show it on the menu
        if (firstSubmenu.getElements().size() > 0) {
            menu.getElements().add(firstSubmenu);
        }
        if (secondSubmenu.getElements().size() > 0) {
            menu.getElements().add(secondSubmenu);
        }
        if (thirdSubmenu.getElements().size() > 0) {
            menu.getElements().add(thirdSubmenu);
        }
        if (fourthSubmenu.getElements().size() > 0) {
            menu.getElements().add(fourthSubmenu);
        }

        // Set the new menumodel
        menumodel = menu;
    }

    public List<String> getCoursesAsString () {
        return coursesAsString;
    }

    public void setCoursesAsString (List < String > courses) {
        this.coursesAsString = courses;
    }

    /*
    public EntityManager getNew_em () {
        return new_em;
    }*/

    public int getUserId () {
        return userId;
    }

    /*
    public void setNew_em (EntityManager new_em){
        this.new_em = new_em;
    }*/

    public ArrayList<String> getSelectedCoursesAsString () {
        return selectedCoursesAsString;
    }

    public void setSelectedCoursesAsString (ArrayList < String > selectedCoursesAsString) {
        this.selectedCoursesAsString = selectedCoursesAsString;
    }

    public List<CourseEntity> getSelectedCoursesAsCourses () {
        return selectedCoursesAsCourses;
    }

    public void setSelectedCoursesAsCourses (List < CourseEntity > selectedCoursesAsCourses) {
        this.selectedCoursesAsCourses = selectedCoursesAsCourses;
    }

    public MenuModel getMenumodel () {
        return menumodel;
    }

    public void setMenumodel (MenuModel menumodel){
        this.menumodel = menumodel;
    }

    public List<CommentEntity> getCommentsToShow () {
        return commentsToShow;
    }

    public void setCommentsToShow (List < CommentEntity > commentsToShow) {
        this.commentsToShow = commentsToShow;
    }

    public List<String> getAllProfessorNames () {
        return allProfessorNames;
    }

    public void setAllProfessorNames (List < String > allProfessorNames) {
        this.allProfessorNames = allProfessorNames;
    }

    public String getUserFullName () {
        return loggedInUser.getName() + " " + loggedInUser.getLastName();
    }

    public List<ProfessorEntity> getAllProfessors () {
        return allProfessors;
    }

    public void setAllProfessors (List < ProfessorEntity > allProfessors) {
        this.allProfessors = allProfessors;
    }

    public PersonEntity getLoggedInUser () {
        return loggedInUser;
    }

    public void setLoggedInUser (PersonEntity loggedInUser){
        this.loggedInUser = loggedInUser;
    }

    public void setUserId ( int userId){
        this.userId = userId;
    }

    public int getCount () {
        return count;
    }

    public void setCount ( int count){
        this.count = count;
    }

    public List<CourseEntity> getCoursesAsCourses () {
        return coursesAsCourses;
    }

    public void setCoursesAsCourses (List < CourseEntity > coursesAsCourses) {
        this.coursesAsCourses = coursesAsCourses;
    }

    public String getText1 () {
        return text1;
    }

    public void setText1 (String text1){
        this.text1 = text1;
    }

    public Integer getRating () {
        return rating;
    }

    public void setRating (Integer rating){
        this.rating = rating;
    }

    /*public void onrate(RateEvent rateEvent){
        this.rating = 3 + ((Integer) rateEvent.getRating());
    }*/

    public String getChosenProfessor () {
        return chosenProfessor;
    }

    public void setChosenProfessor (String chosenProfessor){
        this.chosenProfessor = chosenProfessor;
    }


    public ArrayList<ratingComment> getRCommentsToShow () {
        return RCommentsToShow;
    }

    public void setRCommentsToShow (ArrayList < ratingComment > RCommentsToShow) {
        this.RCommentsToShow = RCommentsToShow;
    }

    public ArrayList<textComment> getTCommentsToShow () {
        return TCommentsToShow;
    }

    public void setTCommentsToShow (ArrayList < textComment > TCommentsToShow) {
        this.TCommentsToShow = TCommentsToShow;
    }

    public ArrayList<ratingTextComment> getRTCommentsToShow () {
        return RTCommentsToShow;
    }

    public void setRTCommentsToShow (ArrayList < ratingTextComment > RTCommentsToShow) {
        this.RTCommentsToShow = RTCommentsToShow;
    }

    public String[] getSelectedCoursesAsStringArray() {
        return selectedCoursesAsStringArray;
    }

    public void setSelectedCoursesAsStringArray(String[] selectedCoursesAsStringArray) {
        this.selectedCoursesAsStringArray = selectedCoursesAsStringArray;
    }
}
