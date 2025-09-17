import { create } from "zustand";
import type { AuthResponse } from "../types";

interface AuthState {
  user: AuthResponse["user"] | null;
  token: string | null;
  setAuth: (auth: AuthResponse) => void;
  logout: () => void;
}

export const useAuthStore = create<AuthState>((set) => ({
  user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user") as string) : null,
  token: localStorage.getItem("token"),
  setAuth: (auth) => {
    localStorage.setItem("token", auth.token);
    localStorage.setItem("user", JSON.stringify(auth.user));
    set({ user: auth.user, token: auth.token });
  },
  logout: () => {
    localStorage.removeItem("token");
    localStorage.removeItem("user");
    set({ user: null, token: null });
  },
}));
