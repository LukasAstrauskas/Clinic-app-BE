package com.sourcery.clinicapp.user.mapper;

import com.sourcery.clinicapp.user.mapper.sqlProvider.UserSqlProvider;
import com.sourcery.clinicapp.user.model.User;
import com.sourcery.clinicapp.user.model.UserDTO;
import org.apache.ibatis.annotations.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface UserMapper {

    @SelectProvider(type = UserSqlProvider.class, method = "userCountSQL")
    int getUserCount(@Param("type") String type);

    @Results(id = "userResult", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "name", column = "name"),
            @Result(property = "surname", column = "surname"),
            @Result(property = "email", column = "email"),
            @Result(property = "type", column = "type"),
            @Result(property = "occupation", column = "occupation_id",
                    one = @One(select = "com.sourcery.clinicapp.occupation.repository.OccupationMapper.getOccupationById"))
    })
    @Select("SELECT * FROM users WHERE users.id=#{id}")
    Optional<UserDTO> getUserById(@Param("id") String id);

    @ResultMap("userResult")
    @Select("SELECT * FROM users WHERE TYPE = #{userType} LIMIT 5 OFFSET #{offset}")
    Collection<UserDTO> getUsers(int offset, String userType);


    @ResultMap("userResult")
    @SelectProvider(type = UserSqlProvider.class, method = "userSearchSQL")
    Collection<UserDTO> userSearch(@Param("search") String search, @Param("occupationId") String occupationId, @Param("type") String type);


    @Select(" SELECT DISTINCT users.id, users.name, users.surname, users.email, users.type FROM timeslot " +
            "LEFT JOIN users ON users.id = timeslot.patient_id WHERE timeslot.physician_id = #{physicianId}" +
            " ORDER BY surname LIMIT 7 OFFSET #{offset}")
    List<UserDTO> getPhysicianPatients(@Param("physicianId") String physicianId, @Param("offset") int offset);

    @Select("SELECT COUNT(DISTINCT patient_id ) FROM timeslot WHERE physician_id = #{physicianId}")
    int amountOfPhysicianPatients(@Param("physicianId") String physicianId);

    @Delete("DELETE FROM users WHERE id=#{uuid}")
    boolean deleteUserById(@Param("uuid") String uuid);

    @Insert("INSERT INTO users (id, name, surname, email, password, type, occupation_id) VALUES" +
            " (#{user.id}, #{user.name}, #{user.surname}, #{user.email}, #{user.password}, #{user.type}, #{user.occupationId})")
    boolean insertUser(@Param("user") User user);

    @Update("UPDATE users SET name=#{user.name}, surname=#{user.surname}, email=#{user.email}," +
            " occupation_id=#{user.occupationId} WHERE id=#{user.id}")
    void updateUser(@Param("user") User user);

    @Update("UPDATE users SET password=#{password} WHERE id=#{id} ")
    void updatePassword(@Param("password") String password, @Param("id") String id);

    @Select("SELECT * FROM users WHERE email=#{email}")
    Optional<User> findByEmail(@Param("email") String email);
}
