package org.openindustries.media.content;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record CreateContentAssetRequest(
        @NotBlank String title,
        @NotBlank @Pattern(regexp = "FILM|SERIES|MUSIC|LIVE_EVENT") String contentType,
        @NotBlank String registryCode) {
}
