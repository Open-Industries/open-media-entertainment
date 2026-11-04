package org.openindustries.media.programming;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/** Outbound port; default in-memory adapter, production adapter is JPA on this service's own database. */
@ApplicationScoped
public class ProgrammingSlotRepository {
    private final ConcurrentHashMap<UUID, ProgrammingSlot> store = new ConcurrentHashMap<>();

    public ProgrammingSlot save(ProgrammingSlot entity) {
        store.put(entity.id, entity);
        return entity;
    }

    public Optional<ProgrammingSlot> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<ProgrammingSlot> findAll() {
        return List.copyOf(store.values());
    }
}
