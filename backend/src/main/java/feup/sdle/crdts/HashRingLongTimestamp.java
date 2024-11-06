package feup.sdle.crdts;

import java.util.Objects;

public class HashRingLongTimestamp<V> implements Comparable<HashRingLongTimestamp<V>> {
    private Integer sequence;
    private VersionStamp versionStamp;
    private V value;

    public HashRingLongTimestamp(Integer identifier, int dot, V value, Integer sequence) {
        this.versionStamp = new VersionStamp(identifier, dot);
        this.value = value;
        this.sequence = sequence;
    }

    public Integer getIdentifier() {
        return this.versionStamp.getIdentifier();
    }

    public VersionStamp getVersionStamp() {
        return this.versionStamp;
    }

    public int getSequenceNumber() {
        return this.sequence;
    }

    public void setSequenceNumber(int value) {
        this.sequence = value;
    }

    public Integer getDot() {
        return this.versionStamp.getDot();
    }

    public V getValue() {
        return this.value;
    }

    @Override
    public int compareTo(HashRingLongTimestamp other) {
        return this.sequence.compareTo(other.sequence);

        /*if(this.versionStamp.getIdentifier().equals(other.versionStamp.getIdentifier())) {
            return this.versionStamp.getDot().compareTo(other.versionStamp.getDot());
        }

        return this.versionStamp.getIdentifier().compareTo(other.versionStamp.getIdentifier());*/
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HashRingLongTimestamp<V> otherVersion = (HashRingLongTimestamp) o;
        return this.versionStamp.equals(otherVersion.versionStamp) && (this.sequence.equals(otherVersion.sequence));
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.sequence) + this.versionStamp.hashCode();
    }
}
