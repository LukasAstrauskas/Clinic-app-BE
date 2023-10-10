package com.sourcery.clinicapp.timeslot.service;

import org.springframework.stereotype.Component;

@Component
public class TimeslotDataHelper {

//    public List<TimeslotsDto> groupTimeslotsByDate(Collection<Timeslot> physicianTimeslots, UUID physicianId) {
//        List<TimeslotsDto> timeslotsList = new ArrayList<>();
//        Map<String, List<TimeslotFullDto>> groupedByDate = physicianTimeslots.stream().map(timeslot -> {
//            String date = DateTimeHelper.toDateString(timeslot.getDate());
//            String time = DateTimeHelper.toTimeString(timeslot.getDate());
//            UUID patientId = timeslot.getPatientId();
//            UUID timeslotId = timeslot.getId();
//            return new TimeslotFullDto(timeslotId, physicianId, date, time, patientId);
//        }).collect(groupingBy(TimeslotFullDto::date));
//        groupedByDate.forEach((date, timeslotDTOs) -> {
//            List<TimePatientDto> collect = timeslotDTOs.stream()
//                    .map(timeslotFullDto -> new TimePatientDto(
//                            timeslotFullDto.time(),
//                            timeslotFullDto.patientId()
//                    )).collect(Collectors.toList());
//            timeslotsList.add(new TimeslotsDto(date, collect));
//        });
//        return timeslotsList.stream().sorted(Comparator.comparing(TimeslotsDto::date)).collect(Collectors.toList());
//    }

}
