import * as zmq from "zeromq";
import { NodeIdentifier } from "./p2p/NodeIdentifier";

class ServerConnection {
    private receiverNode: NodeIdentifier;
    private socket: zmq.Socket;

    constructor(receiverNode: NodeIdentifier) {
        this.receiverNode = receiverNode;

        this.socket = new zmq.Request();
        this.socket.connect(`tcp://${receiverNode.getHostname()}:${receiverNode.getPort()}`);
    }

    send(message: string): void {

    }
}

export { ServerConnection };
