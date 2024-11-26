"use client"

import { CreateCard } from "@/components/CreateCard";
import { SelectLists } from "@/components/SelectLists";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Input } from "@/components/ui/input";

export default function Home({ }) {
    return <>
        <div className="flex flex-col mx-auto w-1/2 gap-x-2 mt-40">
            <h1 className="text-4xl font-bold text-center mb-8">!!Ka-Chow!!</h1>
            <div className="flex flex-row w-full gap-x-2">
                <CreateCard />
                <SelectLists />
            </div>
        </div>
    </>;
}
