
import api from "./axios";
import type { RegisterDTO, LoginDTO, AuthResponse  } from "@/types";

export const register = (data: RegisterDTO) =>
  api.post<AuthResponse>("/auth/register", data).then(res => res.data);


export const login = (data: LoginDTO) =>
  api.post<AuthResponse>("/auth/login", data).then(res => res.data);



