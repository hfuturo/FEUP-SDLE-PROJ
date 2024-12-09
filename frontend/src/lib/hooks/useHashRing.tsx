import useSWR from "swr";
import { HashRing } from "../p2p/HashRing";

export default function useHashRing() {
    const hashring = new HashRing();

    async function getRingView() {
        try {
            const res = await hashring.getViewFromNodes();

            const ringJson = res["ring"];
            const ring = new HashRing();

            ring.addNodesFromJson(ringJson);

            return ring;
        } catch (error) {
            console.error("Failed to fetch ring: ", error);
        }
    }

    const { data: ring, isLoading } = useSWR("ring", getRingView);

    return {
        ring,
        isLoading
    }
}