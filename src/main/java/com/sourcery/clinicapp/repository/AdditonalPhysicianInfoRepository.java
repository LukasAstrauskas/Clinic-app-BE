package com.sourcery.clinicapp.repository;


import com.sourcery.clinicapp.model.AdditionalPhysicianInfo;
import com.sourcery.clinicapp.model.Occupation;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Mapper
@Repository
public interface AdditonalPhysicianInfoRepository {
    @Select("SELECT * FROM additional_physician_info WHERE user_id=#{id}")
    Optional<Occupation> getPhysicianInfo(@Param("id") UUID id);


    @Insert("INSERT INTO additional_physician_info (user_id, occupation_id) VALUES (#{info.userId}, #{info.occupationId})")
    void insertInfo(@Param("info") AdditionalPhysicianInfo info);
}
