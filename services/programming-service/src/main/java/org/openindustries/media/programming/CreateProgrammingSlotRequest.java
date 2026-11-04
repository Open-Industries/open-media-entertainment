package org.openindustries.media.programming;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record CreateProgrammingSlotRequest(
        @NotBlank String name,
        @NotBlank @Pattern(regexp = "PRIME_TIME|DAYTIME|LATE_NIGHT") String slotType,
        @Positive int durationMinutes) {
}
