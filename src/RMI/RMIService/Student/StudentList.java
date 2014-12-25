package RMI.RMIService.Student;

/**
 * Created by amaliujia on 14-12-24.
 */
public class StudentList implements StudentMethods {
    @Override
    public String listStudents() {
        return null;
    }

    public String wishes(String name){
        return "Happy new Year!" + name == null? "everyBody" : name;
    }

}
