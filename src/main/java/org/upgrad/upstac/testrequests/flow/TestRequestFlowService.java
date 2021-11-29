package org.upgrad.upstac.testrequests.flow;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.upgrad.upstac.testrequests.RequestStatus;
import org.upgrad.upstac.testrequests.TestRequest;
import org.upgrad.upstac.users.User;

@Service
@Validated
public class TestRequestFlowService {

  @Autowired
  private TestRequestFlowRepository testRequestFlowRepository;


  @Transactional
  public void log(TestRequest testRequest, RequestStatus from, RequestStatus to, User changedBy) {
    TestRequestFlow testRequestFlow = new TestRequestFlow();
    testRequestFlow.setChangedBy(changedBy);
    testRequestFlow.setRequest(testRequest);
    testRequestFlow.setFromStatus(from);
    testRequestFlow.setToStatus(to);
    testRequestFlowRepository.save(testRequestFlow);
  }


  public List<TestRequestFlow> findByRequest(TestRequest testRequest) {

    return testRequestFlowRepository.findByRequest(testRequest);
  }

}
