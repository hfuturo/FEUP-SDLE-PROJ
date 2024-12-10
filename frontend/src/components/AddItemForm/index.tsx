import { ShoppingList } from "@/lib/crdts/ShoppingList";
import { Input } from "../ui/input";
import { Button } from "../ui/button";
import { Dispatch, SetStateAction, useState } from "react";
import { useAppStore } from "@/lib/store";

type Props = {
    shoppingList: ShoppingList | null;
    setShoppingList: Dispatch<SetStateAction<ShoppingList | null>>;
    setSyncBlocked: Dispatch<SetStateAction<boolean>>;
}

export default function AddItemForm({ shoppingList, setShoppingList, setSyncBlocked}: Props) {
    const [itemName, setItemName] = useState<string>("");
    const database = useAppStore((state) => state.database);

    return <form className="flex flex-row gap-x-2" onSubmit={async (e) => {
        e.preventDefault();

        if (shoppingList) {
            shoppingList.addItem(crypto.randomUUID(), itemName, 0);
            setShoppingList(shoppingList.clone());
        }

        setItemName("");
    }}>
        <Input 
            className="w-full" 
            placeholder="Item name" 
            onFocus={(e) => setSyncBlocked(true)}
            onBlur={(e) => setSyncBlocked(false)}
            onChange={(e) => setItemName(e.target.value)}
            value={itemName}
            required 
        />
        <Button type="submit">
            Add
        </Button>
    </form>
}