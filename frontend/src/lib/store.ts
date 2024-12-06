import { create } from 'zustand'
import { Database } from './database/Database'
import useHashRing from './hooks/useHashRing'
import { CRDTSyncService } from './crdts/CRDTSyncService'

export const useAppStore = create((set) => ({
  database: new Database("ShoppingLists"),
  crdtSyncService: new CRDTSyncService(),
}))