package com.sourcery.clinicapp.notifications;

import com.sourcery.clinicapp.timeslot.model.dto.TimeslotFullDto;
import com.sourcery.clinicapp.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class EmailMessageHandler {

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private UserService userService;

    public ResponseEntity<String> getEmailMessage(TimeslotFullDto timeslotFullDto) {

    String toEmail = userService.getAUserById(timeslotFullDto.patientId()).getEmail();
    String emailSubject = "Appointment confirmation " + LocalDate.now();
    String physicianName = userService.getAUserById(timeslotFullDto.physicianId()).getName();
    String patientName =  userService.getAUserById(timeslotFullDto.patientId()).getName();
    String appointmentDate = timeslotFullDto.date() + ", " + timeslotFullDto.time();
    String emailMessage = "Hello, " + patientName + ",\n" +
            "\nYour appointment successfully confirmed!\n" +
            "\nPhysician name: " +physicianName + ";" +
            "\nAppointment date: " + appointmentDate + ";" +
            "\nIf you have any questions, please contact +37065468789; " +
            "\nIf you want to cancel appointment, please login http://localhost:3000/login;";

    emailSenderService.sendEmail(toEmail, emailSubject, emailMessage);

    return new ResponseEntity<>("Appointment confirmation successfully send!", HttpStatus.OK);
    }
}
