package com.sourcery.clinicapp.repository;

import com.sourcery.clinicapp.model.Occupation;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
@Repository
public interface OccupationRepository {

    @Select("SELECT * FROM occupations")
    List<Occupation> getAllOccupations();


    @Select("SELECT * FROM occupations WHERE id=#{id}")
    Optional<Occupation> getOccupation(@Param("id") UUID id);

}
