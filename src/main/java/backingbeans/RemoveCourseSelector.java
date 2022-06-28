package backingbeans;

import entities.CourseEntity;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

import java.util.List;




@Named
@RequestScoped
public class RemoveCourseSelector {

    private List<CourseEntity> coursesToRemove;
    private CourseEntity selectedCourse;

    public RemoveCourseSelector() {

    }

    public void setCoursesToRemove(List<CourseEntity> coursesToremove) { this.coursesToRemove = coursesToremove; }
    public List<CourseEntity> getCoursesToRemove() { return this.coursesToRemove; }

    public void setSelectedCourse(CourseEntity course) { this.selectedCourse = course; }
    public CourseEntity getSelectedCourse() { return this.selectedCourse; }
}

