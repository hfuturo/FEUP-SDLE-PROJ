package feup.sdle.crdts;

import java.util.HashSet;
import java.util.Optional;

public class CCounter {
    private final HashSet<DottedValue<Integer, Integer, Integer>> set;
    private final int id;

    public CCounter(int id) {
        this.id = id;
        this.set = new HashSet<>();
    }

    public HashSet<DottedValue<Integer, Integer, Integer>> getSet() {
        return this.set;
    }

    public int getValue() {
        int sum = 0;

        for (DottedValue<Integer, Integer, Integer> dv : set) {
            sum += dv.value();
        }

        return sum;
    }

    public void update(int value) {
        Optional<DottedValue<Integer, Integer, Integer>> optDV = this.find(this.id);

        if (optDV.isEmpty()) {
            this.set.add(new DottedValue<>(this.id, 1, Math.max(0, value)));
        }
        else {
            DottedValue<Integer, Integer, Integer> dv = optDV.get();
            this.set.remove(dv);
            this.set.add(new DottedValue<>(this.id, dv.event() + 1, Math.max(0, dv.value() + value)));
        }
    }

    /**
     * Merges a CCounter into myself.
     */
    public void merge(CCounter other) {
        var otherSet = other.getSet();

        for (var otherDv : otherSet) {
            var optDv = this.find(otherDv.identifier());

            // other node has info we do not have
            if (optDv.isEmpty()) {
                this.set.add(otherDv);
                continue;
            }

            var dv = optDv.get();

            // we have a more recent event so we skip
            if (dv.event() >= otherDv.event()) continue;

            // other node has a more recent event, so we replace ours
            this.set.remove(dv);
            this.set.add(otherDv);
        }
    }

    private Optional<DottedValue<Integer, Integer, Integer>> find(Integer identifier) {

        for (DottedValue<Integer, Integer, Integer> dv : this.set) {
            if (dv.identifier().equals(identifier)) {
                return Optional.of(dv);
            }
        }

        return Optional.empty();
    }
}
