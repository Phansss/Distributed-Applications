package backingbeans;

import ejb.CourseServiceBean;
import ejb.MenuBean;
import ejb.PersonServiceBean;
import entities.*;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.*;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.*;
import org.primefaces.model.menu.*;

import java.io.Serializable;
import java.util.*;

@Named
@SessionScoped
public class HomeScreenBean implements Serializable {


    //COMMENTS
    private List<CommentEntity> commentsToShow;
    private String inputCommentText;
    private Integer inputCommentRating;

    //PROFESSORS
    private List<String> professorNames;
    private List<ProfessorEntity> professorEntities;
    private String chosenProfessor;

    //COURSES
    private Map<Integer, CourseEntity> courseEntities;
    private Map<String, Integer> unSubscribedCoursesMap;
    private Map<String, Integer> subscribedCoursesMap;
    private List<CourseEntity> unSubscribedCourses;
    private List<CourseEntity> subscribedCourses;

    //LOGGEDINUSER
    private PersonEntity person;
    private int userId;


    /*CURRENT COURSE
     (idea: make a second stateful bean 'currentCourse' with the attributes containing
      presentation data that is rendered on request.*/
    CourseEntity currentCourse;
    private ArrayList<ratingComment> currentRatingComments;
    private ArrayList<textComment> currentTextComments;
    private ArrayList<ratingTextComment> currentRatingTextComments;
    private List<String> currentCourseProfessors;


    private MenuModel courseMenu;

    //INJECTIONS
    @Inject
    /*Returns the javax.transaction.UserTransaction interface to demarcate transactions.
    Only session beans with bean-managed transaction (BMT) can use this method.
    ALTERNATIVE = CMT*/
    UserTransaction ut;


    @PersistenceContext(unitName = "DADemoPU") private EntityManager em;
    @Inject
    CourseServiceBean courseServiceBean;
    @Inject
    PersonServiceBean personServiceBean;
    @Inject
    CourseMenuBuilder courseMenuBuilder;
    @Inject
    MenuBean menuBean;
    @Inject AddCourseSelector addCourseSelector;
    @Inject RemoveCourseSelector removeCourseSelector;


    //bean constructor
    public HomeScreenBean() {
        //System.out.println("PRINT MSG: Creating a homeScreenBean");
        //System.out.println(courseServiceBean);
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        userId = (int) session.getAttribute("user");

        //System.out.println(userId);
    }



    @PostConstruct
    public void initialize() {
        //Load the personEntity (for now: persistent to DB!)

        this.courseMenu = new DefaultMenuModel();

        this.person = personServiceBean.getPersonEntity(userId);
        //Load all courseEntities from DB (for now: persistent to DB!)
        this.courseEntities = new HashMap<Integer, CourseEntity>();
        courseServiceBean.getAllCourses().forEach((courseEntity) -> {
            courseEntities.put(courseEntity.getId(), courseEntity);
        });
        //Load all courses to which this user currently is subscribed from DB (for now: persistent to DB!)
        this.subscribedCourses = person.getSubscribedCourses();
        this.unSubscribedCourses =courseServiceBean.getAllCourses();
        this.unSubscribedCourses.removeAll(getSubscribedCourses());

        this.currentCourseProfessors = new ArrayList<String>();
        this.currentTextComments = new ArrayList<textComment>();
        this.currentRatingComments = new ArrayList<ratingComment>();
        this.currentRatingTextComments = new ArrayList<ratingTextComment>();
        //Set the current course which is showing to the first course in the subscribedCourseEntities list.
        if (getSubscribedCourses().size() != 0) {
            this.currentCourse = getSubscribedCourses().get(0);
            //set the currentCourseProfessor
            setCurrentCourseProfessors();
            setCurrentCourseComments();
        }
        //Load the Menu Model of the course menu in this homeScreenBeans field so that it can be rendered by primefaces
        //this.courseMenu = courseMenuModel.getCourseMenu();
        //courseMenuModel.buildCoursesMenuTest(getSubscribedCourses());
        menuBean.setCourses(getSubscribedCourses());
        //System.out.println("subscribed courses: " + getSubscribedCoursesMap());
        //System.out.println("unsubscribed courses: " + getUnSubscribedCoursesMap());
        courseMenuBuilder.buildCoursesMenu(getSubscribedCourses());
        this.courseMenu = courseMenuBuilder.getCourseMenu();
    }
    //fired when you add a course in the 'add' dropwdown.
    public void addCourses(){
        System.out.println(addCourseSelector.getCoursesToAdd());
        addCourseSelector.getCoursesToAdd().forEach((course) -> {
            courseServiceBean.addCourseToPerson(course.getCourseId(), getPerson().getId());
            getSubscribedCourses().add(course);
            getUnSubscribedCourses().remove(course);
        });
        courseMenuBuilder.buildCoursesMenu(getSubscribedCourses());
        this.courseMenu = courseMenuBuilder.getCourseMenu();
    }

    //fired when you remove a course in the 'remove' dropdown.
    public void removeCourses() {
        System.out.println(removeCourseSelector.getCoursesToRemove());
        removeCourseSelector.getCoursesToRemove().forEach((course) -> {
            courseServiceBean.removeCourseFromPerson(course.getCourseId(), getPerson().getId());
            getUnSubscribedCourses().add(course);
            getSubscribedCourses().remove(course);
        });
        courseMenuBuilder.buildCoursesMenu(getSubscribedCourses());
        this.courseMenu = courseMenuBuilder.getCourseMenu();
    }

    //Fired when you click on a course in the course menu.
    public void showCourse() {
        System.out.println("MENU ITEM PARAMETERS: ");
        Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        System.out.println("MENU ITEM PARAMETERS: " + params);
        Integer courseId = Integer.parseInt(params.get("course"));
        System.out.println(courseId);
        this.currentCourse = courseServiceBean.getCourseEntity(courseId);
        setCurrentCourseProfessors();
        setCurrentCourseComments();

    }
    private void setCurrentCourseProfessors() {
        //System.out.println("Setting the professors for current course: " + getCurrentCourse().getName());
        this.currentCourseProfessors.clear();
        getCurrentCourse().getGivenByProfessors().forEach((professor) -> {
            this.currentCourseProfessors.add(professor.getName() + " " + professor.getSurname());
        });
    }

    private void setCurrentCourseComments() {
        this.currentRatingTextComments.clear();
        this.currentRatingComments.clear();
        this.currentTextComments.clear();
        getCurrentCourse().getComments().forEach((comment) -> {
            if (comment instanceof ratingComment) {
                this.currentRatingComments.add((ratingComment) comment);
            } else if (comment instanceof textComment) {
                this.currentTextComments.add((textComment) comment);
            } else if (comment instanceof ratingTextComment) {
                this.currentRatingTextComments.add((ratingTextComment) comment);
            }
        });
    }


    public void addComment() {

    }




    //Opgeroepen van zodra op de add knop wordt gedrukt. Rating = 1_5, text is comment
  /*  public void printRating()  {
        System.out.println("Print Rating: " + inputCommentRating + ". Text1: " + inputCommentText + ". Professor: " + chosenProfessor);
        String commentType;
        Integer chosenProfId = 0;

        if(inputCommentRating == null){
            commentType = "T";
        }
        else if (Objects.equals(inputCommentText, "")) {
            commentType = "R";
        } else {
            commentType = "RT";
        }

        for (ProfessorEntity p: professorEntities
        ) {
            if(p.getName().equals(chosenProfessor)){
                chosenProfId = p.getId();
            }
        }

        try {

            em.createNativeQuery("INSERT INTO Professor_Has_Comments (ProfessorEntity_id, commentsAbout_commentId) VALUES (?, (SELECT MAX(commentId) FROM Comments))")
                    .setParameter(1, chosenProfId)
                    .executeUpdate();

            String query;
            switch (commentType){
                case "RT":
                    em.createNativeQuery("INSERT INTO ratingTextComment(commentId, Comment_Rating, Comment_Text) VALUES ((SELECT MAX(commentId) FROM Comments),?,?)")
                            .setParameter(1, inputCommentRating)
                            .setParameter(2, inputCommentText)
                            .executeUpdate();
                    break;
                case "T":
                    em.createNativeQuery("INSERT INTO textComment(commentId, Comment_Text) VALUES ((SELECT MAX(commentId) FROM Comments),?)")
                            .setParameter(1, inputCommentText)
                            .executeUpdate();
                    break;
                case "R":
                    em.createNativeQuery("INSERT INTO ratingComment(commentId, Comment_Rating) VALUES ((SELECT MAX(commentId) FROM Comments),?)")
                            .setParameter(1, inputCommentRating)
                            .executeUpdate();
                    break;
            }
            ut.commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }*/

    public void gatherProfessors() {
        professorNames = new ArrayList<String>(); //wie geeft dit vak, welke professoren zijn dit?)
        currentRatingComments = new ArrayList<ratingComment>();
        currentTextComments = new ArrayList<textComment>();
        currentRatingTextComments = new ArrayList<ratingTextComment>();

        // TODO: Zie of dit niet efficienter kan dan gelijk 20 ifs and for loops?
        // For each selected course, look at which professors teach that course.
        // For each professor, if they haven't been checked already, get all the comments that are about them
        // For each comment, check which type of comment it is, and store it appropriately
        for (CourseEntity c : subscribedCourses
        ) {
            for (ProfessorEntity p : c.getGivenByProfessors()
            ) {
                //System.out.println("¨Print Courses given by prof: " + p.getName());
                if (!professorNames.contains(p.getName())) // TODO: Dit zorgt ervoor dat proffen met dezelfde naam er maar 1 keer inkomen. Misschien oplossen?
                {
                    if (p.getCommentsAbout().size() > 0) {
                        for (CommentEntity d : p.getCommentsAbout()
                        ) {
                            switch (d.getClass().getSimpleName()) {
                                case "ratingComment":
                                    currentRatingComments.add((ratingComment) d);
                                    break;
                                case "textComment":
                                    currentTextComments.add((textComment) d);
                                    break;
                                case "ratingTextComment":
                                    currentRatingTextComments.add((ratingTextComment) d);
                                    break;
                            }
                        }
                    }
                    professorNames.add(p.getName());
                }
            }
        }
        //System.out.println("Print allProfessorNames: " + allProfessorNames);
    }

    public String gotowieiswie(int id){
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.setAttribute("wieiswie", id);
        return "./secured/wieiswie.xhtml";
    }



    public int getUserId () {
        return userId;
    }

    public List<CourseEntity> getSubscribedCourses() {
        return subscribedCourses;
    }

    public void setSubscribedCourses(List < CourseEntity > subscribedCourses) {
        this.subscribedCourses = subscribedCourses;
    }

    public void setMenumodel (DefaultMenuModel menumodel){
        this.courseMenu = menumodel;
    }

    public List<CommentEntity> getCommentsToShow () {
        return commentsToShow;
    }

    public void setCommentsToShow (List < CommentEntity > commentsToShow) {
        this.commentsToShow = commentsToShow;
    }

    public List<String> getProfessorNames() {
        return professorNames;
    }

    public void setProfessorNames(List < String > professorNames) {
        this.professorNames = professorNames;
    }

    public String getUserFullName () {
        return person.getName() + " " + person.getLastName();
    }

    public List<ProfessorEntity> getProfessorEntities() {
        return professorEntities;
    }

    public void setProfessorEntities(List < ProfessorEntity > professorEntities) {
        this.professorEntities = professorEntities;
    }

    public PersonEntity getPerson() {
        return person;
    }

    public void setPerson(PersonEntity person){
        this.person = person;
    }

    public void setUserId ( int userId){
        this.userId = userId;
    }


    public Map<Integer, CourseEntity> getCourseEntities() {
        return courseEntities;
    }

    public void setCourseEntities(Map<Integer, CourseEntity> courseEntities) {
        this.courseEntities = courseEntities;
    }


    public String getInputCommentText() {
        return inputCommentText;
    }

    public void setInputCommentText(String inputCommentText){
        this.inputCommentText = inputCommentText;
    }

    public Integer getInputCommentRating() {
        return inputCommentRating;
    }

    public void setInputCommentRating(Integer inputCommentRating){
        this.inputCommentRating = inputCommentRating;
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

    public ArrayList<ratingComment> getCurrentRatingComments() {
        return currentRatingComments;
    }

    public void setCurrentRatingComments(ArrayList <ratingComment> currentRatingComments) {
        this.currentRatingComments = currentRatingComments;
    }

    public ArrayList<textComment> getCurrentTextComments() {
        return currentTextComments;
    }

    public void setCurrentTextComments(ArrayList <textComment> currentTextComments) {
        this.currentTextComments = currentTextComments;
    }

    public ArrayList<ratingTextComment> getCurrentRatingTextComments() {
        return currentRatingTextComments;
    }

    public void setCurrentRatingTextComments(ArrayList <ratingTextComment> currentRatingTextComments) {
        this.currentRatingTextComments = currentRatingTextComments;

    }

    public MenuModel getCourseMenu() {
        return courseMenu;
    }



    public void subscribeForCourse(jakarta.faces.event.ActionEvent actionEvent) {
    }

    public CourseEntity getCurrentCourse() {
        return this.currentCourse;
    }

    public void setCurrentCourse(CourseEntity currentCourse) {
        this.currentCourse = currentCourse;
    }



    public List<String> getCurrentCourseProfessors() {
        return this.currentCourseProfessors;
    }




    //TODO: Vind een manier om de code hieronder te verwijderen zonder een Faces render error te krijgen (clean-build?).
    public ArrayList<String> getSelectedCoursesAsStringArray() {
        return new ArrayList<String>();
    }
    public Map<String, Integer> getUnSubscribedCoursesMap() {
        return unSubscribedCoursesMap;
    }
    public Map<String, Integer> getSubscribedCoursesMap() {
        return subscribedCoursesMap;
    }

    public List<CourseEntity> getUnSubscribedCourses() { return unSubscribedCourses; }



}
