import { NodeIdentifier } from "./NodeIdentifier";

/**
 * A hash ring implementation where we can compute which node to contact.
 */
export class HashRing {
    private ring: Map<string, NodeIdentifier>;
    private starterNodes: NodeIdentifier[];

    constructor() {
        this.ring = new Map();

        this.starterNodes = [
            new NodeIdentifier(100001, "localhost", 4321, true),
            new NodeIdentifier(100002, "localhost", 4322, true),
            new NodeIdentifier(100003, "localhost", 4323, true),
        ];
    }
}
