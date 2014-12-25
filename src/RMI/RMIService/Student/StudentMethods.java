package RMI.RMIService.Student;

import RMI.RMIBase.SDRemote;

/**
 * Created by amaliujia on 14-12-24.
 */
public interface StudentMethods extends SDRemote{
   public String listStudents();
   public String wishes(String name);
}
