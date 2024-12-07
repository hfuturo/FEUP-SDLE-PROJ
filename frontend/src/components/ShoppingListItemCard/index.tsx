import { ShoppingListItem } from "@/lib/crdts/ShoppingListItem";
import { Card, CardContent } from "../ui/card";
import { Input } from "../ui/input";
import { Dispatch, SetStateAction, useEffect, useState } from "react";
import { ShoppingList } from "@/lib/crdts/ShoppingList";

type Props = {
    shoppingListItem: ShoppingListItem;
    setShoppingList: Dispatch<SetStateAction<ShoppingList | null>>;
    shoppingList: ShoppingList;
}

export default function ShoppingListItemCard({ shoppingListItem, shoppingList, setShoppingList }: Props) {
    const [quantity, setQuantity] = useState<number>(shoppingListItem.getQuantity());
    
    return <Card key={shoppingListItem.getId()}>
        <CardContent className="p-2 flex flex-row">
            <p className="w-3/4 p-2">{shoppingListItem.getName()}</p>
            <Input 
                className="w-1/4" 
                type="number"
                onChange={(e) => {
                    const intValue = parseInt(e.target.value) || 0;
                    const diff = intValue - (quantity || 0);

                    setQuantity(Math.max(0, intValue));
                    shoppingListItem.updateQuantity(diff);

                    setShoppingList(shoppingList.clone()); 
                }}
                value={`${shoppingListItem.getQuantity()}`}
            />
        </CardContent>
    </Card>
}