import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import api from "@/api/axios";
export interface DisponibilityDTO {
  id?: string;
  startTime: string;
  endTime: string;
}

export const useDisponibilities = () => {
  const queryClient = useQueryClient();

  const disponibilitiesQuery = useQuery({
    queryKey: ["disponibilities"],
    queryFn: async () => {
      const { data } = await api.get("/disponibilities/pro/me"); 
      return data;
    },
  });

  const addDisponibilityMutation = useMutation({
    mutationFn: (newDispo: DisponibilityDTO) =>
      api.post("/disponibilities", newDispo),
    onSuccess: () => queryClient.invalidateQueries({ queryKey: ["disponibilities"] }),
  });

  const deleteDisponibilityMutation = useMutation({
    mutationFn: (id: string) => api.delete(`/disponibilities/${id}`),
    onSuccess: () => queryClient.invalidateQueries({ queryKey: ["disponibilities"] }),
  });

  return { disponibilitiesQuery, addDisponibilityMutation, deleteDisponibilityMutation };
};
