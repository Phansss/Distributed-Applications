package ejb;

import entities.CourseEntity;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.model.SelectItem;
import jakarta.faces.model.SelectItemGroup;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Named
@RequestScoped
public class MenuBean {

    private List<CourseEntity> courses;
    private CourseEntity selectedCourse;

    private List<SelectItem> coursesGroup;
    private String courseGroup;

    private SelectItemGroup firstYear;
    private SelectItemGroup secondYear;
    private SelectItemGroup thirdYear;
    private SelectItemGroup fourthYear;

    @PersistenceContext(unitName = "DADemoPU")
    private EntityManager em;

    public MenuBean() {

    }




    @PostConstruct
    public void initialize() {
/*        this.courses = new ArrayList<CourseEntity>();
        CourseEntity course1 = em.find(CourseEntity.class, 1);
        CourseEntity course2 = em.find(CourseEntity.class, 2);
        System.out.println(course1.getName() + " & " + course2.getName());
        courses.add(course1);
        courses.add(course2);*/
//        System.out.println("Postconstruct menuBean!");
//        coursesGroup = new ArrayList<>();
//
//        this.firstYear = new SelectItemGroup("Bachelor 1");
//        this.secondYear = new SelectItemGroup("Bachelor 2");
//        this.thirdYear = new SelectItemGroup("Bachelor 3");
//        this.fourthYear = new SelectItemGroup("Master");
//
//        coursesGroup.add(firstYear);
//        coursesGroup.add(secondYear);
//        coursesGroup.add(thirdYear);
//        coursesGroup.add(fourthYear);
    }


    //GETTERS SETTERS ADDERS REMOVERS
    /*public void addCourse(CourseEntity course) {
        SelectItem itemToAdd = new SelectItem(course.getName());
        System.out.println("selectItem Description: "+ itemToAdd.getDescription());
        System.out.println("selectItem Value: "+ itemToAdd.getValue());
        System.out.println("selectItem Class: "+ itemToAdd.getClass());
        System.out.println("selectItem Label: "+ itemToAdd.getLabel());
        //itemToAdd.setValue("itemValue");
        switch (course.getEnumAsInt()) {
            case 1:
                firstYear.setSelectItems(addSelectItem(firstYear.getSelectItems(), itemToAdd));
            case 2:
                secondYear.setSelectItems(addSelectItem(secondYear.getSelectItems(), itemToAdd));
            case 3:
                thirdYear.setSelectItems(addSelectItem(thirdYear.getSelectItems(), itemToAdd));
            case 4:
                fourthYear.setSelectItems(addSelectItem(fourthYear.getSelectItems(), itemToAdd));
        }
    }
    public void removeCourse(CourseEntity course) {
        switch (course.getEnumAsInt()) {
            case 1:

            case 2:

            case 3:

            case 4:

        }
    }

*/
    public void setCourses(List<CourseEntity> courses) { this.courses = courses; }
    public List<CourseEntity> getCourses() { return this.courses; }

    public void setSelectedCourse(CourseEntity course) { this.selectedCourse = course; }
    public CourseEntity getSelectedCourse() { return this.selectedCourse; }
     /*

    public List<SelectItem> getCoursesGroup() { return this.coursesGroup; }
    public void setCoursesGroup(List<SelectItem> coursesGroup) { this.coursesGroup = coursesGroup; }

    public void setCourseGroup(String courseGroup) { this.courseGroup = courseGroup;}
    public String getCourseGroup() {return this.courseGroup;}

    private SelectItem[] addSelectItem(SelectItem[] array, SelectItem item) {
        if(array == null) {
            array = new SelectItem[0];
        }
        int n = array.length;
        SelectItem[] selectItems = new SelectItem[n + 1];
        int i;
        for (i = 0; i < n; i++)
            selectItems[i] = array[i];
        selectItems[n] = item;
        return selectItems;
    }*/
}
