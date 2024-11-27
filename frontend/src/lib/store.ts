import { create } from 'zustand'
import { Database } from './database/Database'
import useHashRing from './hooks/useHashRing'

export const useAppStore = create((set) => ({
  database: new Database("ShoppingLists"),
}))