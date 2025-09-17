export interface User {
  id: string;
  name: string;
  lastName: string;
  username: string;
  email: string;
  role: "PATIENT" | "PRO" ;
  score: number; // Optional, only for PRO users
  specialty: string; // Optional, only for PRO users

}