import { ShoppingListItem } from "@/lib/crdts/ShoppingListItem";
import { Card, CardContent } from "../ui/card";
import { Input } from "../ui/input";
import { useEffect, useState } from "react";

type Props = {
    shoppingListItem: ShoppingListItem;
}

export default function ShoppingListItemCard({ shoppingListItem }: Props) {
    const [quantity, setQuantity] = useState<number>(shoppingListItem.getQuantity());
    
    return <Card key={shoppingListItem.getId()}>
        <CardContent className="p-2 flex flex-row">
            <p className="w-3/4 p-2">{shoppingListItem.getName()}</p>
            <Input 
                className="w-1/4" 
                type="number"
                onChange={(e) => {
                    setQuantity(parseInt(e.target.value));
                    shoppingListItem.updateQuantity(parseInt(e.target.value));
                }}
                value={quantity} 
            />
        </CardContent>
    </Card>
}