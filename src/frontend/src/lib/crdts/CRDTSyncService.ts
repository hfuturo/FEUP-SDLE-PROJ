import { HashRing } from "../p2p/HashRing";
import { ShoppingList } from "./ShoppingList";

export class CRDTSyncService {
    constructor() { }

    /**
     * Sends updates to the servers of a crdt by its id
     */
    async send(list: ShoppingList, ring: HashRing) {
        if (!list || !ring) {
            return;
        }

        if (list.getItems().getLocalIdentifier() !== list.getLocalIdentifier()) {
            list.getItems().setLocalIdentifier(list.getLocalIdentifier());
        }

        try {
            const id = list.getId();
            for (const node of ring.getResponsibleNodes(id)) {
                try {
                    const res = await fetch(`http://${node?.getHostname()}:${node?.getHttpPort()}/api/cart/${id}`, {
                        method: "PUT",
                        headers: {
                            "Content-Type": "application/json",
                        },
                        body: JSON.stringify(list.toSerializable(false)),
                    });
                    if (res.ok) {
                        const json = await res.json();
                        return json;
                    }
                }
                catch (error) {
                    if (error instanceof TypeError) { // CORS throws NetworkError when 404, but we want to continue to the next node
                        console.warn("Failed to send to node: ", node?.getId());
                    }
                    else {
                        throw error;
                    }
                }
            }
            return null;
        } catch (error) {
            console.error("Failed to update crdt: ", error);
        }
    }

    /**
     * Fetches updates from the servers of a crdt by its id
     */
    async update(id: string, ring: HashRing) {
        if (!ring) {
            console.log("Ring is not defined");
            return;
        }

        try {
            for (const node of ring.getResponsibleNodes(id)) {
                try {
                    console.log("Fetching from node: ", node);
                    const res = await fetch(`http://${node?.getHostname()}:${node?.getHttpPort()}/api/cart/${id}`, {
                        method: "GET",
                        headers: {
                            "Content-Type": "application/json",
                        }
                    });
                    if (res.ok) {
                        const json = await res.json();
                        return json;
                    }
                }
                catch (error) {
                    if (error instanceof TypeError) { // CORS throws NetworkError when 404, but we want to continue to the next node
                        console.warn("Failed to fetch from node:", node?.getId());
                    }
                    else {
                        throw error;
                    }
                }
            }
            return null;
        } catch (error) {
            console.error("Failed to update crdt: ", error);
            return null;
        }
    }
}
