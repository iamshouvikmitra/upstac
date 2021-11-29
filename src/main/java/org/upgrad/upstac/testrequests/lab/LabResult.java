package org.upgrad.upstac.testrequests.lab;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.Data;
import lombok.ToString;
import org.upgrad.upstac.testrequests.TestRequest;
import org.upgrad.upstac.users.User;

@Data
@Entity
public class LabResult {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long resultId;

  @OneToOne(fetch = FetchType.LAZY)
  @JsonIgnore
  @ToString.Exclude
  private TestRequest request;

  private String bloodPressure;
  private String heartBeat;
  private String temperature;
  private String oxygenLevel;
  private String comments;
  private TestStatus result;
  private LocalDate updatedOn;

  @ManyToOne
  private User tester;


}
