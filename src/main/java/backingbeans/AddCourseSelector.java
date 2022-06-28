package backingbeans;

import entities.CourseEntity;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

import java.util.List;

@Named
@RequestScoped
public class AddCourseSelector {

    private List<CourseEntity> coursesToAdd;
    private CourseEntity selectedCourse;

    public AddCourseSelector() {

    }

    public void setCoursesToAdd(List<CourseEntity> coursesToAdd) { this.coursesToAdd = coursesToAdd; }
    public List<CourseEntity> getCoursesToAdd() { return this.coursesToAdd; }

    public void setSelectedCourse(CourseEntity course) { this.selectedCourse = course; }
    public CourseEntity getSelectedCourse() { return this.selectedCourse; }
}
