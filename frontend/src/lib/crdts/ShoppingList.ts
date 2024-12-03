import { AWMap } from "./AWMap";
import { DottedValue } from "./DottedValue";
import { NodeIdentifier } from "../p2p/NodeIdentifier";
import { ShoppingListItem } from "./ShoppingListItem";

interface ShoppingListProto {
  id: string;
  items: any; // Placeholder for serialized AWMap data
  localIdentifier: any; // Placeholder for serialized NodeIdentifier data
  removedCounters: { [key: string]: any }; // Placeholder for serialized DottedValue data
}

export class ShoppingList {
  private id: string;
  private items: AWMap<string, ShoppingListItem>;
  private localIdentifier: number;
  private removedCounters: Map<string, DottedValue<number, number, number>>;

  constructor(localIdentifier: number, id?: string) {
    this.localIdentifier = localIdentifier;
    this.id = id || crypto.randomUUID();
    this.items = new AWMap<string, ShoppingListItem>(localIdentifier);
    this.removedCounters = new Map<string, DottedValue<number, number, number>>();
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
    this.items.add(key, new ShoppingListItem(this.localIdentifier, name, quantity));

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

  static async createShoppingList(ring): Promise<ShoppingList | null> {
    const tries = 10;
    for(let i = 0; i < tries; i++) {
        const randomNumber = Math.floor(Math.random() * Object.keys(ring).length);
        const node = Object.values(ring)[randomNumber];

        try {
            // Switch api port from 8081 to dynamic once the backend is changed to reflect that
            const res = await fetch(`http://${node.hostName}:${node.httpPort}/api/cart/`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
            });

            if (res.ok) {
                return await res.json();
            }
        } catch (e) {
            console.error(e);
            continue;
        }
    }

    return null;
}

  public static fromProtoBuf(
    proto: ShoppingListProto,
    itemDeserializer: (data: any) => ShoppingListItem,
    nodeIdentifierFactory: (data: any) => NodeIdentifier
  ): ShoppingList {
    const localIdentifier = nodeIdentifierFactory(proto.localIdentifier);
    const shoppingList = new ShoppingList(localIdentifier, proto.id);

    const items = AWMap.fromMessageProto<string, ShoppingListItem>(
      proto.items,
      itemDeserializer,
      nodeIdentifierFactory
    );

    const removedCounters = new Map<string, DottedValue<number, number, number>>();
    for (const [key, value] of Object.entries(proto.removedCounters)) {
      removedCounters.set(key, DottedValue.fromMessageProto(value));
    }

    shoppingList.setItems(items);
    shoppingList.setRemovedCounters(removedCounters);

    return shoppingList;
  }
}
