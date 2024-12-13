import useSWR from "swr";
import { CRDTSyncService } from "../crdts/CRDTSyncService";
import { ShoppingList } from "../crdts/ShoppingList";
import { HashRing } from "../p2p/HashRing";
import { Database } from "../database/Database";

export default function useCRDTUpdate(id: string, ring: HashRing, db: Database, offline: boolean) {
    const syncService = new CRDTSyncService();
    const { data: syncedList, isLoading } = useSWR("crdt-sync-service-" + id, async () => {
        if(offline) return null;

        const updatedList = await syncService.update(id, ring);

        if (updatedList) {
            const list = ShoppingList.fromDatabase(await db.getShoppingList(id));

            if (list) {
                const otherList = ShoppingList.fromDatabase(updatedList);
                
                try {
                    list.merge(otherList);
                } catch (error) {
                    console.error("Failed to merge lists: ", error);
                }

                console.log("After merge: ", list);

                return list;
            }
        }

        return updatedList;
    }, {
        refreshInterval: 8000
    });

    return {
        syncedList,
        isLoading
    }
}