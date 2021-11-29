package org.upgrad.upstac.testrequests;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.Data;
import org.upgrad.upstac.testrequests.consultation.Consultation;
import org.upgrad.upstac.testrequests.lab.LabResult;
import org.upgrad.upstac.users.User;
import org.upgrad.upstac.users.models.Gender;

@Data
@Entity
public class TestRequest {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long requestId;

  @ManyToOne
  private User createdBy;

  private LocalDate created = LocalDate.now();

  private RequestStatus status = RequestStatus.INITIATED;


  private String name;
  private Gender gender;
  private String address;
  private Integer age;
  private String email;
  private String phoneNumber;
  private Integer pinCode;

  @OneToOne(mappedBy = "request")
  Consultation consultation;

  @OneToOne(mappedBy = "request")
  LabResult labResult;

}
