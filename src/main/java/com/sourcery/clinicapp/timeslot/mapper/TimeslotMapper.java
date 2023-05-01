package com.sourcery.clinicapp.timeslot.mapper;

import com.sourcery.clinicapp.timeslot.model.Timeslot;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface TimeslotMapper {

    @Select("SELECT * FROM timeslot")
    Collection<Timeslot> getAltTimeslots();

    @Select("SELECT * FROM timeslot WHERE physicianid = #{physicianId}")
    Collection<Timeslot> getPhysicianTimeslots(UUID physicianId);

    @Select("SELECT * FROM timeslot WHERE physicianid = #{physicianId} AND date = #{dateTime}")
    Optional<Timeslot> getTimeslot(UUID physicianId, LocalDateTime dateTime);

    @Select("SELECT COUNT(*) FROM timeslot WHERE patientid = #{patientId} AND physicianid = #{physicianId} AND date >= now()")
    Long countUpcomingTimeslotsWithPhysician(@Param("physicianId") UUID physicianId, @Param("patientId") UUID patientId);

    @Insert("INSERT INTO timeslot (physicianId, date) VALUES(#{physicianId}, #{date})")
    boolean addTimeslot(Timeslot timeslot);

    @Update("UPDATE timeslot SET patientid = #{patientId}" +
            " WHERE physicianid = #{physicianId} AND date = #{date} AND patientid IS NULL")
    boolean updateTimeslotSetPatientID(Timeslot timeslot);

    @Update("UPDATE timeslot SET patientid = NULL " +
            " WHERE physicianid = #{physicianId} AND patientid = #{patientId}")
    boolean  removePatientFromTimeslot(@Param("physicianId") UUID physicianId, @Param("patientId") UUID patientId);

    @Delete("DELETE FROM timeslot WHERE physicianid = #{physicianId} AND date=#{date} AND patientid IS NULL")
    boolean deleteTimeslot(Timeslot timeslot);
}