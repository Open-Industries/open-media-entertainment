package org.openindustries.media.release;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record EvaluateRequest(
        @NotBlank @Pattern(regexp = "DOMESTIC|LATAM|EUROPE|GLOBAL") String territoryGroup,
        @Positive int requiredMaxPiracyIncidents,
        @Positive long requiredMaxSimultaneousReleases) {
}
