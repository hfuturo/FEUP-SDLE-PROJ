import { ShoppingList } from "../crdts/ShoppingList";

/**
 * Wrapper to interact with indexed db from the browser
 */
export class Database {
    private db: IDBDatabase;
    private dbName: string;

    constructor(dbName: string) {
        this.dbName = dbName;
        const request = window.indexedDB.open(dbName, 1);

        request.onerror = (event) => {
            console.error("Error while creating database: ", event);
        };

        request.onsuccess = (event) => {
            console.log("Database created");
            this.db = event.target.result; 
        };

        request.onupgradeneeded = (event) => {
            this.db = event.target.result;
            this.db.createObjectStore(this.dbName, {
                keyPath: "id"
            });
        };
    }

    initialized() {
        return this.db !== undefined;
    }

    createShoppingList(list: ShoppingList) {
        const transaction = this.db.transaction(this.dbName, "readwrite");
        const store = transaction.objectStore(this.dbName);

        const request = store.add(list);

        request.onsuccess = (event) => {
            console.log("Shopping list created");
        };

        request.onerror = (event) => {
            console.error(event);
        };
    }

    async updateShoppingList(listId: string, list: ShoppingList) {
        return new Promise<void>((resolve, reject) => {
            // delete list with listId
            const transaction = this.db.transaction(this.dbName, "readwrite");
            const store = transaction.objectStore(this.dbName);
            const request = store.delete(listId);

            request.onsuccess = (event) => {
                const request2 = store.add(list.toSerializable());

                request2.onsuccess = (event) => {
                    console.log("Shopping list updated");
                    resolve();
                };

                request2.onerror = (event) => {
                    console.error(event);
                    reject(event);
                };
            };
        });
    }

    async getShoppingList(id: string) {
        return new Promise((resolve, reject) => {
            if(!this.initialized()) {
                console.log("Database not initialized");
                return reject(null);
            }
            
            const transaction = this.db.transaction(this.dbName, "readonly");
            const store = transaction.objectStore(this.dbName);
            const request = store.get(id);
    
            request.onsuccess = (event) => {
                const shoppingList = request.result;

                console.log("Shopping list retrieved: ", shoppingList);

                resolve(shoppingList);
            };
    
            request.onerror = (event) => {
                console.error(event);
                reject(null);
            };
        })
    }

    async getShoppingLists() {  
        return new Promise((resolve, reject) => {
            const transaction = this.db.transaction(this.dbName, "readonly");
            const store = transaction.objectStore(this.dbName);
            const request = store.getAll();
    
            request.onsuccess = (event) => {
                console.log("Shopping lists retrieved");
                const shoppingLists = request.result;

                resolve(shoppingLists);
            };
    
            request.onerror = (event) => {
                console.error(event);
                reject(event);
            };
        })      
    }
}