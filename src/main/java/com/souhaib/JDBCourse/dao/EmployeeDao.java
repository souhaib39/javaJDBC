package com.souhaib.JDBCourse.dao;
import com.souhaib.JDBCourse.model.Employee;
import java.util.List;
public interface EmployeeDao {
    List<Employee> findAll();

    Employee findById(int id);

    void save(Employee employee);

    void deleteById(int id);

  //  void save(Employee employee);
}
