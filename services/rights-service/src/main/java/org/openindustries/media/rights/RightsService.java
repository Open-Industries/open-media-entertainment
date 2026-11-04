package org.openindustries.media.rights;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class RightsService {

    @Inject
    RightsRepository repository;

    public RightsWindow open(OpenRightsWindowRequest request) {
        return repository.saveAccount(RightsWindow.open(request.assetId(), request.licensedUnits()));
    }

    public RightsWindow get(UUID id) {
        return repository.findAccount(id)
                .orElseThrow(() -> new DomainException(404, "RightsWindow not found: " + id));
    }

    /**
     * Draws from the account. Invariant: the balance/capacity never goes negative —
     * an over-drawing operation is rejected (422) and nothing is recorded.
     */
    public DistributionUse consume(UUID accountId, CreateDistributionUseRequest request) {
        RightsWindow account = get(accountId);
        if (!"ACTIVE".equals(account.status)) {
            throw new DomainException(409, "RightsWindow is not ACTIVE");
        }
        if (request.units() > account.licensedUnits) {
            throw new DomainException(422, "Insufficient capacity: requested " + request.units()
                    + " exceeds available " + account.licensedUnits);
        }
        account.licensedUnits -= request.units();
        repository.saveAccount(account);
        return repository.saveRecord(DistributionUse.of(accountId, request.channel(), request.units(), account.licensedUnits));
    }

    public List<DistributionUse> recordsOf(UUID accountId) {
        get(accountId);
        return repository.recordsOf(accountId);
    }
}
