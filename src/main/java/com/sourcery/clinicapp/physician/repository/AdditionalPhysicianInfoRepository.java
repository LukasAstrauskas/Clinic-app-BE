package com.sourcery.clinicapp.physician.repository;


import com.sourcery.clinicapp.physician.model.AdditionalPhysicianInfo;
import com.sourcery.clinicapp.occupation.model.Occupation;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Mapper
@Repository
public interface AdditionalPhysicianInfoRepository {
    @Select("SELECT * FROM additional_physician_info WHERE user_id=#{id}")
    Optional<Occupation> getPhysicianInfo(@Param("id") UUID id);

    @Insert("INSERT INTO additional_physician_info (user_id, occupation_id) VALUES (#{info.userId}, #{info.occupationId})")
    void insertInfo(@Param("info") AdditionalPhysicianInfo info);

    @Delete("""
            DELETE FROM additional_physician_info WHERE user_id=#{id};
            DELETE FROM users WHERE id=#{id};
            """)
    void deletePhysicianById(@Param("id") UUID id);



}


