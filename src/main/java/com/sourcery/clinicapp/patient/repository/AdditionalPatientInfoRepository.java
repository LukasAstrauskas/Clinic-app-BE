package com.sourcery.clinicapp.patient.repository;

import com.sourcery.clinicapp.patient.model.AdditionalPatientInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Mapper
@Repository
public interface AdditionalPatientInfoRepository {
    @Select("SELECT * FROM additional_patient_info WHERE user_id=#{id}")
    Optional<AdditionalPatientInfo> getAdditionalPatientInfo(@Param("id") UUID id);
    @Update("UPDATE additional_patient_info SET gender=#{patient.gender}, birth_date=#{patient.birth_date}, " +
            "phone=#{patient.phone}, street=#{patient.street}, city=#{patient.city}, " +
            "postal_code=#{patient.postal_code}, country=#{patient.country}, " +
            "emergency_name=#{patient.emergency_name}, emergency_surname=#{patient.emergency_surname}, " +
            "emergency_phone=#{patient.emergency_phone}, emergency_relation=#{patient.emergency_relation} " +
            "WHERE user_id=#{id}")
    void updateAdditionalPatientInfo(@Param("id") UUID id, @Param("patient") AdditionalPatientInfo patient );
}
