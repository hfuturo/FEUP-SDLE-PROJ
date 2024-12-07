export class DotContext {
    private dots: Map<number, number>;
    private localIdentifier: number;
  
    constructor(localIdentifier: number) {
      this.dots = new Map<number, number>();
      this.localIdentifier = localIdentifier;
    }

    toSerializable() {
      return {
        "dots": this.dots,
      }
    }
  
    public getDots(): Map<number, number> {
      return this.dots;
    }
  
    public setDots(dots: Map<number, number>): void {
      this.dots = dots;
    }
  
    public maxOfReplica(identifier: number): number | null {
      return this.dots.get(identifier) ?? null;
    }
  
    public latestReplicaDot(identifier: number): number | null {
      return this.dots.get(identifier) ?? null;
    }

    public nextOfReplica(identifier: number): number {
      const counter = this.dots.get(identifier) || 0;
      const nextCounter = counter + 1;
      this.dots.set(identifier, nextCounter);
      return nextCounter;
    }
  
    public has(identifier: number, dot: number): boolean {
      const value = this.dots.get(identifier);
      return value !== undefined && value >= dot;
    }
  
    public merge(other: DotContext): void {
      for (const [key, otherDot] of other.getDots()) {
        const localDot = this.dots.get(key) || 0;
        this.dots.set(key, Math.max(localDot, otherDot));
      }
    }

    isMoreRecentThan(other: DotContext): boolean {
        for (const [key, otherValue] of other.dots.entries()) {
            const localValue = this.dots.get(key) || 0;
            if (localValue < otherValue) {
                return false;
            }
        }
        return true;
    }

    isConcurrent(other: DotContext): boolean {
        let thisGreater = false;
        let otherGreater = false;

        // Check dots in this context
        for (const [key, thisValue] of this.dots.entries()) {
            const otherValue = other.dots.get(key) || 0;

            if (thisValue > otherValue) {
                thisGreater = true;
            } else if (thisValue < otherValue) {
                otherGreater = true;
            }

            if (thisGreater && otherGreater) {
                return true;
            }
        }

        // Check for keys in the other context not in this context
        for (const [key, otherValue] of other.dots.entries()) {
            if (!this.dots.has(key)) {
                otherGreater = true;
            }

            if (thisGreater && otherGreater) {
                return true;
            }
        }

        return false;
    }

    clone() {
      const cloned = new DotContext(this.localIdentifier);
      cloned.setDots(this.dots);
      return cloned;
    }

    static fromDatabase(dotContext) {
      const cloned = new DotContext(dotContext.localIdentifier);
      cloned.setDots(dotContext.dots);
      return cloned;
    }
}
  
