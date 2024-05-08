package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    /**
     * 新增员工
     * @param employee
     */
    @Insert(value = "insert into employee (name, username, password, phone, sex, id_number, create_time, update_time, create_user, update_user,status) values( #{name}, #{username}, #{password}, #{phone}, #{sex}, #{idNumber}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser},#{status})")
    void insert(Employee employee);

    /**分页查询，因为是动态sql 用注解不方便，写到配置文件里
     *
     * @param employeePageQueryDTO
     */
    Page<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 启用禁用员工account
     * @param employee
     */
    void update(Employee employee);

    @Select("select * from employee where id = #{id}")
    Employee getById(Long id);
}
