package com.sourcery.clinicapp.patient.repository;

import com.sourcery.clinicapp.patient.model.AdditionalPatientInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Mapper
@Repository
public interface AdditionalPatientInfoRepository {
    @Select("SELECT user_id as userId, gender, birth_date as birthDate, phone, street, city, " +
            "postal_code as postalCode, country, emergency_name as emergencyName, " +
            "emergency_last_name as emergencyLastName, emergency_phone as emergencyPhone, " +
            "emergency_relation as emergencyRelation FROM additional_patient_info " +
            "WHERE user_id=#{id}")
    Optional<AdditionalPatientInfo> getAdditionalPatientInfo(@Param("id") UUID id);
    @Update("UPDATE additional_patient_info SET gender=#{patient.gender}, birth_date=#{patient.birthDate}, " +
            "phone=#{patient.phone}, street=#{patient.street}, city=#{patient.city}, " +
            "postal_code=#{patient.postalCode}, country=#{patient.country}, " +
            "emergency_name=#{patient.emergencyName}, emergency_last_name=#{patient.emergencyLastName}, " +
            "emergency_phone=#{patient.emergencyPhone}, emergency_relation=#{patient.emergencyRelation} " +
            "WHERE user_id=#{id}")
    boolean updateAdditionalPatientInfo(@Param("id") UUID id, @Param("patient") AdditionalPatientInfo patient );
    @Insert("INSERT INTO additional_patient_info (user_id, gender, birth_date, phone, street, city, " +
            "postal_code, country, emergency_name, emergency_last_name, emergency_phone, emergency_relation) " +
            "VALUES " +
            "(#{patient.userId}, #{patient.gender}, #{patient.birthDate}, #{patient.phone}, " +
            "#{patient.street}, #{patient.city}, #{patient.postalCode}, #{patient.country}, " +
            "#{patient.emergencyName}, #{patient.emergencyLastName}, #{patient.emergencyPhone}, " +
            "#{patient.emergencyRelation})")
    boolean createAdditionalPatientInfo(@Param("patient") AdditionalPatientInfo patient);
}
