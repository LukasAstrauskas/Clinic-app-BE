package com.sourcery.clinicapp.physicianInfo.repository;


import com.sourcery.clinicapp.occupation.model.Occupation;
import com.sourcery.clinicapp.physicianInfo.model.PhysicianInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Mapper
@Repository
public interface PhysicianInfoRepository {
    @Select("SELECT * FROM additional_physician_info WHERE user_id=#{id}")
    Optional<Occupation> getPhysicianInfo(@Param("id") UUID id);

    @Insert("INSERT INTO additional_physician_info (user_id, occupation_id) VALUES (#{info.userId}, #{info.occupationId})")
    void insertPhysicianInfo(@Param("info") PhysicianInfo info);

    @Update("UPDATE additional_physician_info SET occupation_id=#{occupationID} WHERE  user_id=#{physicianID}")
    void updatePhysicianInfo(@Param("occupationID") UUID occupationID, @Param("physicianID") UUID physicianID);

    @Delete("DELETE FROM additional_physician_info WHERE user_id=#{id}")
    void deletePhysicianInfo(@Param("id") UUID userId);


}


