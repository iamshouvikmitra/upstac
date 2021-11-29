package org.upgrad.upstac.testrequests.consultation;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateConsultationRequest {

  @NotNull
  private DoctorSuggestion suggestion;

  private String comments;
}
