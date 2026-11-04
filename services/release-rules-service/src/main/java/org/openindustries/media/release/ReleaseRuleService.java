package org.openindustries.media.release;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ReleaseRuleService {

    @Inject
    ReleaseRuleRepository repository;

    public ReleaseRule create(CreateReleaseRuleRequest request) {
        return repository.save(ReleaseRule.create(request.name(), request.territoryGroup(),
                request.maxSimultaneousReleases(), request.maxPiracyIncidents(), request.priority()));
    }

    public ReleaseRule get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new DomainException(404, "ReleaseRule not found: " + id));
    }

    public List<ReleaseRule> list() {
        return repository.findAll();
    }

    public ReleaseRule activate(UUID id) {
        ReleaseRule rule = get(id);
        rule.status = "ACTIVE";
        return repository.save(rule);
    }

    public Evaluation evaluate(EvaluateRequest request) {
        var candidates = repository.activeFor(request.territoryGroup());
        if (candidates.isEmpty()) {
            return new Evaluation("DENIED", null,
                    "No active rule for " + request.territoryGroup());
        }
        for (ReleaseRule rule : candidates) {
            if (rule.maxPiracyIncidents <= request.requiredMaxPiracyIncidents()
                    && rule.maxSimultaneousReleases >= request.requiredMaxSimultaneousReleases()) {
                return new Evaluation("ALLOWED", rule.id,
                        "Rule '" + rule.name + "' covers the request");
            }
        }
        return new Evaluation("DEGRADED", candidates.get(0).id,
                "Active rules exist for " + request.territoryGroup() + " but none meets the requirement");
    }
}
