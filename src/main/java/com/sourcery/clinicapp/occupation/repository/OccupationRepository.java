package com.sourcery.clinicapp.occupation.repository;

import com.sourcery.clinicapp.occupation.model.Occupation;
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

    @Select("SELECT * FROM occupations WHERE id=#{id} ")
    Optional<Occupation> findById(@Param("id") UUID id);
}
