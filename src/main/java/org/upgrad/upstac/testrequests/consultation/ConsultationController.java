package org.upgrad.upstac.testrequests.consultation;


import static org.upgrad.upstac.exception.UpgradResponseStatusException.asBadRequest;
import static org.upgrad.upstac.exception.UpgradResponseStatusException.asConstraintViolation;

import java.util.List;
import javax.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.upgrad.upstac.config.security.UserLoggedInService;
import org.upgrad.upstac.exception.AppException;
import org.upgrad.upstac.testrequests.RequestStatus;
import org.upgrad.upstac.testrequests.TestRequest;
import org.upgrad.upstac.testrequests.TestRequestQueryService;
import org.upgrad.upstac.testrequests.TestRequestUpdateService;
import org.upgrad.upstac.testrequests.flow.TestRequestFlowService;
import org.upgrad.upstac.users.User;


@RestController
@RequestMapping("/api/consultations")
public class ConsultationController {

  Logger log = LoggerFactory.getLogger(ConsultationController.class);

  @Autowired
  private TestRequestUpdateService testRequestUpdateService;

  @Autowired
  private TestRequestQueryService testRequestQueryService;

  @Autowired
  TestRequestFlowService testRequestFlowService;

  @Autowired
  private UserLoggedInService userLoggedInService;

  @GetMapping("/in-queue")
  @PreAuthorize("hasAnyRole('DOCTOR')")
  public List<TestRequest> getForConsultations() {
    return testRequestQueryService.findBy(RequestStatus.LAB_TEST_COMPLETED);
  }

  @GetMapping
  @PreAuthorize("hasAnyRole('DOCTOR')")
  public List<TestRequest> getForDoctor() {
    User doctor = userLoggedInService.getLoggedInUser();
    return testRequestQueryService.findByDoctor(doctor);
  }

  @PreAuthorize("hasAnyRole('DOCTOR')")
  @PutMapping("/assign/{id}")
  public TestRequest assignForConsultation(@PathVariable Long id) {
    try {
      User doctor = userLoggedInService.getLoggedInUser();
      return testRequestUpdateService.assignForConsultation(id, doctor);
    } catch (AppException e) {
      throw asBadRequest(e.getMessage());
    }
  }

  @PreAuthorize("hasAnyRole('DOCTOR')")
  @PutMapping("/update/{id}")
  public TestRequest updateConsultation(@PathVariable Long id,
      @RequestBody CreateConsultationRequest testResult) {
    try {
      User doctor = userLoggedInService.getLoggedInUser();
      return testRequestUpdateService.updateConsultation(id, testResult, doctor);
    } catch (ConstraintViolationException e) {
      throw asConstraintViolation(e);
    } catch (AppException e) {
      throw asBadRequest(e.getMessage());
    }
  }
}
