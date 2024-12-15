import { Hasher } from "../utils/Hasher";
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
        for (const node of this.nodes) {
            try {
                const res = await fetch(`http://${node.getHostname()}:${node.getHttpPort()}/api/ring/`);

                if (res.ok) {
                    return await res.json();
                }
            } catch (e) {
                console.error(e);
                continue;
            }
        }

        return {}
    }

    /**
     * Get the closest key value to key: string
     */
    getResponsibleNode(key: string) {
        const keyHash = Hasher.md5(key);

        // Get the keys of the Map and sort them
        const keys = Array.from(this.ring.keys()).sort((a, b) => (a > b ? 1 : -1));

        // Find the first key that is greater than or equal to the given hash
        for (const key of keys) {
            if (key >= keyHash) {
                return this.ring.get(key); // Found the closest higher key
            }
        }

        return this.ring.get(keys[0]);
    }

    /**
     * Get the closest N key values to key: string
     */
    getResponsibleNodes(key: string, nReplicas: number = 3) {
        const keyHash = Hasher.md5(key);
        const responsibleNodes = [];

        // Get the keys of the Map and sort them
        const keys = Array.from(this.ring.keys()).sort((a, b) => (a > b ? 1 : -1));

        // Find the first key that is greater than or equal to the given hash
        for (const key of keys) {
            if (key >= keyHash) {
                if (responsibleNodes.length < nReplicas) {
                    responsibleNodes.push(this.ring.get(key)); // Found the closest higher key
                }
            }

        }

        for (const key of keys) {
            if (responsibleNodes.length < nReplicas) {
                responsibleNodes.push(this.ring.get(key)); // Found the closest higher key
            }
        }

        return responsibleNodes;
    }

    /**
     * Add a node to the hash ring
     */
    addNodeFromJson(key: string, nodeJson: any) {
        const node = new NodeIdentifier(nodeJson["localIdentifier"], nodeJson["hostName"], nodeJson["port"], nodeJson["httpPort"], true);
        this.ring.set(key, node);
    }

    /**
     * Add multiple nodes to the hash ring
     */
    addNodesFromJson(nodesJson: any) {
        Object.keys(nodesJson).forEach((key) => {
            const node = nodesJson[key];
            this.addNodeFromJson(key, node);
        });
    }

    static fromJson(jsonRing) {
        const hashRing = new HashRing();
        hashRing.addNodesFromJson(jsonRing);
    }
}
