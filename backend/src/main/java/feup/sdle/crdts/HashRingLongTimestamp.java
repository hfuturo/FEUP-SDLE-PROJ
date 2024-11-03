package feup.sdle.crdts;

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
        if(!(o instanceof HashRingLongTimestamp)) {
            return false;
        }

        HashRingLongTimestamp<V> v = (HashRingLongTimestamp) o;
        return this.versionStamp.getIdentifier().equals(v.versionStamp.getIdentifier())
                && this.versionStamp.getDot().equals(v.versionStamp.getDot());
    }
}
