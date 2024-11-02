package feup.sdle.crdts;

import feup.sdle.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * This is used in order to compared how fresh the event knowledge is between two different replicas.
 * We store the replica id along with the current event counter in order to be able to compare that
 * freshness.
 *
 * You can think of this as a vector clock implementation
 */
public class DotContext {
    // The String is the replica identifier (TODO change this in the future)
    // The Long is the event counter of that replica
    private HashMap<String, Integer> dots;

    public DotContext(String localIdentifier) {
        this.dots = new HashMap<String, Integer>();
    }

    public HashMap<String, Integer> getDots() {
        return this.dots;
    }

    /**
     * This will be used to get the maximum counter value the local replica has of another replica
     * which will be extremely when merging the both of them
     */
    public Optional<Integer> maxOfReplica(String identifier) {
        return Optional.of(this.dots.get(identifier));
    }

    /**
     * This is used to get the new entry on the map related to a certain
     * replica identifier
     */
    public Pair<String, Integer> nextOfReplica(String identifier) {
        Integer counter = this.dots.get(identifier);

        if (counter == null) {
            return new Pair<String, Integer>(identifier, 1);
        } else {
            return new Pair<String, Integer>(identifier, counter + 1);
        }
    }

    /**
     * This is used to determine if a certain replica identifer has
     * surpassed a certain event number (the dot)
     */
    public boolean has(String identifier, Integer dot) {
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
        for(Map.Entry<String, Integer> dotEntry: other.dots.entrySet()) {
            Integer otherDot = dotEntry.getValue();
            Integer localDot = this.dots.get(dotEntry.getKey());

            if(localDot != null) {
                this.dots.put(dotEntry.getKey(), Math.max(otherDot, localDot));
            } else {
                this.dots.put(dotEntry.getKey(), otherDot);
            }
        }
    }
}