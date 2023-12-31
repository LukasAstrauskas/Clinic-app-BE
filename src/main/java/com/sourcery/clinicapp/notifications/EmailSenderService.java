package com.sourcery.clinicapp.notifications;


import com.sourcery.clinicapp.timeslot.model.dto.TimeslotDTO;
import com.sourcery.clinicapp.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class EmailSenderService {

    @Autowired
    private EmailNotificationConfig emailNotificationConfig;

    @Autowired
    private UserService userService;

    public void getEmailMessage(TimeslotDTO timeslotFullDto) {

//    String toEmail = userService.getUserById(timeslotFullDto.patientId()).getEmail();
    String toEmail = "medclinicMock@gmail.com";
    String emailSubject = "Appointment confirmation " + LocalDate.now();
    String physicianName = userService.getUserById(timeslotFullDto.getPhysicianId()).getName();
    String patientName =  userService.getUserById(timeslotFullDto.getPatientId()).getName();
    String appointmentDate = timeslotFullDto.getDate();
    String emailMessage = "Hello, " + patientName + ",\n" +
            "\nYour appointment successfully confirmed!\n" +
            "\nPhysician name: " +physicianName + ";" +
            "\nAppointment date: " + appointmentDate + ";" +
            "\nIf you have any questions, please contact +37065468789; " +
            "\nIf you want to cancel appointment, please login http://localhost:3000/login;";

    emailNotificationConfig.sendEmail(toEmail, emailSubject, emailMessage);
    }
}
