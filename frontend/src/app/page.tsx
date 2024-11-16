import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import Link from "next/link";

export default function Home() {
    return <>
        <div className="flex flex-col mx-auto w-1/2 gap-x-2 mt-40">
            <h1 className="text-4xl font-bold text-center mb-8">!!Ka-Chow!!</h1>
            <div className="flex flex-row w-full gap-x-2">
                <Card className="p-4 shadow-md w-full mx-auto h-60 flex flex-col justify-center">
                    <CardHeader>
                        <CardTitle>Create shopping list</CardTitle>
                        <CardDescription>Create shopping list</CardDescription>
                    </CardHeader>
                    <CardContent>
                        <Link href="/create">
                            <Button className="w-full">
                                Create
                            </Button>
                        </Link>
                    </CardContent>
                </Card>

                <Card className="p-4 shadow-md w-full mx-auto h-60 flex flex-col justify-center">
                    <CardHeader>
                        <CardTitle>View shopping list</CardTitle>
                        <CardDescription>View shopping list</CardDescription>
                    </CardHeader>
                    <CardContent>
                        <div className="flex flex-col gap-y-2">
                            <Input
                                placeholder="Enter list id"
                            />
                            <Button>View</Button>
                        </div>
                    </CardContent>
                </Card>
            </div>
        </div>
    </>;
}
