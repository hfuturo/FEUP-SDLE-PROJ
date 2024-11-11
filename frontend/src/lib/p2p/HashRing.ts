import { NodeIdentifier } from "./NodeIdentifier";

/**
 * A hash ring implementation where we can compute which node to contact.
 */
export class HashRing {
  private ring: Map<string, NodeIdentifier> = new Map();

}
