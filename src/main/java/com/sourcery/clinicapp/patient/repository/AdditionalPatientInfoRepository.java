package com.sourcery.clinicapp.patient.repository;

import com.sourcery.clinicapp.patient.model.AdditionalPatientInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Mapper
@Repository
public interface AdditionalPatientInfoRepository {
    @Select("SELECT * FROM additional_patient_info WHERE user_id=#{id}")
    Optional<AdditionalPatientInfo> getAdditionalPatientInfo(@Param("id") UUID id);
}
