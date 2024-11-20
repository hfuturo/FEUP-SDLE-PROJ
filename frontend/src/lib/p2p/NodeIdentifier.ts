export class NodeIdentifier {
  private id: number;
  private hostname: string;
  private port: number;
  private apiPort: number;
  private alive: boolean;

  constructor(id: number, hostname: string, port: number, apiPort: number, alive: boolean) {
    this.id = id;
    this.hostname = hostname;
    this.port = port;
    this.apiPort = apiPort;
    this.alive = alive;
  }

  getId(): number {
    return this.id;
  }

  getApiPort(): number {
    return this.apiPort;
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
