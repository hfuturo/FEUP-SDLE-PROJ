import { useAppStore } from "@/lib/store";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "../ui/card";
import { Button } from "../ui/button";
import { Input } from "@/components/ui/input";
import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import { ShoppingList } from "@/lib/crdts/ShoppingList";

export const SelectLists = () => {
    const router = useRouter();
    const database = useAppStore((state) => state.database);
    const [shoppingLists, setShoppingLists] = useState<ShoppingList[]>([]);
    const [viewListId, setViewListId] = useState<string>("");
    const [error, setError] = useState<boolean>(false);

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
    }, [database]);

    return <Card className="p-4 shadow-md w-full mx-auto h-96 flex flex-col justify-center">
            <CardHeader>
                <CardTitle>View shopping list</CardTitle>
                <CardDescription>View shopping list</CardDescription>
            </CardHeader>
            <CardContent>
                <div className="flex flex-col max-h-36 overflow-y-auto my-4">
                    {shoppingLists.map((list) => (
                        <article key={list.id} className="flex flex-row gap-x-4 justify-center my-2">
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
                        onChange={(e) => setViewListId(e.target.value)}
                        value={viewListId}
                        style={{
                            borderColor: error ? "red" : undefined,
                            outlineColor: error ? "red" : undefined
                          }}
                    />
                    <Button
                    onClick={() => {
                        if (viewListId == "") {
                            setError(true);
                            return;
                        }
                        const sli = shoppingLists.find((sli) => sli.id === viewListId);
                        if (sli === undefined) {
                            setError(true);
                            return;
                        }

                        setError(false);
                        router.push(`/list/${viewListId}`);
                    }}
                    >
                        View
                    </Button>
                </div>
            </CardContent>
        </Card>
};