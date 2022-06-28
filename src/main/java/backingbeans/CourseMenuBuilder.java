package backingbeans;

import entities.CourseEntity;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import org.primefaces.model.menu.*;


import java.util.*;


@Named
@RequestScoped
public class CourseMenuBuilder {

    private DefaultSubMenu firstSubmenu;
    private DefaultSubMenu secondSubmenu;
    private DefaultSubMenu thirdSubmenu;
    private DefaultSubMenu fourthSubmenu;

    private DefaultMenuModel courseMenu;

    public CourseMenuBuilder() {

    }

    @PostConstruct

    private void initialize() {
        courseMenu = new DefaultMenuModel();

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

    public void buildCoursesMenu(List<CourseEntity> subscribedCourses) {
        courseMenu.getElements().clear();
        DefaultMenuItem item;
        for (CourseEntity c : subscribedCourses) {

            Integer courseId = c.getCourseId();
            item = DefaultMenuItem.builder()
                    .value(c.getName())
                    .command("#{homeScreenBean.showCourse}")
                    .build();
            item.setParam("course", c.getId());

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

    public DefaultMenuModel getCourseMenu() {
        return courseMenu;
    }

}
