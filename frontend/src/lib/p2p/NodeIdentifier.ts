export class NodeIdentifier {
  private id: number;
  private hostname: string;
  private port: number;
  private alive: boolean;

  constructor(id: number, hostname: string, port: number, alive: boolean) {
    this.id = id;
    this.hostname = hostname;
    this.port = port;
    this.alive = alive;
  }

  getId(): number {
    return this.id;
  }

  getHostname(): string {
    return this.hostname;
  }

  getPort(): number {
    return this.port;
  }

  isAlive(): boolean {
    return this.alive;
  }
}
