package com.souhaib.JDBCourse.dao;

import com.souhaib.JDBCourse.utils.Utils;
import com.souhaib.JDBCourse.model.Employee;
import com.souhaib.JDBCourse.dao.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.sql.Statement;
import java.sql.ResultSet;
public class EmployeeDaoImpl implements EmployeeDao{


    @Override
    public List<Employee> findAll() {
       Connection con =DBConnection.getConnection();
       if(con == null){
           return null;
       }
       String query = "select * from employee;";
       List<Employee> employees =new ArrayList<Employee>();
       try (PreparedStatement preparedStatement=con.prepareStatement(query)){
           ResultSet resultSet=preparedStatement.executeQuery();
           while (resultSet.next()){
               Employee employee=new Employee(resultSet.getInt("id"),resultSet.getString("name"),resultSet.getBoolean("gender"),
               resultSet.getDate("birth_date"),resultSet.getDouble("salary"));
              employees.add(employee);
           }
       }catch (SQLException e){
           e.printStackTrace();
       }finally {
           try {
               con.close();
           } catch (SQLException throwables) {
               throwables.printStackTrace();
           }
       }
       return employees;
    }

    @Override
    public Employee findById(int id) {
        Connection con =DBConnection.getConnection();
        if(con == null){
            return null;
        }
        String query = "select * from employee WHERE id=?;";
        try (PreparedStatement preparedStatement=con.prepareStatement(query)){
            preparedStatement.setInt(1,id);
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                Employee employee=new Employee(resultSet.getInt("id"),resultSet.getString("name"),resultSet.getBoolean("gender"),
                        resultSet.getDate("birth_date"),resultSet.getDouble("salary"));
                return employee;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                con.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void save(Employee employee) {
        Connection con = DBConnection.getConnection();
        if(con == null) {
            return;
        }

        if(employee.getId() > 0) { // Update
            String query = "UPDATE employee SET name=?, gender=?, birth_date=?, salary=? WHERE id=?;";
            try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
                preparedStatement.setString(1, employee.getName());
                preparedStatement.setBoolean(2, employee.isGender());
                preparedStatement.setDate(3, Utils.getSqlDate(employee.getBirthDate()));
                preparedStatement.setDouble(4, employee.getSalary());
                preparedStatement.setInt(5, employee.getId());

                preparedStatement.executeUpdate();
            } catch(SQLException se) {
                se.printStackTrace();
            } finally {
                try {
                    con.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }  else { // Create
        String query = "INSERT INTO employee (name, gender, birth_date, salary) VALUES (?, ?, ?, ?);;";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {

            preparedStatement.setString(1, employee.getName());
            preparedStatement.setBoolean(2, employee.isGender());
            preparedStatement.setDate(3, Utils.getSqlDate(employee.getBirthDate()));
            preparedStatement.setDouble(4, employee.getSalary());
            preparedStatement.executeUpdate();
        } catch(SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
    }

    @Override
    public void deleteById(int id) {
        Connection con = DBConnection.getConnection();
        if(con==null){
            return;
        }
        String query = "DELETE FROM employee WHERE id=?;";
        try (PreparedStatement preparedStatement=con.prepareStatement(query)){
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
        }catch (SQLException se){
            se.printStackTrace();
        }finally {
            try {
                con.close();
            }catch (SQLException throwables){
                throwables.printStackTrace();
            }
        }
    }
}