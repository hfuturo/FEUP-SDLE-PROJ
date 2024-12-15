# SDLE Assignment

SDLE Assignment of group T07G13.

Group members:

1. Henrique Silva (<up202105647@up.pt>)
2. João Fernandes (<up202108867@up.pt>)
3. Tomás Palma (<up202108880@up.pt>)

# How to run

## Backend

```bash
cd backend
```

Run each node on a different terminal.

### Starter Nodes

```bash
./gradlew bootRun --args='--node.id=100001 --node.hostname=localhost --node.port=4321 --node.starter=true --api.port=8081'
```

```bash
./gradlew bootRun --args='--node.id=100002 --node.hostname=localhost --node.port=4322 --node.starter=true --api.port=8082'
```

```bash
./gradlew bootRun --args='--node.id=100003 --node.hostname=localhost --node.port=4323 --node.starter=true --api.port=8083'
```

### Non-Starter Nodes

```bash
./gradlew bootRun --args='--node.id=<id> --node.hostname=<hostname> --node.port=<port> --node.starter=false --api.port=<port>'
```

Example:

```bash
./gradlew bootRun --args='--node.id=40 --node.hostname=localhost --node.port=4325 --node.starter=false --api.port=8085'
```

## Frontend

```bash
cd frontend
npm install
npm run dev
```
