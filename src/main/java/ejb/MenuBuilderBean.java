package ejb;

import entities.CourseEntity;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Named;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;


import java.util.List;


@Named
@SessionScoped
public class MenuBuilderBean implements java.io.Serializable {

    private DefaultSubMenu firstSubmenu;
    private DefaultSubMenu secondSubmenu;
    private DefaultSubMenu thirdSubmenu;
    private DefaultSubMenu fourthSubmenu;


    private DefaultMenuModel model;

    public MenuBuilderBean() {
        model = new DefaultMenuModel();
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
    public void buildMenu(List<CourseEntity> subscribedCourses) {
        clearMenu();
        DefaultMenuItem item;
        for (CourseEntity c : subscribedCourses
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
            System.out.println("showing Submenu 1");
            model.getElements().add(firstSubmenu);
        }
        if (secondSubmenu.getElements().size() > 0) {
            System.out.println("showing Submenu 2");
            model.getElements().add(secondSubmenu);
        }
        if (thirdSubmenu.getElements().size() > 0) {
            System.out.println("showing Submenu 3");
            model.getElements().add(thirdSubmenu);
        }
        if (fourthSubmenu.getElements().size() > 0) {
            System.out.println("showing Submenu 4");
            model.getElements().add(fourthSubmenu);
        }
        System.out.println(model.getElements());
    }

    public DefaultMenuModel getModel() {
        return model;
    }

    private void clearMenu(){
        firstSubmenu.getElements().clear();
        secondSubmenu.getElements().clear();
        thirdSubmenu.getElements().clear();
        fourthSubmenu.getElements().clear();
        model.getElements().clear();
    }
}
