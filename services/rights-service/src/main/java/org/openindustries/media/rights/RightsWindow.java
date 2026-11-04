package org.openindustries.media.rights;

import java.util.UUID;

/**
 * RightsWindow aggregate — the guarded resource account of this core: a balance/capacity that
 * consumption operations draw from. Core invariant (ADR-0004): it NEVER goes negative;
 * an operation that would overdraw it is rejected and records nothing.
 */
public class RightsWindow {
    public UUID id;
        public java.util.UUID assetId;
    public long licensedUnits;
    public String status;

    public static RightsWindow open(java.util.UUID assetId, long licensedUnits) {
        RightsWindow a = new RightsWindow();
        a.id = UUID.randomUUID();
        a.assetId = assetId;
        a.licensedUnits = licensedUnits;
        a.status = "ACTIVE";
        return a;
    }
}
