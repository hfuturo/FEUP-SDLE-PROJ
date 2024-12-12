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
        this.dotContext.nextOfReplica(this.localIdentifier);
    }

    static fromDatabase(mvregister) {
        const cloned = new MVRegister<T>(mvregister.localIdentifier);
        
        cloned.values = new Set(mvregister.values);
        cloned.dotContext = new DotContext(mvregister.dotContext);

        return cloned;
    }
}
