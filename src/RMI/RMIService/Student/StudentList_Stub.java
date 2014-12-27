package RMI.RMIService.Student;

import ArcherException.SDRemoteReferenceObjectException;
import RMI.Client.SDRemoteObjectReference;
import RMI.Client.SDRemoteStub;

import java.lang.reflect.Method;

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
        String result = null;
        try {
            Method method = StudentMethods.class.getMethod("wishes", String.class);
			result = (String)invoke(method, new Object[] {name});      // what does this mean? invoke
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SDRemoteReferenceObjectException e) {
            e.printStackTrace();
        }
        return result;
    }
}
