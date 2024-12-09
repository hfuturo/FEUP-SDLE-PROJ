import useSWR from "swr";
import { CRDTSyncService } from "../crdts/CRDTSyncService";
import { ShoppingList } from "../crdts/ShoppingList";
import { HashRing } from "../p2p/HashRing";

export default function useCRDTUpdate(id: string, ring: HashRing) {
    const syncService = new CRDTSyncService();
    const { data: syncedList, isLoading } = useSWR("crdt-sync-service-" + id, async () => {
        return await syncService.update(id, ring);
    }, {
        refreshInterval: 4000
    });

    return {
        syncedList,
        isLoading
    }
}