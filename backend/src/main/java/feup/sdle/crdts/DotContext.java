package feup.sdle.crdts;

import com.fasterxml.jackson.annotation.JsonProperty;
import feup.sdle.message.DotContextProto;

import java.beans.IntrospectionException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * This is used in order to compared how fresh the event knowledge is between two different replicas.
 * We store the replica id along with the current event counter in order to be able to compare that
 * freshness.
 *
 * You can think of this as a vector clock implementation
 */
public class DotContext {
    @JsonProperty("dots")
    private HashMap<Integer, Integer> dots;

    public DotContext(@JsonProperty("dots") HashMap<Integer, Integer> dots) {
        this.dots = Objects.requireNonNullElseGet(dots, HashMap::new);
    }

    public DotContext(int localIdentifier) {
        this.dots = new HashMap<Integer, Integer>();
    }

    public HashMap<Integer, Integer> getDots() {
        return this.dots;
    }

    public void setDots(HashMap<Integer, Integer> dots) {
        this.dots = dots;
    }

    /**
     * This will be used to get the maximum counter value the local replica has of another replica
     * which will be extremely when merging the both of them
     */
    public Optional<Integer> maxOfReplica(int identifier) {
        return Optional.of(this.dots.get(identifier));
    }

    public Optional<Integer> latestReplicaDot(Integer identifier) {
       if(this.dots.get(identifier) != null) {
           return Optional.of(this.dots.get(identifier));
       }

       return Optional.empty();
    }

    /**
     * This is used to get the new entry on the map related to a certain
     * replica identifier
     */
    public Integer nextOfReplica(Integer identifier) {
        Integer counter = this.dots.get(identifier);

        if (counter == null) {
            this.dots.put(identifier, 1);

            return 1;
        } else {
            this.dots.put(identifier, counter + 1);

            return counter + 1;
        }
    }

    /**
     * This is used to determine if a certain replica identifer has
     * surpassed a certain event number (the dot)
     */
    public boolean has(Integer identifier, Integer dot) {
        Integer value = this.dots.get(identifier);

        if(value == null) {
            return false;
        }

        return value >= dot;
    }

    /**
     * This merges the current DotContext with another one from another replica
     */
    public void merge(DotContext other) {
        for(Map.Entry<Integer, Integer> dotEntry: other.dots.entrySet()) {
            Integer otherDot = dotEntry.getValue();
            Integer localDot = this.dots.get(dotEntry.getKey());

            if(localDot != null) {
                this.dots.put(dotEntry.getKey(), Math.max(otherDot, localDot));
            } else {
                this.dots.put(dotEntry.getKey(), otherDot);
            }
        }
    }

    /**
     * This determines whether or not the local dot context has items the other does not and the other has items
     * that we do not have
     * Two DotContexts are said to be concurrent if there is simulateously one entry less than the other and another greater
     */
    public boolean isConcurrent(DotContext other) {
        boolean thisGreater = false;
        boolean otherGreater = false;

        for (Integer key : this.dots.keySet()) {
            Integer thisValue = this.dots.getOrDefault(key, 0);
            Integer otherValue = other.dots.getOrDefault(key, 0);

            if (thisValue > otherValue) {
                thisGreater = true;
            } else if (thisValue < otherValue) {
                otherGreater = true;
            }

            if (thisGreater && otherGreater) {
                return true;
            }
        }

        for (Integer key : other.dots.keySet()) {
            if (!this.dots.containsKey(key)) {
                otherGreater = true;
            }

            if (thisGreater && otherGreater) {
                return true;
            }
        }

        return false;
    }

    public boolean isMoreRecentThan(DotContext other) {
        for (Map.Entry<Integer, Integer> otherEntry : other.dots.entrySet()) {
            Integer localValue = this.dots.getOrDefault(otherEntry.getKey(), 0);
            if (localValue < otherEntry.getValue()) {
                return false;
            }
        }
        return true;
    }

    public DotContextProto.DotContext toMessageDotContext() {
        return DotContextProto.DotContext.newBuilder()
                .putAllDots(this.dots)
                .build();
    }

    public static DotContext fromMessageDotContext(DotContextProto.DotContext msgDotContext) {
        DotContext dotContext = new DotContext(0);
        dotContext.setDots(new HashMap<>(msgDotContext.getDotsMap()));
        return dotContext;
    }
}