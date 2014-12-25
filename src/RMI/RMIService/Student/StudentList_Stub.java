package RMI.RMIService.Student;

import RMI.Client.SDRemoteObjectReference;
import RMI.Client.SDRemoteStub;

/**
 * Created by amaliujia on 14-12-24.
 */
public class StudentList_Stub extends SDRemoteStub implements StudentMethods{
    public StudentList_Stub(SDRemoteObjectReference ref) {
        super(ref);
    }

    @Override
    public String listStudents() {
        return null;
    }

    public String wishes(String name){
        return "Happy new Year!" + name == null? "everyBody" : name;
    }
}
