package com.sourcery.clinicapp.repository;

import com.sourcery.clinicapp.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface UserRepository {

    @Insert("INSERT INTO users (id, name, email, password, type) VALUES (#{user.id}, #{user.name}, #{user.email}, #{user.password}, #{user.type})")
    void InsertUsers(@Param("user") User user);


    @Select("SELECT * FROM users WHERE type='patient'")
    List<User> getPatients();

    @Select("SELECT * FROM users WHERE type='physician'")
    List<User> getPhysicians();

}
