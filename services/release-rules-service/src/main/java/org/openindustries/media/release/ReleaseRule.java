package org.openindustries.media.release;

import java.util.UUID;

/**
 * ReleaseRule aggregate — a decision rule of this core: for one territoryGroup, it guarantees a
 * capacity (maxSimultaneousReleases) and a threshold (maxPiracyIncidents), with a precedence priority.
 * Only ACTIVE rules decide; every decision names its rule and its reason (audit).
 */
public class ReleaseRule {
    public UUID id;
    public String name;
    public String territoryGroup;
    public long maxSimultaneousReleases;
    public int maxPiracyIncidents;
    public int priority;
    public String status;

    public static ReleaseRule create(String name, String territoryGroup, long maxSimultaneousReleases, int maxPiracyIncidents, int priority) {
        ReleaseRule r = new ReleaseRule();
        r.id = UUID.randomUUID();
        r.name = name;
        r.territoryGroup = territoryGroup;
        r.maxSimultaneousReleases = maxSimultaneousReleases;
        r.maxPiracyIncidents = maxPiracyIncidents;
        r.priority = priority;
        r.status = "DRAFT";
        return r;
    }
}
