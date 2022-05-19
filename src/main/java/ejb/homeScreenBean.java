package ejb;

import entities.*;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Stateful;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.bean.ManagedBean;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

import java.io.Serializable;
import java.util.*;

@ManagedBean(name = "homeScreenBean")
@SessionScoped
public class homeScreenBean implements Serializable {

    @PersistenceContext(name = "DADemoPU")
    EntityManager new_em;

    private List<String> selectedCoursesAsString;

    private List<String> allProfessorNames;

    private List<CourseEntity> selectedCoursesAsCourses;
    private List<String> courses;

    private MenuModel menumodel;

    private List<ratingTextComment> commentsToShow;

    public homeScreenBean() {
    }

    @PostConstruct
    public void setNecessaryCourses(){
        courses = new ArrayList<String>();
        selectedCoursesAsString = new ArrayList<String>();
        selectedCoursesAsCourses = new ArrayList<CourseEntity>();

        Query query = new_em.createQuery("SELECT c FROM CourseEntity c", CourseEntity.class);
        List<CourseEntity> courseEntityList = (List<CourseEntity>) query.getResultList();
        for (CourseEntity c: courseEntityList) {
            courses.add(c.getName());
        }


        query = new_em.createQuery("SELECT P FROM PersonEntity P"); // TODO: FILTER THIS ON THE LOGGED IN USER
        List<PersonEntity> selectedCoursesEntityList = (List<PersonEntity>) query.getResultList();
        for (PersonEntity p: selectedCoursesEntityList) {
            for(CourseEntity c: p.getFollowingCourses()){
                selectedCoursesAsString.add(c.getName());
                selectedCoursesAsCourses.add(c);
            }
        }
        makeCourseMenu();
        gatherProfessors();
    }

    public void gatherProfessors(){
        allProfessorNames = new ArrayList<String>();

        Query query = new_em.createQuery("SELECT P FROM ProfessorEntity P"); // TODO: FILTER THIS ON THE LOGGED IN USER
        List<ProfessorEntity> allProfessorsList = (List<ProfessorEntity>) query.getResultList();
        for(ProfessorEntity c: allProfessorsList){
            allProfessorNames.add(c.getName());
        }
    }

    public void makeCourseMenu(){

        MenuModel menu = new DefaultMenuModel();

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

        for (CourseEntity c: selectedCoursesAsCourses
        ) {
            item = DefaultMenuItem.builder()
                    .value(c.getName())
                    .build();

            switch(c.getEnumAsInt()){

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

        if(firstSubmenu.getElements().size()>0){
            menu.getElements().add(firstSubmenu);
        }
        if(secondSubmenu.getElements().size()>0){
            menu.getElements().add(secondSubmenu);
        }
        if(thirdSubmenu.getElements().size()>0){
            menu.getElements().add(thirdSubmenu);
        }
        if(fourthSubmenu.getElements().size()>0){
            menu.getElements().add(fourthSubmenu);
        }
        menumodel = menu;
        createTimeline();
    }

    public void addCourseFromMenu(){

    }

    public void removeCourseFromMenu(){

    }

    public void createTimeline(){
        Query query = new_em.createQuery("SELECT c FROM ratingTextComment c", ratingTextComment.class); // TODO: FILTER THIS ON THE LOGGED IN USER

        commentsToShow = (List<ratingTextComment>) query.getResultList();
    }

    public List<String> getCourses() {
        return courses;
    }

    public void setCourses(List<String> courses) {
        this.courses = courses;
    }

    public EntityManager getNew_em() {
        return new_em;
    }

    public void setNew_em(EntityManager new_em) {
        this.new_em = new_em;
    }

    public List<String> getSelectedCoursesAsString() {
        return selectedCoursesAsString;
    }

    public void setSelectedCoursesAsString(List<String> selectedCoursesAsString) {
        this.selectedCoursesAsString = selectedCoursesAsString;
    }

    public List<CourseEntity> getSelectedCoursesAsCourses() {
        return selectedCoursesAsCourses;
    }

    public void setSelectedCoursesAsCourses(List<CourseEntity> selectedCoursesAsCourses) {
        this.selectedCoursesAsCourses = selectedCoursesAsCourses;
    }

    public MenuModel getMenumodel() {
        return menumodel;
    }

    public void setMenumodel(MenuModel menumodel) {
        this.menumodel = menumodel;
    }

    public List<ratingTextComment> getCommentsToShow() {
        return commentsToShow;
    }

    public void setCommentsToShow(List<ratingTextComment> commentsToShow) {
        this.commentsToShow = commentsToShow;
    }

    public List<String> getAllProfessorNames() {
        return allProfessorNames;
    }

    public void setAllProfessorNames(List<String> allProfessorNames) {
        this.allProfessorNames = allProfessorNames;
    }
}
