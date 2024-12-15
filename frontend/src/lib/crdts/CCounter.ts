import { DottedValue } from "./DottedValue";

export class CCounter {
    private set: Set<DottedValue<number, number, number>>;
    private readonly identifier: number;

    constructor(identifier: number) {
        this.identifier = identifier;
        this.set = new Set();
    }

    public getSet(): Set<DottedValue<number, number, number>> {
        return this.set;
    }

    toSerializable(local: boolean = true) {
        return {
            "identifier": this.identifier,
            "set": Array.from(this.set).map((dv) => ({
                "identifier": dv.identifier,
                "event": dv.event,
                "value": dv.value,
            })),
        };
    }

    public isConcurrent(other: CCounter) {
        if(other) return false;
        
        let localGreater = false;
        let otherGreater = false;

        // Check if there is a dot greater in this dotted values and another greater in the other
        for (const key of Array.from(this.set)) {
            const otherValue = Array.from(other.set).find(el => el.identifier === key.identifier)?.event || 0;

            if (key.event > otherValue) {
                localGreater = true;
            } else if (key.event < otherValue) {
                otherGreater = true;
            }

            if (localGreater && otherGreater) {
                return true;
            }
        }

        // Check for keys in the other set not in this set
        for (const key of Array.from(other.set)) {
            if(!Array.from(this.set).find(el => el.identifier === key.identifier)) {
                otherGreater = true;
            }

            if (localGreater && otherGreater) {
                return true;
            }
        }

        return false;
    }

    public setSet(set: Set<DottedValue<number, number, number>>): void {
        this.set = set;
    }

    public getValue(): number {
        let sum = 0;
        for (const dv of this.set) {
            sum += dv.value;
        }
        return sum;
    }

    public update(value: number): void {
        const optDV = this.find(this.identifier);
        const MAX_VALUE = 2147483647; // Max value for a 32-bit signed integer (used by database)

        if (Number.isNaN(value)) {
            value = 0;
        }

        if (!optDV) {
            console.log("No existing dotted value for this ID");
            const current = this.getValue();
            // No existing dotted value for this ID
            const newValue = (value > 0 ? Math.min(value, MAX_VALUE) : Math.max(-current, value));
            this.set.add(new DottedValue(this.identifier, 1, newValue));
        } else {
            // Existing dotted value found
            console.log("Existing dotted value found")
            const dv = optDV;
            const newEvent = dv.event + 1;
            const newValue = (value > 0 ? Math.min(dv.value + value, MAX_VALUE) : Math.max(dv.value - this.getValue(), dv.value + value));

            this.set.delete(dv);
            this.set.add(new DottedValue(this.identifier, newEvent, newValue));
        }
    }

    public merge(other: CCounter): void {
        for (const otherDv of other.getSet()) {
            const optDv = this.find(otherDv.identifier);

            if (!optDv) {
                // Add other node's data if we don't have it
                this.set.add(otherDv);
            } else {
                const dv = optDv;

                if (dv.event >= otherDv.event) {
                    // Skip if we have a more recent event
                    continue;
                }

                // Replace with more recent data
                this.set.delete(dv);
                this.set.add(otherDv);
            }
        }
    }

    private find(identifier: number): DottedValue<number, number, number> | undefined {
        for (const dv of this.set) {
            if (dv.identifier === identifier) {
                return dv;
            }
        }
        return undefined;
    }

    public toMessageCCounter(): any {
        return {
            id: this.identifier,
            set: Array.from(this.set).map((dv) => ({
                identifier: dv.identifier,
                event: dv.event,
                valueInt: dv.value,
            })),
        };
    }

    public static fromMessageCCounter(msgCCounter: any): CCounter {
        const cCounter = new CCounter(msgCCounter.identifier);
        const set = new Set<DottedValue<number, number, number>>();

        for (const dv of msgCCounter.set) {
            set.add(new DottedValue(dv.identifier, dv.event, dv.valueInt));
        }

        cCounter.setSet(set);
        return cCounter;
    }

    static fromDatabase(cCounter, localId: number) {
        const cloned = new CCounter(localId);
        if (cCounter.set.length > 0) {
            cloned.setSet(new Set(cCounter.set));
        } else {
            cloned.setSet(new Set());
        }

        return cloned;
    }
}
