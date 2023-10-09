package controller;

import java.util.Date;
import javax.sql.DataSource;

import model.*;

public interface AccountDAO {
  public void setDataSource(DataSource ds);
  
  public void create(int id_account_code, String email, String password, String name, String phone, String province_code, String register_date, int status, Date last_login_date, int gender, String random_code);
  
  public Account getAccount(String email);
  
  public boolean checkAccount(String email);
  
  
//  public List<AccountUser> listAccountUser();
// 	Cấu trúc:
  
//  public List<Student> listStudents() {
//      String SQL = "select * from Student";
//      List <Student> students = jdbcTemplateObject.query(SQL, new StudentMapper());
//      return students;
//   }
//  
//  public void delete(String user, String password);
//  Cấu trúc:
  
//  public void delete(Integer id) {
//      String SQL = "delete from Student where id = ?";
//      jdbcTemplateObject.update(SQL, id);
//      System.out.println("Deleted Record with ID = " + id );
//      return;
//   }
//  
//  public void update(String user, String password); --> Edit, sửa
//  Cấu trúc:
//  public void update(Integer id, Integer age){
//      String SQL = "update Student set age = ? where id = ?";
//      jdbcTemplateObject.update(SQL, age, id);
//      System.out.println("Updated Record with ID = " + id );
//      return;
//   }
  
}
