"use client"

import { useAppStore } from "@/lib/store";
import { useParams } from "next/navigation";

export default function List() {
    const params = useParams();    
    const database = useAppStore((state) => state.database);

    return <h1>{params.id}</h1>
}