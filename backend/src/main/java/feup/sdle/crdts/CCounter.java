package feup.sdle.crdts;

import feup.sdle.message.CCounterProto;
import feup.sdle.message.DottedValueProto;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;

public class CCounter {
    private HashSet<DottedValue<Integer, Integer, Integer>> set;
    private final int id;

    public CCounter(int id) {
        this.id = id;
        this.set = new HashSet<>();
    }

    public HashSet<DottedValue<Integer, Integer, Integer>> getSet() {
        return this.set;
    }

    public void setSet(HashSet<DottedValue<Integer, Integer, Integer>> set) {
        this.set = set;
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
            if (value > 0)
                this.set.add(new DottedValue<>(this.id, 1, value));
            else
                this.set.add(new DottedValue<>(this.id, 1, this.getValue() + value < 0 ? -this.getValue() : value));
        }
        else {
            DottedValue<Integer, Integer, Integer> dv = optDV.get();

            if (value > 0)
                this.set.add(new DottedValue<>(this.id, dv.event() + 1, dv.value() + value));
            else
                this.set.add(new DottedValue<>(this.id, dv.event() + 1, this.getValue() + value < 0 ? dv.value() - this.getValue() : dv.value() + value));

            this.set.remove(dv);
        }
    }

    /**
     * Merges a CCounter into myself.
     */
    //TODO: find a way to fix when two reset counters are merged (see testMergeResetCounters)
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

    public CCounterProto.CCounter toMessageCCounter() {
        var builder = CCounterProto.CCounter.newBuilder()
                .setId(this.id);

        for (DottedValue<Integer, Integer, Integer> dv : this.set) {
            builder.addSet(DottedValueProto.DottedValue.newBuilder()
                    .setIdentifier(dv.identifier())
                    .setEvent(dv.event())
                    .setValueInt(dv.value())
            );
        }

        return builder.build();
    }

    public static CCounter fromMessageCCounter(CCounterProto.CCounter msgCCounter) {
        CCounter cCounter = new CCounter(msgCCounter.getId());

        HashSet<DottedValue<Integer, Integer, Integer>> set = new HashSet<>();
        for (DottedValueProto.DottedValue dv : msgCCounter.getSetList()) {
            set.add(DottedValue.fromMessageDottedValueInt(dv));
        }

        cCounter.setSet(set);

        return cCounter;
    }
}
