package org.openindustries.media.content;

import java.time.Instant;
import java.util.UUID;

/**
 * ContentAsset aggregate — Content asset registry; the registry code is masked at registration.
 *
 * Privacy by design (ISO/IEC 27002 A.5.34): secret identifiers are validated at
 * creation and NEVER persisted or returned in clear — only a masked form is kept.
 * All identifiers in this reference implementation are synthetic.
 */
public class ContentAsset {
    public UUID id;
        public String title;
        public String contentType;
        public String registryCodeMasked;
    public String status;
    public Instant createdAt;

    public static ContentAsset create(String title, String contentType, String registryCode) {
        ContentAsset e = new ContentAsset();
        e.id = UUID.randomUUID();
        e.title = title;
        e.contentType = contentType;
        e.registryCodeMasked = mask(registryCode);
        e.status = "DRAFT";
        e.createdAt = Instant.now();
        return e;
    }

    static String mask(String value) {
        if (value == null || value.length() < 4) {
            return "****";
        }
        return "*".repeat(value.length() - 4) + value.substring(value.length() - 4);
    }
}
