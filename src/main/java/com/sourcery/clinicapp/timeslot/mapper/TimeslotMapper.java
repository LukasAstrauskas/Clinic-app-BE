package com.sourcery.clinicapp.timeslot.mapper;

import com.sourcery.clinicapp.timeslot.model.Timeslot;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface TimeslotMapper {

    @Select("SELECT * FROM TIMESLOT")
    Collection<Timeslot> getAltTimeslots();

    @Select("SELECT * FROM TIMESLOT WHERE physicianid = #{physicianId}")
    Collection<Timeslot> getPhysicianTimeslots(UUID physicianId);

    @Select("SELECT * FROM TIMESLOT WHERE physicianid = #{physicianId} AND date=#{dateTime}")
    Optional<Timeslot> getTimeslot(UUID physicianId, LocalDateTime dateTime);

    @Insert("INSERT INTO timeslot (physicianId, date) VALUES(#{physicianId}, #{date})")
    boolean addTimeslot(Timeslot timeslot);

    @Delete("DELETE FROM timeslot WHERE physicianid = #{physicianId} AND date=#{dateTime}")
    boolean deleteTimeslot(UUID physicianId, LocalDateTime dateTime);
}