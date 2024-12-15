import { CCounter } from "./CCounter";
import { MVRegister } from "./MVRegister";

export class ShoppingListItem {
    private id: string;
    private localIdentifier: number;
    private counter: CCounter;
    private name: MVRegister<string>;

    constructor(id: string, localIdentifier: number, name?: string, value?: number) {
        this.localIdentifier = localIdentifier;
        this.counter = new CCounter(localIdentifier);
        this.name = new MVRegister<string>(localIdentifier);
        this.id = id;

        if (value !== undefined) {
            const validValue = value < 0 ? 0 : value;
            this.counter.update(validValue);
        }

        if (name !== undefined) {
            this.name.update(name);
        }
    }

    getId() {
        return this.id;
    }

    getName(): MVRegister<string> {
        return this.name;
    }

    toSerializable(local: boolean = true) {
        return {
            "id": this.id,
            "localIdentifier": this.localIdentifier,
            "counter": this.counter.toSerializable(local),
            "name": this.name.toSerializable(local),
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

    public changeName(name: string): void {
        this.name.update(name);
    }

    public updateQuantity(quantity: number): void {
        this.counter.update(quantity);
    }

    public merge(other: ShoppingListItem): void {
        this.name.merge(other.getName());
        this.counter.merge(other.getCounter());
    }

    public toMessageShoppingListItem(): any {
        return {
            ccounter: this.counter.toMessageCCounter(),
        };
    }

    static fromDatabase(slItem, localId: number) {
        const sl = new ShoppingListItem(slItem.id, localId);

        sl.setCounter(CCounter.fromDatabase(slItem.counter, localId));
        sl.name = MVRegister.fromDatabase(slItem.name, localId);

        return sl;
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
