import { useAppStore } from "@/lib/store";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "../ui/card";
import { Button } from "../ui/button";
import { Input } from "@/components/ui/input";
import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";

export const SelectLists = () => {
    const router = useRouter();
    const database = useAppStore((state) => state.database);
    const [shoppingLists, setShoppingLists] = useState<ShoppingList[]>([]);

    useEffect(() => {
        const fetchShoppingLists = async () => {
            try {
                const lists = await database.getShoppingLists();
                setShoppingLists(lists);
            } catch (error) {
                console.error("Failed to fetch shopping lists:", error);
            }
        };

        fetchShoppingLists();
    });

    return <Card className="p-4 shadow-md w-full mx-auto h-96 flex flex-col justify-center">
            <CardHeader>
                <CardTitle>View shopping list</CardTitle>
                <CardDescription>View shopping list</CardDescription>
            </CardHeader>
            <CardContent>
                <div className="flex flex-col max-h-36 overflow-y-auto my-4">
                    {shoppingLists.map((list) => (
                        <article className="flex flex-row gap-x-4 justify-center my-2">
                            <p>{list.id}</p>
                            <Button
                                onClick={() => {
                                    router.push(`/list/${list.id}`);
                                }}
                            >
                                View
                            </Button>
                        </article>
                    ))}
                </div>
                <div className="flex flex-col gap-y-2">
                    <Input
                        placeholder="Enter list id"
                    />
                    <Button>View</Button>
                </div>
            </CardContent>
        </Card>
};