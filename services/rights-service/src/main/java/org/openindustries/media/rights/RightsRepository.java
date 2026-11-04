package org.openindustries.media.rights;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/** Outbound port; default in-memory adapter, production adapter is JPA on this service's own database. */
@ApplicationScoped
public class RightsRepository {
    private final ConcurrentHashMap<UUID, RightsWindow> accounts = new ConcurrentHashMap<>();
    private final CopyOnWriteArrayList<DistributionUse> records = new CopyOnWriteArrayList<>();

    public RightsWindow saveAccount(RightsWindow account) {
        accounts.put(account.id, account);
        return account;
    }

    public Optional<RightsWindow> findAccount(UUID id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public DistributionUse saveRecord(DistributionUse record) {
        records.add(record);
        return record;
    }

    public List<DistributionUse> recordsOf(UUID accountId) {
        return records.stream().filter(r -> r.accountId.equals(accountId)).toList();
    }
}
