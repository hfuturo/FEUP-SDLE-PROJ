import { ShoppingListItem } from "@/lib/crdts/ShoppingListItem";
import { Card, CardContent } from "../ui/card";
import { Input } from "../ui/input";
import { Dispatch, SetStateAction, useEffect, useRef, useState } from "react";
import { ShoppingList } from "@/lib/crdts/ShoppingList";
import { Button } from "@/components/ui/button"

type Props = {
    shoppingListItem: ShoppingListItem;
    setShoppingList: Dispatch<SetStateAction<ShoppingList | null>>;
    shoppingList: ShoppingList;
    setSyncBlocked: Dispatch<SetStateAction<boolean>>;
}

export default function ShoppingListItemCard({ shoppingListItem, shoppingList, setShoppingList, setSyncBlocked }: Props) {
    const [quantity, setQuantity] = useState<number>(shoppingListItem.getQuantity());
    const [itemName, setItemName] = useState<string>();
    const nameRef = useRef(null);

    useEffect(() => {
        const names = shoppingListItem.getName().getValues();

        if(names.size < 2) {
            setItemName(names.values().next().value);
        }
    });

    return <Card key={shoppingListItem.getId()} className="p-4">
        <CardContent className="p-2 flex flex-row">
            <Input 
                key={`${shoppingListItem.getId()}-name-input`}
                className="w-3/4 p-2"
                type="text"
                onFocus={(e) => setSyncBlocked(true)}
                onBlur={(e) => setSyncBlocked(false)}
                onChange={((e) => {
                    setItemName(e.target.value);
                    shoppingListItem.changeName(e.target.value);
                    setShoppingList(shoppingList.clone());
                    
                })}
                value={itemName}
            />
            <Input 
                key={`${shoppingListItem.getId()}-quantity-input`}
                className="w-1/4" 
                type="number"
                onFocus={(e) => setSyncBlocked(true)}
                onBlur={(e) => setSyncBlocked(false)}
                onChange={(e) => {
                    const intValue = parseInt(e.target.value) || 0;
                    const diff = intValue - (quantity || 0);

                    setQuantity(Math.max(0, intValue));
                    shoppingListItem.updateQuantity(diff);

                    setShoppingList(shoppingList.clone()); 
                }}
                value={`${shoppingListItem.getQuantity()}`}
            />
            <Button 
                variant="destructive"
                onClick={() => {
                    shoppingList.remove(shoppingListItem.getId())
                    setShoppingList(shoppingList.clone())
                }}
            >Destructive</Button>
        </CardContent>
    </Card>
}