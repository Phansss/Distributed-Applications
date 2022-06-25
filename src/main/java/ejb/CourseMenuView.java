package ejb;

import entities.CourseEntity;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import org.primefaces.model.menu.*;


import java.util.*;


@Named
@SessionScoped
public class CourseMenuView implements java.io.Serializable {

    private DefaultSubMenu firstSubmenu;
    private DefaultSubMenu secondSubmenu;
    private DefaultSubMenu thirdSubmenu;
    private DefaultSubMenu fourthSubmenu;

    private final DefaultMenuModel courseMenu;

    public CourseMenuView() {
        courseMenu = new DefaultMenuModel();
    }

    @PostConstruct
    private void initialize() {
        firstSubmenu = new DefaultSubMenu();
        secondSubmenu = new DefaultSubMenu();
        thirdSubmenu = new DefaultSubMenu();
        fourthSubmenu = new DefaultSubMenu();
        firstSubmenu = DefaultSubMenu.builder()
                .label("1st Bachelor")
                .build();
        secondSubmenu = DefaultSubMenu.builder()
                .label("2nd Bachelor")
                .build();
        thirdSubmenu = DefaultSubMenu.builder()
                .label("3rd Bachelor")
                .build();
        fourthSubmenu = DefaultSubMenu.builder()
                .label("Master")
                .build();
    }

    public void buildCoursesMenuTest(List<CourseEntity> subscribedCourses) {

        DefaultMenuItem item;
        for (CourseEntity c : subscribedCourses) {

            Integer courseId = c.getCourseId();
            item = DefaultMenuItem.builder()
                    .value(c.getName())
                    .command("#{homeScreenBean.showCourse}")
                    .build();
            item.setParam("courseId", c.getId());

            //System.out.println("setting courseId parameter of course " + c.getName() +  " to " + courseId.toString());

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
        if (firstSubmenu.getElements().size() > 0) {
            //System.out.println("showing Submenu 1");
            courseMenu.getElements().add(firstSubmenu);
        }
        if (secondSubmenu.getElements().size() > 0) {
            //System.out.println("showing Submenu 2");
            courseMenu.getElements().add(secondSubmenu);
        }
        if (thirdSubmenu.getElements().size() > 0) {
            //System.out.println("showing Submenu 3");
            courseMenu.getElements().add(thirdSubmenu);
        }
        if (fourthSubmenu.getElements().size() > 0) {
            //System.out.println("showing Submenu 4");
            courseMenu.getElements().add(fourthSubmenu);
        }

    }

    public void addCourse(CourseEntity course) {

        Integer courseId = course.getCourseId();
        DefaultMenuItem item;

        item = DefaultMenuItem.builder()
                .value(course.getName())
                .command("#{homeScreenBean.showCourse}")
                .build();
        item.setParam("courseId", course.getId());
        switch (course.getEnumAsInt()) {
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
        if (firstSubmenu.getElements().size() > 0 && !(courseMenu.getElements().contains(firstSubmenu))) {
            //System.out.println("showing Submenu 1");
            courseMenu.getElements().add(firstSubmenu);
        }
        if (secondSubmenu.getElements().size() > 0 && !(courseMenu.getElements().contains(secondSubmenu))) {
            //System.out.println("showing Submenu 2");
            courseMenu.getElements().add(secondSubmenu);
        }
        if (thirdSubmenu.getElements().size() > 0 && !(courseMenu.getElements().contains(thirdSubmenu))) {
            //System.out.println("showing Submenu 3");
            courseMenu.getElements().add(thirdSubmenu);
        }
        if (fourthSubmenu.getElements().size() > 0 && !(courseMenu.getElements().contains(fourthSubmenu))) {
            //System.out.println("showing Submenu 4");
            courseMenu.getElements().add(fourthSubmenu);
        }
    }

    public void removeCourse(CourseEntity course) {
        Integer courseId = course.getId();
        System.out.println("Removng..");
        switch (course.getEnumAsInt()) {
            case 1:
                Iterator<MenuElement> iterator1 = firstSubmenu.getElements().iterator();
                while(iterator1.hasNext()) {
                    MenuElement me = iterator1.next();
                    DefaultMenuItem dme = (DefaultMenuItem) me;
                    int menuElementId = Integer.parseInt(dme.getParams().get("courseId").get(0));
                    if (menuElementId == courseId) {
                        System.out.println("remove from menu");
                        iterator1.remove();
                    }
                }
                break;
            case 2:
                Iterator<MenuElement> iterator2 = secondSubmenu.getElements().iterator();
                while(iterator2.hasNext()) {
                    MenuElement me = iterator2.next();
                    DefaultMenuItem dme = (DefaultMenuItem) me;
                    int menuElementId = Integer.parseInt(dme.getParams().get("courseId").get(0));
                    if (menuElementId == courseId) {
                        System.out.println("remove from menu");
                        iterator2.remove();
                    }
                }
                break;
            case 3:
                Iterator<MenuElement> iterator3 = thirdSubmenu.getElements().iterator();
                while(iterator3.hasNext()) {
                    MenuElement me = iterator3.next();
                    DefaultMenuItem dme = (DefaultMenuItem) me;
                    int menuElementId = Integer.parseInt(dme.getParams().get("courseId").get(0));
                    if (menuElementId == courseId) {
                        System.out.println("remove from menu");
                        iterator3.remove();
                    }
                }
                break;
            case 4:
                Iterator<MenuElement> iterator4 = fourthSubmenu.getElements().iterator();
                while(iterator4.hasNext()) {
                    MenuElement me = iterator4.next();
                    DefaultMenuItem dme = (DefaultMenuItem) me;
                    int menuElementId = Integer.parseInt(dme.getParams().get("courseId").get(0));
                    if (menuElementId == courseId) {
                        System.out.println("remove from menu");
                        iterator4.remove();
                    }
                }
                break;
        }
        if (firstSubmenu.getElements().size() <= 0) {
            //System.out.println("showing Submenu 1");
            courseMenu.getElements().remove(firstSubmenu);
        }
        if (secondSubmenu.getElements().size() <= 0) {
            //System.out.println("showing Submenu 2");
            courseMenu.getElements().remove(secondSubmenu);
        }
        if (thirdSubmenu.getElements().size() <= 0) {
            //System.out.println("showing Submenu 3");
            courseMenu.getElements().remove(thirdSubmenu);
        }
        if (fourthSubmenu.getElements().size() <= 0) {
            //System.out.println("showing Submenu 4");
            courseMenu.getElements().remove(fourthSubmenu);
        }

    }


    public DefaultMenuModel getCourseMenu() {
        return courseMenu;
    }

}
