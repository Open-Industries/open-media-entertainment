package org.openindustries.media.rights;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record CreateDistributionUseRequest(
        @NotBlank @Pattern(regexp = "STREAMING|BROADCAST|THEATRICAL|AIRLINE") String channel,
        @Positive long units) {
}
