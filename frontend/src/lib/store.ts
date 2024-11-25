import { create } from 'zustand'
import { Database } from './database/Database'

export const useAppStore = create((set) => ({
  database: new Database("ShoppingLists")
}))