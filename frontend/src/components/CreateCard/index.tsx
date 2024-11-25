import { HashRing } from "@/lib/p2p/HashRing";
import { Button } from "../ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "../ui/card";
import { ShoppingList } from "@/lib/shoppinglist/ShoppingList";
import { useRouter } from "next/navigation";
import useHashRing from "@/lib/hooks/useHashRing";
import { useAppStore } from "@/lib/store";

export function CreateCard() {
    const router = useRouter();
    const { ring } = useHashRing();
    const database = useAppStore((state) => state.database);

    return (
        <Card className="p-4 shadow-md w-full mx-auto h-60 flex flex-col justify-center">
            <CardHeader>
                <CardTitle>Create shopping list</CardTitle>
                <CardDescription>Create shopping list</CardDescription>
            </CardHeader>
            <CardContent>
                <Button 
                    className="w-full"
                    onClick={async () => {
                        const shoppingList: ShoppingList | null = await ShoppingList.createShoppingList(ring);
                        
                        if(shoppingList) {
                            database.createShoppingList(shoppingList);
                            router.push(`/list/${shoppingList.id}`);
                        }
                    }}
                >
                    Create
                </Button>
            </CardContent>
        </Card>
    )
}