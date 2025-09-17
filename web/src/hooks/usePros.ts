import { useQuery } from "@tanstack/react-query";
import api from "@/api/axios";
import type { User } from "@/types";



export const usePros = () => {
  const prosQuery = useQuery({
    queryKey: ["pros"],
    queryFn: async () => {
      const { data } = await api.get<User[]>("/users/pros");
      return data;
    },
  });

  return { prosQuery };
};
