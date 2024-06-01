package com.vicious.finallib.common.guisync;

import java.util.Objects;

public record SlotIndex(int inventory, int slotIndex) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SlotIndex slotIndex1 = (SlotIndex) o;
        return inventory == slotIndex1.inventory && slotIndex == slotIndex1.slotIndex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(inventory, slotIndex);
    }
}
