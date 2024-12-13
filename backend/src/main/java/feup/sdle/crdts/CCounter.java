package feup.sdle.crdts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import feup.sdle.message.CCounterProto;
import feup.sdle.message.DottedValueProto;

import java.util.HashSet;
import java.util.Optional;

public class CCounter {
    private HashSet<DottedValue<Integer, Integer, Integer>> set;
    private final int identifier;

    public CCounter(int identifier, HashSet<DottedValue<Integer, Integer, Integer>> set) {
        this.identifier = identifier;
        this.set = set;
    }

    @JsonCreator
    public static CCounter fromJson(@JsonProperty("identifier") int identifier, @JsonProperty("set") HashSet<DottedValue<Integer, Integer, Integer>> set) {
        return new CCounter(identifier, set);
    }

    public CCounter(int identifier) {
        this.identifier = identifier;
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
        Optional<DottedValue<Integer, Integer, Integer>> optDV = this.find(this.identifier);

        if (optDV.isEmpty()) {
            if (value > 0)
                this.set.add(new DottedValue<>(this.identifier, 1, value));
            else
                this.set.add(new DottedValue<>(this.identifier, 1, this.getValue() + value < 0 ? -this.getValue() : value));
        }
        else {
            DottedValue<Integer, Integer, Integer> dv = optDV.get();

            if (value > 0)
                this.set.add(new DottedValue<>(this.identifier, dv.event() + 1, dv.value() + value));
            else
                this.set.add(new DottedValue<>(this.identifier, dv.event() + 1, this.getValue() + value < 0 ? dv.value() - this.getValue() : dv.value() + value));

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
                .setId(this.identifier);

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

    public int getIdentifier() {
        return this.identifier;
    }
}
