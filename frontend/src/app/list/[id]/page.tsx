"use client"

import AddItemForm from "@/components/AddItemForm";
import ShoppingListItemCard from "@/components/ShoppingListItemCard";
import { Switch } from "@/components/ui/switch";
import { ShoppingList } from "@/lib/crdts/ShoppingList";
import useCRDTUpdate from "@/lib/hooks/useCRDTUpdate";
import useHashRing from "@/lib/hooks/useHashRing";
import { useAppStore } from "@/lib/store";
import { useParams } from "next/navigation";
import { useEffect, useState } from "react";

export default function List() {
    const { ring } = useHashRing();
    const params = useParams();
    const database = useAppStore((state) => state.database);
    const crdtSyncService = useAppStore((state) => state.crdtSyncService);
    const [shoppingList, setShoppingList] = useState<ShoppingList | null>(null);
    const [syncBlocked, setSyncBlocked] = useState<boolean>(false);
    const [offline, setOffline] = useState<boolean>(false);
    const { syncedList } = useCRDTUpdate(`${params.id}`, ring, database, offline);

    console.log("Current shopping list: ", shoppingList);

    useEffect(() => {
        if(shoppingList) return;

        const fetchFromDb = async () => {
            const list = await database.getShoppingList(params.id);

            if(list) {
                const sl = ShoppingList.fromDatabase(list);
               
                if(sl) setShoppingList(sl);
            }
        };

        fetchFromDb();
    });

    useEffect(() => {
        if(!syncBlocked && syncedList) {
           setShoppingList(syncedList);
        }
    }, [syncedList]);

    useEffect(() => {
        if(!offline) crdtSyncService.send(shoppingList, ring);
    }, [shoppingList]);

    useEffect(() => {
        const fetchShoppingList = async () => {
            try {
                // If it is not in our database, we have to fetch from server
                const list = await database.getShoppingList(params.id);

                if(list) {
                    const sl = ShoppingList.fromDatabase(list);
                    console.log("Shopping list from database: ", sl);
                    if(sl) setShoppingList(sl);
                    if(!offline) crdtSyncService.send(sl, ring);
                } else if(!offline) {
                    const newList = await crdtSyncService.update(params.id, ring);
                    console.log("Fetched list: ", newList);
                    console.log("Fetched list from database: ", ShoppingList.fromDatabase(newList))
                    const serializedSl= ShoppingList.fromDatabase(newList);
                    if(serializedSl) setShoppingList(serializedSl);
                }
            } catch (error) {
                console.error("Failed to fetch shopping lists:", error);
            }
        };

        fetchShoppingList();
    }, [params, ring]);

    useEffect(() => {
        const updateShoppingList = async () => {
            await database.updateShoppingList(shoppingList?.getId(), shoppingList);
        };

        if(shoppingList) {
            updateShoppingList();
        }
    }, [shoppingList]);

    return <div className="flex flex-col mx-auto items-center w-1/2 mt-16 gap-y-4">
        <div className="flex flex-row justify-center items-center gap-x-4">
            <h1 className="text-center text-3xl">List</h1>
            <div className="flex flex-row">
                <Switch
                    checked={offline}
                    onCheckedChange={(checked) => setOffline(checked)}
                    id="offline-switch"
                />
                <label htmlFor="offline-switch" className="ml-2">Offline</label>
            </div>
        </div>
        <AddItemForm 
            shoppingList={shoppingList} 
            setShoppingList={setShoppingList} 
            setSyncBlocked={setSyncBlocked}
        />
        <div className="flex flex-col gap-y-4">
            {shoppingList && Array.from(shoppingList.getItems().getValues().values()).map((value, idx) => (
                <ShoppingListItemCard 
                    key={"shopping-list-item-" + idx} 
                    shoppingListItem={value.value} 
                    setShoppingList={setShoppingList}
                    shoppingList={shoppingList}              
                    setSyncBlocked={setSyncBlocked}  
                />
            ))}
        </div>
    </div>
}