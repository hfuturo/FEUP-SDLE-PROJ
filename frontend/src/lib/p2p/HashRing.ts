import { NodeIdentifier } from "./NodeIdentifier";

/**
 * A hash ring implementation where we can compute which node to contact.
 */
export class HashRing {
    private ring: Map<string, NodeIdentifier>;
    private nodes: NodeIdentifier[];

    constructor() {
        this.ring = new Map();

        this.nodes = [
            new NodeIdentifier(100001, "localhost", 4321, 8081, true),
            new NodeIdentifier(100002, "localhost", 4322, 8082, true),
            new NodeIdentifier(100003, "localhost", 4323, 8083, true),
        ];
    }

    async getViewFromNodes() {
        for(const node of this.nodes) {
            try {
                const res = await fetch(`http://${node.getHostname()}:${node.getApiPort()}/api/ring/`);

                if(res.ok) {
                    return await res.json();
                }
            } catch(e) {
                console.error(e);
                continue;
            }
        }

        return {}
    }
}
