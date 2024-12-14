import { ShoppingListItem } from "@/lib/crdts/ShoppingListItem";
import { Card, CardContent } from "../ui/card";
import { Input } from "../ui/input";
import { Dispatch, SetStateAction, useEffect, useState } from "react";
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
    const [itemNames, setItemNames] = useState<string[]>([]);
    const [selectedName, setSelectedName] = useState<string | null>(null);

    useEffect(() => {
        const names = Array.from(shoppingListItem.getName().getValues());
        setItemNames(names);
        if (names.length === 1) {
            setSelectedName(names[0]);
        }
    }, [shoppingList]);

    const handleNameSelect = (name: string) => {
        setSelectedName(name);
        shoppingListItem.changeName(name);
        setShoppingList(shoppingList.clone());
    };

    return (
        <Card key={shoppingListItem.getId()} className="p-4">
            <CardContent className="p-2 flex flex-row">
                <div className="w-3/4 space-x-2">
                    {selectedName ? (
                        <Input
                            key={`${shoppingListItem.getId()}-name-input`}
                            className="p-2"
                            type="text"
                            onFocus={() => setSyncBlocked(true)}
                            onBlur={() => setSyncBlocked(false)}
                            onChange={(e) => {
                                setSelectedName(e.target.value);
                                shoppingListItem.changeName(e.target.value);
                                setShoppingList(shoppingList.clone());
                            }}
                            value={selectedName}
                        />
                    ) : (
                        itemNames.map((name) => (
                            <Button
                                key={`${shoppingListItem.getId()}-${name}-button`}
                                variant="outline"
                                onClick={() => handleNameSelect(name)}
                                className="mb-1 border-yellow-500"
                            >
                                {name}
                            </Button>
                        ))
                    )}
                </div>
                <Input
                    key={`${shoppingListItem.getId()}-quantity-input`}
                    className="w-1/4"
                    type="number"
                    onFocus={() => setSyncBlocked(true)}
                    onBlur={() => setSyncBlocked(false)}
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
                    className="ml-3"
                    variant="destructive"
                    onClick={() => {
                        shoppingList.remove(shoppingListItem.getId());
                        setShoppingList(shoppingList.clone())
                    }}
                >X</Button>
            </CardContent>
        </Card>
    );
}
