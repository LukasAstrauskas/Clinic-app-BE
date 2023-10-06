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
    Optional<Occupation> getOccupationById(@Param("id") UUID id);

}
