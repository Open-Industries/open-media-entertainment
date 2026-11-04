package org.openindustries.media.release;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record CreateReleaseRuleRequest(
        @NotBlank String name,
        @NotBlank @Pattern(regexp = "DOMESTIC|LATAM|EUROPE|GLOBAL") String territoryGroup,
        @Positive long maxSimultaneousReleases,
        @Positive int maxPiracyIncidents,
        @Min(1) @Max(100) int priority) {
}
