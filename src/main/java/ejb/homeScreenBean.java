package ejb;

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
public class  homeScreenBean implements Serializable {


    //COMMENTS
    ArrayList<ratingComment> RCommentsToShow;
    ArrayList<textComment> TCommentsToShow;
    ArrayList<ratingTextComment> RTCommentsToShow;
    private List<CommentEntity> commentsToShow;
    private String inputComment;
    private Integer inputRating;


    //PROFESSORS
    private List<String> professorNames;
    private List<ProfessorEntity> professorEntities;
    private List<String> currentCourseProfessors;
    private String chosenProfessor;

    //COURSES
    private Map<Integer, CourseEntity> courseEntities;
    private Map<String, Integer> unSubscribedCoursesMap;
    private Map<String, Integer> subscribedCoursesMap;
    private List<CourseEntity> subscribedCourses;
    CourseEntity currentCourse;

    //LOGGEDINUSER
    private PersonEntity person;
    private int userId;


    private MenuModel courseMenu;

    //INJECTIONS
    @Inject
    /*Returns the javax.transaction.UserTransaction interface to demarcate transactions.
    Only session beans with bean-managed transaction (BMT) can use this method.
    ALTERNATIE = CMT*/
    UserTransaction ut;
    @PersistenceContext(unitName = "DADemoPU")
    private EntityManager em;
    @Inject
    CourseServiceBean courseServiceBean;
    @Inject
    PersonServiceBean personServiceBean;
    @Inject
    CourseMenuView courseMenuView;

    @PostConstruct
    public void initialize() {
        //Load the personEntity (for now: persistent to DB!)
        this.person = personServiceBean.getPersonEntity(userId);

        //Load all courseEntities from DB (for now: persistent to DB!)
        this.courseEntities = new HashMap<Integer, CourseEntity>();
        courseServiceBean.getAllCourses().forEach((courseEntity) -> {
            courseEntities.put(courseEntity.getId(), courseEntity);
        });

        //Load all courses to which this user currently is subscribed from DB (for now: persistent to DB!)
        this.subscribedCourses = person.getSubscribedCourses();

        //Create 2 Hashmaps which contain key value pairs of subscried and unsubscribed courses of this user)
        this.unSubscribedCoursesMap = new HashMap<String, Integer>();
        this.subscribedCoursesMap = new HashMap<String, Integer>();
        getCourseEntities().forEach((courseId, course) -> {
            if (getSubscribedCourses().contains(course)) {
                subscribedCoursesMap.put(course.getName(), course.getId());
            }
            else {
                unSubscribedCoursesMap.put(course.getName(), course.getId());
            }
        });
        this.currentCourseProfessors = new ArrayList<String>();
        //Set the current course which is showing to the first course in the subscribedCourseEntities list.
        if (getSubscribedCourses().size() != 0) {
            this.currentCourse = getSubscribedCourses().get(0);
            setCurrentCourseProfessors();
        }
        //set the currentCourseProfessor


        //Load the Menu Model of the course menu in this homeScreenBeans field so that it can be rendered by primefaces
        this.courseMenu = courseMenuView.getCourseMenu();
        courseMenuView.buildCoursesMenuTest(getSubscribedCourses());
        System.out.println("subscribed courses: " + getSubscribedCoursesMap());
        System.out.println("unsubscribed courses: " + getUnSubscribedCoursesMap());
    }

    public homeScreenBean() {
        //System.out.println("PRINT MSG: Creating a homeScreenBean");
        //System.out.println(courseServiceBean);
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        userId = (int) session.getAttribute("user");
        //System.out.println(userId);
    }
    public void addCourse(){
        String courseName = FacesContext.getCurrentInstance().getExternalContext().
                getRequestParameterMap().get("j_idt9:multiple-add");
        if (courseName != null) {
            Integer courseId = getUnSubscribedCoursesMap().get(courseName);
            courseServiceBean.addCourseToPerson(courseId, getPerson().getId());
            getSubscribedCoursesMap().put(courseName, courseId);
            getUnSubscribedCoursesMap().remove(courseName);
            courseMenuView.addCourse(getCourseEntities().get(courseId));
        }
        //Add element to menu

        //Persist to database

        // PrimeFaces library seems to sometimes only work with either List<String> or sometimes List<Entity> so we have to write weird checks such as this
        //try {
            //List<CourseEntity> oldCourses = new ArrayList<>(selectedCoursesAsCourses);
            //System.out.println("Print oldCourses 1" + oldCourses);
            //CourseEntity changedCourse;
            //boolean trueforaddfalseforremove;
            //selectedCoursesAsCourses.clear();
            //System.out.println("Print oldCourses 2" + oldCourses);
            //System.out.println("Print selectedCourses" + selectedCoursesAsString);
            /*for (CourseEntity c : coursesAsCourses
            ) {
                if(selectedCoursesAsString.



             contains(c.getName())) {
                    selectedCoursesAsCourses.add(c);

                    if(!oldCourses.contains(c)){
                        changedCourse = c;
                        System.out.println("Print Created Relation between: " + changedCourse.getCourseId() + " and " + userId);
                        ut.begin();
                        em.joinTransaction();
                        em.createNativeQuery("INSERT INTO jnd_course_person (course_fk, person_fk) VALUES (?,?)")
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
                    em.joinTransaction();
                    em.createNativeQuery("DELETE FROM jnd_course_person WHERE (course_fk = ?) AND (person_fk = ?)")
                            .setParameter(1, changedCourse.getCourseId())
                            .setParameter(2, userId)
                            .executeUpdate();
                    ut.commit();
                }
            }
            System.out.println("Print Selectedcourses as courses: " + selectedCoursesAsCourses);

            gatherProfessors();
            makeCourseMenu();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }*/
    }
    public void removeCourse() {
        String courseName = FacesContext.getCurrentInstance().getExternalContext().
                getRequestParameterMap().get("j_idt9:multiple-remove");
        //System.out.println("clicked on course: " + courseName);
        //System.out.println("Current Subscribed courses: " + getSubscribedCoursesMap());
        if (courseName != null) {
            Integer courseId = getSubscribedCoursesMap().get(courseName);
            courseServiceBean.removeCourseFromPerson(courseId, getPerson().getId());
            getUnSubscribedCoursesMap().put(courseName, courseId);
            getSubscribedCoursesMap().remove(courseName);
            courseMenuView.removeCourse(getCourseEntities().get(courseId));
        }
    }

    public void showCourse() {
        System.out.println("MENU ITEM PARAMETERS: ");
        Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        System.out.println("MENU ITEM PARAMETERS: " + params);

        Integer courseId = Integer.parseInt(params.get("courseId"));
        System.out.println(courseId);
        CourseEntity currentCourse = courseServiceBean.getCourseEntity(courseId);
        this.currentCourse = currentCourse;
        setCurrentCourseProfessors();
    }

    private void setCurrentCourseProfessors() {
        //System.out.println("Setting the professors for current course: " + getCurrentCourse().getName());
        this.currentCourseProfessors.clear();
        getCurrentCourse().getGivenByProfessors().forEach((professor) -> {
            //System.out.println("Professor: " + professor.getName() + " " + professor.getSurname());
            this.currentCourseProfessors.add(professor.getName() + " " + professor.getSurname());
        });
    }

    public void addComment() {

    }


    //Opgeroepen van zodra op de add knop wordt gedrukt. Rating = 1_5, text is comment
    public void printRating()  {
        System.out.println("Print Rating: " + inputRating + ". Text1: " + inputComment + ". Professor: " + chosenProfessor);
        String commentType;
        Integer chosenProfId = 0;

        if(inputRating == null){
            commentType = "T";
        }
        else if (Objects.equals(inputComment, "")) {
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

            em.createNativeQuery("INSERT INTO Professor_Comments (ProfessorEntity_id, commentsAbout_commentId) VALUES (?, (SELECT MAX(commentId) FROM Comments))")
                    .setParameter(1, chosenProfId)
                    .executeUpdate();

            String query;
            switch (commentType){
                case "RT":
                    em.createNativeQuery("INSERT INTO ratingTextComment(commentId, Comment_Rating, Comment_Text) VALUES ((SELECT MAX(commentId) FROM Comments),?,?)")
                            .setParameter(1, inputRating)
                            .setParameter(2, inputComment)
                            .executeUpdate();
                    break;
                case "T":
                    em.createNativeQuery("INSERT INTO textComment(commentId, Comment_Text) VALUES ((SELECT MAX(commentId) FROM Comments),?)")
                            .setParameter(1, inputComment)
                            .executeUpdate();
                    break;
                case "R":
                    em.createNativeQuery("INSERT INTO ratingComment(commentId, Comment_Rating) VALUES ((SELECT MAX(commentId) FROM Comments),?)")
                            .setParameter(1, inputRating)
                            .executeUpdate();
                    break;
            }
            ut.commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void gatherProfessors() {
        professorNames = new ArrayList<String>(); //wie geeft dit vak, welke professoren zijn dit?)
        RCommentsToShow = new ArrayList<ratingComment>();
        TCommentsToShow = new ArrayList<textComment>();
        RTCommentsToShow = new ArrayList<ratingTextComment>();

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


    public String getInputComment() {
        return inputComment;
    }

    public void setInputComment(String inputComment){
        this.inputComment = inputComment;
    }

    public Integer getInputRating() {
        return inputRating;
    }

    public void setInputRating(Integer inputRating){
        this.inputRating = inputRating;
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



}
