package org.openindustries.media.content;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Outbound port (hexagonal architecture). Default adapter is in-memory so the reference
 * implementation runs anywhere; the production adapter is JPA against this service's own
 * database (database-per-service, ADR-0003).
 */
@ApplicationScoped
public class ContentAssetRepository {
    private final ConcurrentHashMap<UUID, ContentAsset> store = new ConcurrentHashMap<>();

    public ContentAsset save(ContentAsset entity) {
        store.put(entity.id, entity);
        return entity;
    }

    public Optional<ContentAsset> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<ContentAsset> findAll() {
        return List.copyOf(store.values());
    }
}
