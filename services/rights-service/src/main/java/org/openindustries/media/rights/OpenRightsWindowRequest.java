package org.openindustries.media.rights;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record OpenRightsWindowRequest(
        @NotNull java.util.UUID assetId,
        @PositiveOrZero long licensedUnits) {
}
