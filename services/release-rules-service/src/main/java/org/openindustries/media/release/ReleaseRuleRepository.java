package org.openindustries.media.release;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/** Outbound port; default in-memory adapter, production adapter is JPA on this service's own database. */
@ApplicationScoped
public class ReleaseRuleRepository {
    private final ConcurrentHashMap<UUID, ReleaseRule> store = new ConcurrentHashMap<>();

    public ReleaseRule save(ReleaseRule rule) {
        store.put(rule.id, rule);
        return rule;
    }

    public Optional<ReleaseRule> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<ReleaseRule> findAll() {
        return List.copyOf(store.values());
    }

    /** Active rules for a scope, highest priority (lowest number) first. */
    public List<ReleaseRule> activeFor(String territoryGroup) {
        return store.values().stream()
                .filter(r -> r.territoryGroup.equals(territoryGroup) && "ACTIVE".equals(r.status))
                .sorted(Comparator.comparingInt(r -> r.priority))
                .toList();
    }
}
