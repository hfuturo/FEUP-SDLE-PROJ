import { CCounter } from "./CCounter";

export class ShoppingListItem {
  private counter: CCounter;
  private name: string;

  constructor(localIdentifier: number, name: string, value?: number) {
    this.counter = new CCounter(localIdentifier);
    this.name = name;

    if (value !== undefined) {
      const validValue = value < 0 ? 0 : value;
      this.counter.update(validValue);
    }
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
