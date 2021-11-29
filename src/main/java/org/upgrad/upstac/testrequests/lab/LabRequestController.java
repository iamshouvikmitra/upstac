package org.upgrad.upstac.testrequests.lab;


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
@RequestMapping("/api/labrequests")
public class LabRequestController {

  Logger log = LoggerFactory.getLogger(LabRequestController.class);

  @Autowired
  private TestRequestUpdateService testRequestUpdateService;

  @Autowired
  private TestRequestQueryService testRequestQueryService;

  @Autowired
  private TestRequestFlowService testRequestFlowService;

  @Autowired
  private UserLoggedInService userLoggedInService;


  @GetMapping("/to-be-tested")
  @PreAuthorize("hasAnyRole('TESTER')")
  public List<TestRequest> getForTests() {
    return testRequestQueryService.findBy(RequestStatus.INITIATED);
  }

  @GetMapping
  @PreAuthorize("hasAnyRole('TESTER')")
  public List<TestRequest> getForTester() {
    User tester = userLoggedInService.getLoggedInUser();
    return testRequestQueryService.findByTester(tester);
  }


  @PreAuthorize("hasAnyRole('TESTER')")
  @PutMapping("/assign/{id}")
  public TestRequest assignForLabTest(@PathVariable Long id) {
    User tester = userLoggedInService.getLoggedInUser();
    return testRequestUpdateService.assignForLabTest(id, tester);
  }

  @PreAuthorize("hasAnyRole('TESTER')")
  @PutMapping("/update/{id}")
  public TestRequest updateLabTest(@PathVariable Long id,
      @RequestBody CreateLabResult createLabResult) {
    try {
      User tester = userLoggedInService.getLoggedInUser();
      return testRequestUpdateService.updateLabTest(id, createLabResult, tester);
    } catch (ConstraintViolationException e) {
      throw asConstraintViolation(e);
    } catch (AppException e) {
      throw asBadRequest(e.getMessage());
    }
  }


}
