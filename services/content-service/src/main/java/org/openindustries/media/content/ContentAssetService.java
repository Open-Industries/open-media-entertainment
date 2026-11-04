package org.openindustries.media.content;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ContentAssetService {

    @Inject
    ContentAssetRepository repository;

    public ContentAsset create(CreateContentAssetRequest request) {
        return repository.save(ContentAsset.create(request.title(), request.contentType(), request.registryCode()));
    }

    public ContentAsset get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new DomainException(404, "ContentAsset not found: " + id));
    }

    public List<ContentAsset> list() {
        return repository.findAll();
    }

    public ContentAsset publish(UUID id) {
        ContentAsset entity = get(id);
        if ("ARCHIVED".equals(entity.status)) {
            throw new DomainException(409, "A archived contentasset is terminal");
        }
        if ("PUBLISHED".equals(entity.status)) {
            throw new DomainException(409, "ContentAsset already PUBLISHED");
        }
        entity.status = "PUBLISHED";
        return repository.save(entity);
    }

    public ContentAsset archive(UUID id) {
        ContentAsset entity = get(id);
        if ("ARCHIVED".equals(entity.status)) {
            throw new DomainException(409, "A archived contentasset is terminal");
        }
        if ("ARCHIVED".equals(entity.status)) {
            throw new DomainException(409, "ContentAsset already ARCHIVED");
        }
        entity.status = "ARCHIVED";
        return repository.save(entity);
    }
}
