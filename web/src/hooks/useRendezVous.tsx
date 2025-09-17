import { useQuery } from "@tanstack/react-query";
import api from "@/api/axios";

export interface RendezVousDTO {
  id: string;
  userId: string; // Patient
  pro: {
    id: string;
    username: string;
  };
  dateTime: string;
  status: string; // RESERVED / CANCELED
}

export const useRendezVous = () => {
  const rendezVousQuery = useQuery({
    queryKey: ["rendezvous"],
    queryFn: async () => {
      const { data } = await api.get("/rendezvous/pro/me"); 
      return data as RendezVousDTO[];
    },
  });

  return { rendezVousQuery };
};
