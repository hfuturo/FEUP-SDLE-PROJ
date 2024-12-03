import { DotContext } from "./DotContext";
import { DottedValue } from "./DottedValue";
import { AWSet } from "./AWSet";

interface NodeIdentifier {
  getId(): number;
  toMessageNodeIdentifier(): { id: number };
}

/**
 * Add-Wins Map (AWMap) implementation using DotContext, AWSet, and DottedValue.
 * This structure is a conflict-free replicated data type (CRDT) that represents a map
 * where additions take precedence over removals in conflict resolution.
 */
export class AWMap<K, V extends { merge: (other: V) => void }> {
  private dotContext: DotContext;
  private localIdentifier: number;
  private keys: AWSet<K>;
  private values: Map<K, DottedValue<number, number, V>>;

  constructor(localIdentifier: number) {
    this.localIdentifier = localIdentifier;
    this.dotContext = new DotContext(localIdentifier);
    this.values = new Map<K, DottedValue<number, number, V>>();
    this.keys = new AWSet<K>(localIdentifier);
  }

  /**
   * Retrieves the latest dot value for a given replica ID.
   */
  public latestDot(id: number): number | null {
    return this.dotContext.latestReplicaDot(id) || null;
  }

  public setDotContext(dotContext: DotContext): void {
    this.dotContext = dotContext;
  }

  public setKeys(keys: AWSet<K>): void {
    this.keys = keys;
  }

  public setValues(values: Map<K, DottedValue<number, number, V>>): void {
    this.values = values;
  }

  public getValues(): Map<K, DottedValue<number, number, V>> {
    return this.values;
  }

  public getDotContext(): DotContext {
    return this.dotContext;
  }

  public getValue(key: K): DottedValue<number, number, V> | undefined {
    return this.values.get(key);
  }

  public getKeys(): AWSet<K> {
    return this.keys;
  }

  /**
   * Adds a value to the map. If the key already exists, merges the values.
   */
  public add(id: K, value: V): void {
    const item = this.values.get(id);

    if (!item) {
      const dot = this.dotContext.nextOfReplica(this.localIdentifier);
      this.values.set(id, {
        identifier: this.localIdentifier,
        event: dot,
        value: value,
        toMessageDottedValue: function () {
          return {
            identifier: this.identifier,
            event: this.event,
            value: this.value,
          };
        },
      });
    } else {
      item.value.merge(value);
    }
  }

  /**
   * Removes a key-value pair from the map.
   */
  public remove(id: K): void {
    this.values.delete(id);
  }

  /**
   * Merges another AWMap into this one.
   */
  public merge(other: AWMap<K, V>): void {
    const localKeys = new Set(this.values.keys());
    const otherKeys = new Set(other.values.keys());

    // Find common keys and merge their values
    for (const key of localKeys) {
      if (otherKeys.has(key)) {
        const localValue = this.values.get(key);
        const otherValue = other.values.get(key);
        if (localValue && otherValue) {
          localValue.value.merge(otherValue.value);
        }
      }
    }

    // Add keys unique to the other map
    for (const key of otherKeys) {
      if (!localKeys.has(key)) {
        const otherValue = other.values.get(key);
        if (otherValue) {
          this.values.set(key, otherValue);
        }
      }
    }

    // Merge DotContext
    this.dotContext.merge(other.getDotContext());
  }

  /**
   * Serializes the AWMap into a plain object for serialization or networking.
   */
  public toMessageProto(): {
    dotContext: { [key: number]: number };
    localIdentifier: { id: number };
    keys: { dotContext: { [key: number]: number }; localIdentifier: number; values: K[] };
    values: { [key: string]: { identifier: number; event: number; value: V } };
  } {
    const serializedValues: { [key: string]: { identifier: number; event: number; value: V } } = {};

    this.values.forEach((dottedValue, key) => {
      serializedValues[key.toString()] = dottedValue.toMessageDottedValue();
    });

    return {
      dotContext: this.dotContext.toMessageDotContext(),
      localIdentifier: this.localIdentifier.toMessageNodeIdentifier(),
      keys: this.keys.toMessageAWSet(),
      values: serializedValues,
    };
  }

  /**
   * Deserializes a plain object into an AWMap instance.
   */
  public static fromMessageProto<K, V>(
    msgAWMap: {
      dotContext: { [key: number]: number };
      localIdentifier: { id: number };
      keys: { dotContext: { [key: number]: number }; localIdentifier: number; values: K[] };
      values: { [key: string]: { identifier: number; event: number; value: V } };
    },
    valueFromProto: (protoValue: any) => V,
    nodeIdentifierFactory: (id: number) => NodeIdentifier
  ): AWMap<K, V> {
    const localIdentifier = nodeIdentifierFactory(msgAWMap.localIdentifier.id);
    const awMap = new AWMap<K, V>(localIdentifier);

    awMap.setDotContext(new DotContext(msgAWMap.dotContext));
    const values = new Map<K, DottedValue<number, number, V>>();

    for (const [key, dottedValue] of Object.entries(msgAWMap.values)) {
      values.set(key as unknown as K, {
        identifier: dottedValue.identifier,
        event: dottedValue.event,
        value: valueFromProto(dottedValue.value),
        toMessageDottedValue: () => dottedValue,
      });
    }

    awMap.setValues(values);
    return awMap;
  }
}
