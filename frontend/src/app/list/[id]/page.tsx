"use client"

import AddItemForm from "@/components/AddItemForm";
import ShoppingListItemCard from "@/components/ShoppingListItemCard";
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
    const { syncedList } = useCRDTUpdate(`${params.id}`, ring, database);
    const [syncBlocked, setSyncBlocked] = useState<boolean>(false);
    const [fetchedFromDbFirst, setFetchedFromDbFirst] = useState<boolean>(false);

    console.log("syncedList: ", syncedList);
    console.log("fetched from db first: ", fetchedFromDbFirst);

    useEffect(() => {
        if(!fetchedFromDbFirst) return;

        if(!syncBlocked && syncedList) {
           setShoppingList(syncedList);
        }
    }, [syncedList]);

    useEffect(() => {
        crdtSyncService.send(shoppingList, ring);
    }, [shoppingList]);

    useEffect(() => {
        const fetchShoppingList = async () => {
            try {
                // If it is not in our database, we have to fetch from server
                const list = await database.getShoppingList(params.id);

                console.log("Database list: ", list);

                if(list) {
                    const sl = ShoppingList.fromDatabase(list);
                    console.log("Shopping list from database: ", sl);
                    setShoppingList(sl);
                    crdtSyncService.send(sl, ring);
                    setFetchedFromDbFirst(true);
                } else {
                    const newList = await crdtSyncService.update(params.id, ring);
                    console.log("Fetched list: ", newList);
                    console.log("Fetched list from database: ", ShoppingList.fromDatabase(newList))
                    setShoppingList(ShoppingList.fromDatabase(newList));
                }
            } catch (error) {
                console.error("Failed to fetch shopping lists:", error);
            }
        };

        fetchShoppingList();
    }, [params, ring]);

    useEffect(() => {
        if(!fetchedFromDbFirst) return;

        const updateShoppingList = async () => {
            await database.updateShoppingList(shoppingList?.getId(), shoppingList);
        };

        if(shoppingList) {
            updateShoppingList();
        }
    }, [shoppingList]);

    return <div className="flex flex-col mx-auto items-center w-1/2 mt-16 gap-y-4">
        <h1 className="text-center text-3xl">List</h1>
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