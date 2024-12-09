import { HashRing } from "../p2p/HashRing";
import { ShoppingList } from "./ShoppingList";

export class CRDTSyncService {
    constructor() { }

    /**
     * Sends updates to the servers of a crdt by its id
     */
    async send(list: ShoppingList, ring: HashRing) {
        try {
            const id = list.getId();
            const node = ring.getResponsibleNode(id);
            const res = await fetch(`http://${node.getHostname()}:${node.getHttpPort()}/api/cart/${id}`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({ "type": "shoppingList", ...list.toSerializable() }),
            });

            if (res.ok) {
                return await res.json();
            }
        } catch (error) {
            console.error("Failed to update crdt: ", error);
        }
    }

    /**
     * Fetches updates from the servers of a crdt by its id
     */
    async update(id: string, ring: HashRing) {
        if (!ring) return;

        try {
            const node = ring.getResponsibleNode(id);
            const res = await fetch(`http://${node?.getHostname()}:${node?.getHttpPort()}/api/cart/${id}`, {
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                }
            });

            if (res.ok) {
                return await res.json();
            }

        } catch (error) {
            console.error("Failed to update crdt: ", error);
        }
    }
}