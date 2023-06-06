package ro.hiringsystem.model.dto.interview.messaging;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InterviewForceAction {

    @JsonProperty("type")
    private InterviewForceActionType type;
}
