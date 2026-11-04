package org.openindustries.media.programming;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ProgrammingSlotService {

    @Inject
    ProgrammingSlotRepository repository;

    public ProgrammingSlot create(CreateProgrammingSlotRequest request) {
        return repository.save(ProgrammingSlot.create(request.name(), request.slotType(), request.durationMinutes()));
    }

    public ProgrammingSlot get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new DomainException(404, "ProgrammingSlot not found: " + id));
    }

    public List<ProgrammingSlot> list() {
        return repository.findAll();
    }

    public ProgrammingSlot air(UUID id) {
        ProgrammingSlot entity = get(id);
        if (!"SCHEDULED".equals(entity.status)) {
            throw new DomainException(409, "Only a SCHEDULED programmingslot can transition via air");
        }
        entity.status = "AIRED";
        return repository.save(entity);
    }
}
