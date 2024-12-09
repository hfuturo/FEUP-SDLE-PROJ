import { AWMap } from "./AWMap";
import { DottedValue } from "./DottedValue";
import { NodeIdentifier } from "../p2p/NodeIdentifier";
import { ShoppingListItem } from "./ShoppingListItem";
import { Hasher } from "../utils/Hasher";
import { HashRing } from "../p2p/HashRing";

interface ShoppingListProto {
  id: string;
  items: any; // Placeholder for serialized AWMap data
  localIdentifier: any; // Placeholder for serialized NodeIdentifier data
  removedCounters: { [key: string]: any }; // Placeholder for serialized DottedValue data
}

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
    this.items = new AWMap<string, ShoppingListItem>(localIdentifier);
    this.removedCounters = new Map<string, DottedValue<number, number, number>>();
  }

  public toSerializable() {
    return {
      "id": this.id,
      "localIdentifier": this.localIdentifier,
      "items": this.items.toSerializable(),
      "removedCounters": this.removedCounters
    }
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
          item.event,
          item.value.getQuantity() + removed.value
        )
      );
    } else {
      this.removedCounters.set(
        key,
        new DottedValue<number, number, number>(
          item.identifier,
          item.event,
          item.value.getQuantity()
        )
      );
    }

    this.items.remove(key);
  }

  public merge(other: ShoppingList): void {
    const latestOtherDot = this.items.getDotContext().latestReplicaDot(other.localIdentifier.getId());

    this.items.merge(other.items);

    if (!latestOtherDot) return;

    for (const [key, dottedValue] of other.removedCounters.entries()) {
      if (
        dottedValue.identifier === other.localIdentifier.getId() &&
        dottedValue.event > latestOtherDot
      ) {
        const currentValue = this.items.getValue(key);
        if (currentValue) {
          currentValue.value.updateQuantity(-dottedValue.value);
        }
      }
    }
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

  static async createShoppingList(ring: HashRing): Promise<ShoppingList | null> {
    const id = crypto.randomUUID();
    const node = ring?.getResponsibleNode(id);
    const shoppingList = new ShoppingList(50, id);

    const tries = 10;
    for(let i = 0; i < tries; i++) {
        try {
            // Switch api port from 8081 to dynamic once the backend is changed to reflect that
            const res = await fetch(`http://${node?.getHostname()}:${node?.getHttpPort()}/api/cart/${id}`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({"type": "shoppingList", ...shoppingList.toSerializable()}),
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
    const cloned = new ShoppingList(list.localIdentifier, list.id);
    cloned.setItems(AWMap.fromDatabase(list.items) as AWMap<string, ShoppingListItem>);
    cloned.setRemovedCounters(list.removedCounters);    
    return cloned;
  }
}
