import type { User } from "./user";

export interface Disponibility {
    id: string;
    startTime: string;
    endTime: string;
    user: User;
    slots?: string[];
}   
