package org.openindustries.media.programming;

import java.util.UUID;

/** ProgrammingSlot aggregate — Programming slot catalogue for schedules. */
public class ProgrammingSlot {
    public UUID id;
        public String name;
        public String slotType;
        public int durationMinutes;
    public String status;

    public static ProgrammingSlot create(String name, String slotType, int durationMinutes) {
        ProgrammingSlot e = new ProgrammingSlot();
        e.id = UUID.randomUUID();
        e.name = name;
        e.slotType = slotType;
        e.durationMinutes = durationMinutes;
        e.status = "SCHEDULED";
        return e;
    }
}
