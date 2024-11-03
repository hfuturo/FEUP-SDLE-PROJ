package feup.sdle.crdts;

public class VersionStamp implements Comparable<VersionStamp> {
    private Integer identifier;
    private Integer dot;

    public VersionStamp(Integer identifier, Integer dot) {
        this.identifier = identifier;
        this.dot = dot;
    }

    public Integer getIdentifier() {
        return this.identifier;
    }

    public Integer getDot() {
        return this.dot;
    }

    @Override
    public int compareTo(VersionStamp o) {
        if(this.dot.equals(o.dot)) {
            return this.identifier.compareTo(o.identifier);
        }

        return this.dot.compareTo(o.dot);
    }
}
