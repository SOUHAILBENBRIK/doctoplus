export interface RegisterDTO {
  name: string;
  email: string;
  password: string;
  role: "PRO";
  score : number;
  specialty: string;
}

export interface LoginDTO {
  email: string;
  password: string;
}

export interface AuthResponse {
  token: string;
  user: {
    id: string;
    name: string;
    username: string;
    email: string;
    role: "PATIENT" | "PRO";
  };
}