"use client"

import AddItemForm from "@/components/AddItemForm";
import ShoppingListItemCard from "@/components/ShoppingListItemCard";
import { CRDTSyncService } from "@/lib/crdts/CRDTSyncService";
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
    const { syncedList } = useCRDTUpdate(`${params.id}`, ring);

    useEffect(() => {
        console.log("Currently shopping list: ", shoppingList);
        crdtSyncService.send(shoppingList, ring);
    }, [shoppingList, ring]);

    useEffect(() => {
        const fetchShoppingList = async () => {
            try {
                const list = await database.getShoppingList(params.id);

                console.log("BEFORE LIST: ", list);

                const sl = ShoppingList.fromDatabase(list);

                console.log("ANOTHER SKILL ISSUE: ", sl);

                setShoppingList(sl);
                crdtSyncService.send(sl, ring);
            } catch (error) {
                console.error("Failed to fetch shopping lists:", error);
            }
        };

        fetchShoppingList();
    }, [params]);

    useEffect(() => {
        console.log("how many times this ran?");
        const updateShoppingList = async () => {
            await database.updateShoppingList(shoppingList?.getId(), shoppingList);
        };

        if(shoppingList) {
            updateShoppingList();
        }
    }, [shoppingList]);

    return <div className="flex flex-col mx-auto items-center w-1/2 mt-16 gap-y-4">
        <h1 className="text-center text-3xl">List</h1>
        <AddItemForm shoppingList={shoppingList} setShoppingList={setShoppingList} />
        <div className="flex flex-col gap-y-4">
            {shoppingList && Array.from(shoppingList.getItems().getValues().values()).map((value, idx) => (
                <ShoppingListItemCard 
                    key={"shopping-list-item-" + idx} 
                    shoppingListItem={value.value} 
                    setShoppingList={setShoppingList}
                    shoppingList={shoppingList}
                />
            ))}
        </div>
    </div>
}