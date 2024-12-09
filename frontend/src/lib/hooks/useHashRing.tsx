import useSWR from "swr";
import { HashRing } from "../p2p/HashRing";

export default function useHashRing() {
    const hashring = new HashRing();

    async function getRingView() {
        try {
            const res = await hashring.getViewFromNodes();

            const ringJson = res["ring"];
            const ring = new HashRing();
            
            console.log("BEFORE ADD NODES FROM JSON: ", ring);

            ring.addNodesFromJson(ringJson);

            console.log("AFTER ADD NODES FROM JSON: ", ring);

            return ring;
        } catch (error) {
            console.error("Failed to fetch ring: ", error);
        }
    }

    const { data: ring, isLoading } = useSWR("ring", getRingView, {
        refreshInterval: 10000
    });

    return {
        ring,
        isLoading
    }
}