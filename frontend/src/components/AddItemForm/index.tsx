import { ShoppingList } from "@/lib/crdts/ShoppingList";
import { Input } from "../ui/input";
import { Button } from "../ui/button";
import { useState } from "react";
import { useAppStore } from "@/lib/store";

type Props = {
    shoppingList: ShoppingList | null;
}

export default function AddItemForm({ shoppingList }: Props) {
    const [itemName, setItemName] = useState<string>("");
    const database = useAppStore((state) => state.database);

    console.log("current list: ", shoppingList);

    return <form className="flex flex-row gap-x-2" onSubmit={async (e) => {
        e.preventDefault();

        if (shoppingList) {
            shoppingList.addItem("", itemName, 0);
            await database.updateShoppingList(shoppingList.getId(), shoppingList);
        }
    }}>
        <Input 
            className="w-full" 
            placeholder="Item name" 
            onChange={(e) => setItemName(e.target.value)}
            required 
        />
        <Button type="submit">
            Add
        </Button>
    </form>
}