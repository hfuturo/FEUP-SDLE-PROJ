import { DotContext } from "./DotContext";
import { DottedValue } from "./DottedValue";
import { AWSet } from "./AWSet";
import { ShoppingListItem } from "./ShoppingListItem";

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

    serializedMap(local: boolean = true) {
        let map = null;

        if (local) {
            map = new Map();
        } else {
            map = {};
        }

        for (const [key, value] of this.values) {
            if (local) {
                map.set(key, new DottedValue(value.identifier, value.event, value.value.toSerializable(local)));
            } else {
                map[key] = new DottedValue(value.identifier, value.event, value.value.toSerializable(local));
            }
        }

        return map;
    }

    getLocalIdentifier() {
        return this.localIdentifier;
    }

    setLocalIdentifier(localIdentifier: number) {
        this.localIdentifier = localIdentifier;
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

    clone() {
        const cloned = new AWMap<K, V>(this.localIdentifier);
        cloned.setDotContext(this.dotContext.clone());
        cloned.setKeys(this.keys.clone());
        cloned.setValues(this.values);
        return cloned;
    }

    toSerializable(local: boolean = true) {
        return {
            "localIdentifier": this.localIdentifier,
            "values": this.serializedMap(local),
            "keys": this.keys.toSerializable(local),
            "dotContext": this.dotContext.toSerializable(local)
        }
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
            this.dotContext.getDots().set(this.localIdentifier, dot);
            this.values.set(id, {
                identifier: this.localIdentifier,
                event: dot,
                value: value
            });
            this.keys.add(id);
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

        this.keys.merge(other.getKeys());

        // Merge DotContext
        this.dotContext.merge(other.getDotContext());
    }

    static fromDatabase(awMap, localId: number) {
        const cloned = new AWMap(awMap.localIdentifier);
        const map = new Map();

        if (awMap.values.entries) {
            for (const [key, value] of awMap.values.entries()) {
                map.set(key, new DottedValue(value.identifier, value.event, ShoppingListItem.fromDatabase(value.value, localId)));
            }
        } else {
            for (const [key, value] of Object.entries(awMap.values)) {
                map.set(key, new DottedValue(value.identifier, value.event, ShoppingListItem.fromDatabase(value.value, localId)));
            }
        }

        cloned.setValues(map);
        cloned.setDotContext(DotContext.fromDatabase(awMap.dotContext, localId));
        cloned.setKeys(AWSet.fromDatabase(awMap.keys, localId));

        return cloned;
    }
}
