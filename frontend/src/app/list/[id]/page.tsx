"use client"

import AddItemForm from "@/components/AddItemForm";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { ShoppingList } from "@/lib/crdts/ShoppingList";
import { useAppStore } from "@/lib/store";
import { useParams } from "next/navigation";
import { useEffect, useState } from "react";

export default function List() {
    const params = useParams();
    const database = useAppStore((state) => state.database);
    const [shoppingList, setShoppingList] = useState<ShoppingList | null>(null);

    useEffect(() => {
        const fetchShoppingList = async () => {
            try {
                const list = await database.getShoppingList(params.id);
                setShoppingList(new ShoppingList(50, list.id));
            } catch (error) {
                console.error("Failed to fetch shopping lists:", error);
            }
        };

        fetchShoppingList();
    }, [params]);

    return <div className="flex flex-col mx-auto items-center w-1/2 mt-16 gap-y-4">
        <h1 className="text-center text-3xl">List</h1>
        <AddItemForm shoppingList={shoppingList} />
        <div className="flex flex-col gap-y-4">
            {shoppingList === null
                ? <p>No items added.</p>
                : shoppingList.getItems().getValues().entries().map(([key, value]) => (
                   <article key={key} className="flex flex-row gap-x-2">
                        <p>{key}</p>
                    </article>
                ))
            }
        </div>
    </div>
}