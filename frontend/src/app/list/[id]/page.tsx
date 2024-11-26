"use client"

import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { ShoppingList } from "@/lib/shoppinglist/ShoppingList";
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
                setShoppingList(new ShoppingList(list.id, list.items));
            } catch (error) {
                console.error("Failed to fetch shopping lists:", error);
            }
        };

        fetchShoppingList();
    }, [params]);

    return <div className="flex flex-col mx-auto items-center w-1/2 mt-16 gap-y-4">
        <h1 className="text-center text-3xl">List</h1>
        <form className="flex flex-row gap-x-2" onSubmit={(e) => {
            e.preventDefault();

            console.log(shoppingList)
            if (shoppingList) shoppingList.addItem({});
        }}>
            <Input className="w-full" placeholder="Product name" required />
            <Button type="submit">
                Add
            </Button>
        </form>
        <div className="flex flex-col gap-y-4">
            {shoppingList === null
                ? <p>No items added.</p>
                : <div>
                    <></>
                </div>
            }
        </div>
    </div>
}