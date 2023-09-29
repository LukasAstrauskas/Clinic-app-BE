package com.sourcery.clinicapp.occupation.repository;

import com.sourcery.clinicapp.occupation.model.Occupation;
import org.apache.ibatis.annotations.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface OccupationMapper {

    @Select("SELECT * FROM occupations")
    Collection<Occupation> getAllOccupations();

    @Select("SELECT * FROM occupations WHERE id=#{id} ")
    Optional<Occupation> findById(@Param("id") UUID id);

    @ResultType(Occupation.class)
    @Select("SELECT * FROM occupations WHERE id=#{id} ")
    Occupation selectOccupation(@Param("id") UUID occupation_id);

}
