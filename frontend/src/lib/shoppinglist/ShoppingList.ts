import { HashRing } from "../p2p/HashRing";
import { NodeIdentifier } from "../p2p/NodeIdentifier";

export class ShoppingList {
    static async createShoppingList(ring): Promise<ShoppingList | null> {
        const tries = 10;
        for(let i = 0; i < tries; i++) {
            const randomNumber = Math.floor(Math.random() * Object.keys(ring).length);
            const node = Object.values(ring)[randomNumber];

            try {
                // Switch api port from 8081 to dynamic once the backend is changed to reflect that
                const res = await fetch(`http://${node.hostName}:${8081}/api/cart/`, {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                });

                if (res.ok) {
                    return await res.json();
                }
            } catch (e) {
                console.error(e);
                continue;
            }
        }

        return null;
    }

    static async getShoppingList(id: string, ring): Promise<string> {
        try {

        } catch (e) {

        }
    }
}