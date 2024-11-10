package feup.sdle.crdts;

import java.util.Objects;

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
    public String toString() {
        return "Dot: " + this.dot +  ", Identifier: " + this.identifier;
    }

    @Override
    public int compareTo(VersionStamp o) {
        if(this.identifier.equals(o.identifier)) {
            return this.dot.compareTo(o.dot);
        }

        return this.identifier.compareTo(o.identifier);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VersionStamp otherVersion = (VersionStamp) o;
        return this.identifier.equals(otherVersion.identifier) && this.dot.equals(otherVersion.dot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.identifier, this.dot);
    }
}
