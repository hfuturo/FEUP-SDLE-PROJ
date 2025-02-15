import { AWMap } from "./AWMap";
import { DottedValue } from "./DottedValue";
import { NodeIdentifier } from "../p2p/NodeIdentifier";
import { ShoppingListItem } from "./ShoppingListItem";
import { HashRing } from "../p2p/HashRing";

export class ShoppingList {
    private id: string;
    private lastItemId: number;
    private items: AWMap<string, ShoppingListItem>;
    private localIdentifier: number;
    private removedCounters: Map<string, DottedValue<number, number, number>>;

    constructor(localIdentifier: number, id?: string) {
        this.localIdentifier = localIdentifier;
        this.id = id || crypto.randomUUID();
        this.lastItemId = 0;
        this.items = new AWMap<string, ShoppingListItem>(this.localIdentifier);
        this.removedCounters = new Map<string, DottedValue<number, number, number>>();
    }

    public toSerializable(local: boolean = true) {

        // serialize removedCounters
        let map = null;

        if (local) {
            map = new Map();
        }
        else {
            map = {};
        }

        if (this.removedCounters) {
            for (const [k, v] of this.removedCounters) {
                if (local) {
                    map.set(k, (new DottedValue(v.identifier, v.event, v.value)).toSerializable());
                }
                else {
                    map[k] = (new DottedValue(v.identifier, v.event, v.value)).toSerializable();
                }
            }
        }

        return {
            "type": "shoppingList",
            "id": this.id,
            "localIdentifier": this.localIdentifier,
            "items": this.items.toSerializable(local),
            "removedCounters": map
        }
    }

    public getLocalIdentifier() {
        return this.localIdentifier;
    }

    public setNodeIdentifier(identifier: number): void {
        this.localIdentifier = identifier;
    }

    public setItems(items: AWMap<string, ShoppingListItem>): void {
        this.items = items;
    }

    public setRemovedCounters(removedCounters: Map<string, DottedValue<number, number, number>>): void {
        this.removedCounters = removedCounters;
    }

    public addItem(key: string, name: string, quantity: number): void {
        this.items.add(key, new ShoppingListItem(key, this.localIdentifier, name, quantity));
    }

    public remove(key: string): void {
        const item = this.items.getValue(key);
        if (!item) return;

        const removed = this.removedCounters.get(key);
        if (removed) {
            this.removedCounters.set(
                key,
                new DottedValue<number, number, number>(
                    item.identifier,
                    item.event + 1,
                    item.value.getQuantity() + removed.value
                )
            );

            item.value.updateQuantity(-(item.value.getQuantity() + removed.value));
        } else {
            this.removedCounters.set(
                key,
                new DottedValue<number, number, number>(
                    item.identifier,
                    item.event + 1,
                    item.value.getQuantity()
                )
            );

            item.value.updateQuantity(-item.value.getQuantity());
        }

        // this.items.remove(key);
    }

    getRemovedCounters() {
        return this.removedCounters;
    }

    public merge(other: ShoppingList): void {
        const latestOtherDot = this.items.getDotContext().latestReplicaDot(other.localIdentifier) || 0;

        console.warn(this.items.getDotContext());

        const modifedItems = this.getModifiedItems(other.getItems())

        this.items.merge(other.items);

        // if (!latestOtherDot) {
        //     console.warn("No latest other dot!");
        //     return;
        // }

        for (const [key, dottedValue] of other.removedCounters.entries()) {
            const currentValue = this.items.getValue(key);
            if (currentValue) {
                const otherCounter = other.items.getValue(key)?.value.getCounter();
                console.log("OTHER COUNTER", otherCounter);
                console.log("CONCURRENT: ", currentValue.value.getCounter().isConcurrent(otherCounter));
                if (otherCounter && !currentValue.value.getCounter().isConcurrent(otherCounter)) {
                    this.items.remove(key);
                    this.removedCounters.set(
                        key,
                        dottedValue
                    );
                } else if (otherCounter) {
                    this.removedCounters.delete(key);
                    console.log("DELETING FROM REMOVE COUNTERS");
                    currentValue.value.updateQuantity(-dottedValue.value);
                }
            }
        }

        for (const [key, dottedValue] of this.removedCounters.entries()) {
            const currentValue = this.items.getValue(key);
            if (currentValue) {
                const otherCounter = other.items.getValue(key)?.value.getCounter();

                if (otherCounter && (otherCounter.getValue() > 0 && !other.removedCounters.has(key))) {
                    this.removedCounters.delete(key);
                } else {
                    this.items.remove(key);
                }
            }
        }
    }

    public getModifiedItems(otherList: AWMap<string, ShoppingListItem>) {
        const modifiedItems = [];

        for (const [k, v] of otherList.getValues()) {
            const localValue = this.items.getValue(k);
            if (localValue !== undefined && localValue.eve) {
                modifiedItems.push(k);
            }
        }

        console.warn("MODIFIED ITEMS", modifiedItems);
        return modifiedItems;
    }

    public getItems(): AWMap<string, ShoppingListItem> {
        return this.items;
    }

    public getId(): string {
        return this.id;
    }

    public setId(id: string): void {
        this.id = id;
    }

    public toProtoBuf(): ShoppingListProto {
        const removedCountersSerialized: { [key: string]: any } = {};

        this.removedCounters.forEach((value, key) => {
            removedCountersSerialized[key] = value.toMessageDottedValue();
        });

        return {
            id: this.id,
            items: this.items.toMessageProto(),
            localIdentifier: this.localIdentifier.toMessageNodeIdentifier(),
            removedCounters: removedCountersSerialized,
        };
    }

    static generateLocalIdentifier() {
        return Math.floor(Math.random() * 10000);
    }

    static async createShoppingList(ring: HashRing): Promise<ShoppingList | null> {
        let shoppingList = null;
        const tries = 10;
        for (let i = 0; i < tries; i++) {
            const id = crypto.randomUUID();
            const node = ring?.getResponsibleNode(id);
            const localIdentifier = Math.floor(Math.random() * 10000);
            shoppingList = new ShoppingList(localIdentifier, id);

            try {
                // Switch api port from 8081 to dynamic once the backend is changed to reflect that
                const res = await fetch(`http://${node?.getHostname()}:${node?.getHttpPort()}/api/cart/${id}`, {
                    method: "PUT",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify({ "type": "shoppingList", ...shoppingList.toSerializable(false) }),
                });

                if (res.ok) {
                    return await res.json();
                }
            } catch (e) {
                console.error(e);
                continue;
            }
        }

        return shoppingList;
    }

    clone() {
        const cloned = new ShoppingList(this.localIdentifier, this.id);

        cloned.setItems(this.items.clone());
        cloned.setRemovedCounters(this.removedCounters);
        return cloned;
    }

    /**
     * list: {
      "id": "f1aef91a-0469-4869-beee-3e7658fd1e98",
      "localIdentifier": 50,
      "items": {
          "localIdentifier": 50,
          "values": {},
          "keys": {
              "localIdentifier": 50,
              "values": {},
              "dotContext": {
                  "dots": {}
              }
          }
      },
      "removedCounters": {}
  }
     */
    static fromDatabase(list) {
        try {
            const cloned = new ShoppingList(list.localIdentifier, list.id);
            cloned.setItems(AWMap.fromDatabase(list.items, list.localIdentifier) as AWMap<string, ShoppingListItem>);
            cloned.getItems().setLocalIdentifier(list.localIdentifier);

            const map = new Map();

            if (list.removedCounters.entries) {
                for (const [key, value] of list.removedCounters.entries()) {
                    map.set(key, new DottedValue(value.identifier, value.event, value.value));
                }
            }
            else {
                for (const [key, value] of Object.entries(list.removedCounters)) {
                    map.set(key, new DottedValue(value.identifier, value.event, value.value));
                }
            }

            cloned.setRemovedCounters(map);

            return cloned;
        } catch (error) {
            console.error(error);
        }
    }
}
