import { DotContext } from "./DotContext";
import { DottedValue } from "./DottedValue";

/**
 * Add-Wins Set (AWSet) implementation using DotContext and DottedValue.
 * This structure is a conflict-free replicated data type (CRDT) that allows distributed systems
 * to resolve conflicts by considering additions as dominant over removals.
 */
export class AWSet<V> {
  private values: Set<DottedValue<number, number, V>>;
  private dotContext: DotContext;
  private localIdentifier: number;

  constructor(localIdentifier: number) {
    this.localIdentifier = localIdentifier;
    this.values = new Set<DottedValue<number, number, V>>();
    this.dotContext = new DotContext(localIdentifier);
  }

  toSerializable() {
    return {
      "localIdentifier": this.localIdentifier,
      "values": Array.from(this.values).map((val) => (new DottedValue(val.identifier, val.event, val.value)).toSerializable()),
      "dotContext": this.dotContext.toSerializable(),
    }
  }

  /**
   * Adds an element to the AWSet.
   */
  public add(element: V): void {
    const dottedValue = new DottedValue<number, number, V>(
      this.localIdentifier,
      this.dotContext.nextOfReplica(this.localIdentifier),
      element
    );
    this.values.add(dottedValue);
    this.dotContext.getDots().set(this.localIdentifier, this.dotContext.nextOfReplica(this.localIdentifier));
  }

  /**
   * Removes an element from the AWSet by filtering it out of the set.
   */
  public remove(element: V): void {
    this.values = new Set(
      [...this.values].filter((el) => el.value !== element)
    );
  }

  /**
   * Gets the set of elements in the local AWSet that are not present in the provided DotContext.
   */
  private getDiffElements(
    elements: Set<DottedValue<number, number, V>>,
    dc: DotContext
  ): Set<DottedValue<number, number, V>> {
    return new Set(
      [...elements].filter((el) => !dc.has(el.identifier, el.event))
    );
  }

  /**
   * Merges the current AWSet with another AWSet.
   */
  public merge(other: AWSet<V>): void {
    const newSet = new Set<DottedValue<number, number, V>>([...this.values]);

    // Retain common elements between sets
    for (const value of other.values) {
      if (this.values.has(value)) {
        newSet.add(value);
      }
    }

    // Add differences from this AWSet and the other AWSet
    for (const diff of this.getDiffElements(this.values, other.dotContext)) {
      newSet.add(diff);
    }
    for (const diff of this.getDiffElements(other.values, this.dotContext)) {
      newSet.add(diff);
    }

    this.values = newSet;
  }

  /**
   * Gets the values in the AWSet without their metadata.
   */
  public getValues(): Set<V> {
    return new Set([...this.values].map((dottedValue) => dottedValue.value));
  }

  /**
   * Serializes the AWSet into a plain object for serialization or networking.
   */
  public toMessageAWSet(): {
    dotContext: { [key: number]: number };
    localIdentifier: number;
    values: Array<{ identifier: number; event: number; value: V }>;
  } {
    return {
      dotContext: this.dotContext.toMessageDotContext(),
      localIdentifier: this.localIdentifier,
      values: [...this.values].map((value) => value.toMessageDottedValue()),
    };
  }

  setValues(values: Set<DottedValue<number, number, V>>): void {
    this.values = values;
  }

  setDotContext(dotContext: DotContext): void {
    this.dotContext = dotContext;
  }

  clone() {
    const cloned = new AWSet<V>(this.localIdentifier);
    cloned.setValues(this.values);
    cloned.setDotContext(this.dotContext.clone());

    return cloned;
  }

  static fromDatabase(awset) {
    const cloned = new AWSet(awset.localIdentifier);
    cloned.setValues(awset.values);
    cloned.setDotContext(DotContext.fromDatabase(awset.dotContext));
    return cloned;
  }
}
