package com.sourcery.clinicapp.patientInfo.repository;

import com.sourcery.clinicapp.patientInfo.model.PatientInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Mapper
@Repository
public interface PatientInfoRepository {
    @Select("SELECT user_id as userId, gender, birth_date as birthDate, phone, street, city, " +
            "postal_code as postalCode, country, emergency_name as emergencyName, " +
            "emergency_last_name as emergencyLastName, emergency_phone as emergencyPhone, " +
            "emergency_relation as emergencyRelation FROM additional_patient_info " +
            "WHERE user_id=#{id}")
    Optional<PatientInfo> getPatientInfo(@Param("id") String id);

    @Update("UPDATE additional_patient_info SET gender=#{info.gender}, birth_date=#{info.birthDate}, " +
            "phone=#{info.phone}, street=#{info.street}, city=#{info.city}, " +
            "postal_code=#{info.postalCode}, country=#{info.country}, " +
            "emergency_name=#{info.emergencyName}, emergency_last_name=#{info.emergencyLastName}, " +
            "emergency_phone=#{info.emergencyPhone}, emergency_relation=#{info.emergencyRelation} " +
            "WHERE user_id=#{id}")
    boolean updatePatientInfo(@Param("id") String id, @Param("info") PatientInfo info);

    @Insert("INSERT INTO additional_patient_info (user_id, gender, birth_date, phone, street, city, " +
            "postal_code, country, emergency_name, emergency_last_name, emergency_phone, emergency_relation) VALUES " +
            "(#{info.userId}, #{info.gender}, #{info.birthDate}, #{info.phone}, " +
            "#{info.street}, #{info.city}, #{info.postalCode}, #{info.country}, " +
            "#{info.emergencyName}, #{info.emergencyLastName}, #{info.emergencyPhone}, " +
            "#{info.emergencyRelation})")
    boolean createPatientInfo(@Param("info") PatientInfo info);
}
