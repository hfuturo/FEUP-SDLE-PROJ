import { DotContext } from "./DotContext";

export class MVRegister<T> {
    private values: Set<T>;
    private dotContext: DotContext;

    private readonly localIdentifier: number;

    constructor(localIdentifier: number) {
        this.values = new Set();
        this.dotContext = new DotContext(localIdentifier);
        this.localIdentifier = localIdentifier;
    }

    toSerializable(local: boolean = true) {
        return {
            "localIdentifier": this.localIdentifier,
            "values": Array.from(this.values),
            "dotContext": this.dotContext.toSerializable(local),
        };
    }

    public getValues(): Set<T> {
        return this.values;
    }

    public getDotContext(): DotContext {
        return this.dotContext;
    }

    merge(other: MVRegister<T>): void {
        console.log("other: ", other);
        console.log("this: ", this);
        if (this.dotContext.isConcurrent(other.getDotContext())) {
            other.getValues().forEach(value => this.values.add(value));
        } else if (other.getDotContext().isMoreRecentThan(this.dotContext)) {
            this.values.clear();
            other.getValues().forEach(value => this.values.add(value));
        }
        this.dotContext.merge(other.getDotContext());
    }

    update(value: T): void {
        this.values.clear();
        this.values.add(value);
        this.dotContext.getDots().set(this.localIdentifier, this.dotContext.nextOfReplica(this.localIdentifier));
    }

    static fromDatabase(mvregister, localId: number) {
        const cloned = new MVRegister<T>(localId);

        cloned.values = new Set(mvregister.values);
        cloned.dotContext = DotContext.fromDatabase(mvregister.dotContext, localId);

        return cloned;
    }
}
