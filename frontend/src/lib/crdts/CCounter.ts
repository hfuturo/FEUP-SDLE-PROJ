import { DottedValue } from "./DottedValue";

export class CCounter {
  private set: Set<DottedValue<number, number, number>>;
  private readonly id: number;

  constructor(id: number) {
    this.id = id;
    this.set = new Set();
  }

  public getSet(): Set<DottedValue<number, number, number>> {
    return this.set;
  }

  toSerializable() {
    return {
      "id": this.id,
      "set": Array.from(this.set).map((dv) => ({
        "identifier": dv.identifier,
        "event": dv.event,
        "value": dv.value,
      })),
    };
  }

  public setSet(set: Set<DottedValue<number, number, number>>): void {
    this.set = set;
  }

  public getValue(): number {
    let sum = 0;
    for (const dv of this.set) {
      sum += dv.value;
    }
    return sum;
  }

  public update(value: number): void {
    const optDV = this.find(this.id);

    if(Number.isNaN(value)) {
      value = 0;
    }

    if (!optDV) {
      // No existing dotted value for this ID
      const newValue = value > 0 ? value : Math.max(-this.getValue(), value);
      this.set.add(new DottedValue(this.id, 1, newValue));
    } else {
      // Existing dotted value found
      const dv = optDV;
      const newEvent = dv.event + 1;
      const newValue = value > 0 ? dv.value + value : Math.max(0, dv.value + value);

      this.set.delete(dv);
      this.set.add(new DottedValue(this.id, newEvent, newValue));
    }
  }

  public merge(other: CCounter): void {
    for (const otherDv of other.getSet()) {
      const optDv = this.find(otherDv.identifier);

      if (!optDv) {
        // Add other node's data if we don't have it
        this.set.add(otherDv);
      } else {
        const dv = optDv;

        if (dv.event >= otherDv.event) {
          // Skip if we have a more recent event
          continue;
        }

        // Replace with more recent data
        this.set.delete(dv);
        this.set.add(otherDv);
      }
    }
  }

  private find(identifier: number): DottedValue<number, number, number> | undefined {
    for (const dv of this.set) {
      if (dv.identifier === identifier) {
        return dv;
      }
    }
    return undefined;
  }

  public toMessageCCounter(): any {
    return {
      id: this.id,
      set: Array.from(this.set).map((dv) => ({
        identifier: dv.identifier,
        event: dv.event,
        valueInt: dv.value,
      })),
    };
  }

  public static fromMessageCCounter(msgCCounter: any): CCounter {
    const cCounter = new CCounter(msgCCounter.id);
    const set = new Set<DottedValue<number, number, number>>();

    for (const dv of msgCCounter.set) {
      set.add(new DottedValue(dv.identifier, dv.event, dv.valueInt));
    }

    cCounter.setSet(set);
    return cCounter;
  }

  static fromDatabase(cCounter) {
    console.log("Database ccounter: ", cCounter.set.length);
    const cloned = new CCounter(cCounter.localIdentifier);
    if(cCounter.set.length > 0) {
      cloned.setSet(new Set(cCounter.set));
    } else {
      cloned.setSet(new Set());
    }
    
    return cloned;
  }
}
