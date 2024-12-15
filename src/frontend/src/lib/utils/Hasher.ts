import { createHash } from "crypto";

export class Hasher {
    static md5(str: string) {
        const md5Hash = createHash('md5');
        md5Hash.update(str);
    
        return md5Hash.digest('hex');
    }
}