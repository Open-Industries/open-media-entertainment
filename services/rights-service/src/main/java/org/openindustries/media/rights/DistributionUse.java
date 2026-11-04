package org.openindustries.media.rights;

import java.time.Instant;
import java.util.UUID;

/** One guarded consumption record — the auditable evidence of a draw on a RightsWindow. */
public class DistributionUse {
    public UUID id;
    public UUID accountId;
    public String channel;
    public long units;
    public long remainingAfter;
    public Instant recordedAt;

    public static DistributionUse of(UUID accountId, String channel, long units, long remainingAfter) {
        DistributionUse r = new DistributionUse();
        r.id = UUID.randomUUID();
        r.accountId = accountId;
        r.channel = channel;
        r.units = units;
        r.remainingAfter = remainingAfter;
        r.recordedAt = Instant.now();
        return r;
    }
}
