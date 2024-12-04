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

    public isConcurrent(other: DotContext): boolean {
      let localHasItemsOtherDoesNot = false;
      let otherHasItemsLocalDoesNot = false;
  
      for (const [key, otherDot] of other.getDots()) {
        const localDot = this.dots.get(key);
        if (localDot === undefined || localDot !== otherDot) {
          localHasItemsOtherDoesNot = true;
        }
      }
  
      for (const [key, localDot] of this.dots) {
        const otherDot = other.getDots().get(key);
        if (otherDot === undefined || localDot !== otherDot) {
          otherHasItemsLocalDoesNot = true;
        }
      }
  
      return localHasItemsOtherDoesNot && otherHasItemsLocalDoesNot;
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
  