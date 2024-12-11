import { HashRing } from "../p2p/HashRing";
import { ShoppingList } from "./ShoppingList";

export class CRDTSyncService {
    constructor() { }

    /**
     * Sends updates to the servers of a crdt by its id
     */
    async send(list: ShoppingList, ring: HashRing) {
        if(!list || !ring) {
            return;
        }

        if(list.getItems().getLocalIdentifier() !== list.getLocalIdentifier()) {
            list.getItems().setLocalIdentifier(list.getLocalIdentifier());
        }

        console.log("Sending list: ", JSON.stringify(list.toSerializable(false)));

        try {
            const id = list.getId();
            const node = ring.getResponsibleNode(id);
            const res = await fetch(`http://${node.getHostname()}:${node.getHttpPort()}/api/cart/${id}`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({ "type": "shoppingList", ...list.toSerializable(false) }),
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
        if (!ring) {
            console.log("Ring is not defined");
            return;
        }

        try {
            const node = ring.getResponsibleNode(id);
            const res = await fetch(`http://${node?.getHostname()}:${node?.getHttpPort()}/api/cart/${id}`, {
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                }
            });

            if (res.ok) {
                const json = await res.json();

                return json;
            } else {
                return null;
            }
        } catch (error) {
            console.error("Failed to update crdt: ", error);
            return null;
        }
    }
}