import useSWR from "swr";
import { HashRing } from "../p2p/HashRing";

export default function useHashRing() {
    const hashring = new HashRing();

    async function getRingView() {
        const res = await hashring.getViewFromNodes();

        return res["ring"];
    }

    const { data: ring, isLoading } = useSWR("ring", getRingView);

    return {
        ring,
        isLoading
    }
}