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

}
