import { create } from 'zustand'
import { Database } from './database/Database'
import useHashRing from './hooks/useHashRing'
import { CRDTSyncService } from './crdts/CRDTSyncService'
import { HashRing } from './p2p/HashRing'

export const useAppStore = create((set) => ({
  database: new Database("ShoppingLists"),
  crdtSyncService: new CRDTSyncService(),
  ring: new HashRing(),
}))