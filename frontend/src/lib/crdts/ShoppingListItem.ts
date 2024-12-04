import { CCounter } from "./CCounter";

export class ShoppingListItem {
  private id: string;
  private counter: CCounter;
  private name: string;

  constructor(id: string, localIdentifier: number, name: string, value?: number) {
    this.counter = new CCounter(localIdentifier);
    this.name = name;
    this.id = id;

    if (value !== undefined) {
      const validValue = value < 0 ? 0 : value;
      this.counter.update(validValue);
    }
  }
  
  getId() {
    return this.id;
  }

  getName() {
    return this.name;  
  }

  toSerializable() {
    return {
      "name": this.name,
      "counter": this.counter.toSerializable(),
    };
  }

  public getCounter(): CCounter {
    return this.counter;
  }

  public setCounter(counter: CCounter): void {
    this.counter = counter;
  }

  public getQuantity(): number {
    return this.counter.getValue();
  }

  public updateQuantity(quantity: number): void {
    this.counter.update(quantity);
  }

  public merge(other: ShoppingListItem): void {
    this.counter.merge(other.getCounter());
  }

  public toMessageShoppingListItem(): any {
    return {
      ccounter: this.counter.toMessageCCounter(),
    };
  }

  public static fromMessageShoppingListItem(
    identifier: number,
    msgSLItem: any,
    counterFactory: (data: any) => CCounter
  ): ShoppingListItem {
    const shoppingListItem = new ShoppingListItem(identifier);
    shoppingListItem.setCounter(counterFactory(msgSLItem.ccounter));
    return shoppingListItem;
  }
}
