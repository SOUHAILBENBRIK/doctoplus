import { useState } from "react";
import { useDisponibilities } from "@/hooks/useDisponibilities";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import type { Disponibility } from "@/types/disponibility";

export default function DisponibilitiesPage() {
  const { disponibilitiesQuery, addDisponibilityMutation, deleteDisponibilityMutation } =
    useDisponibilities();

  const [startTime, setStartTime] = useState("");
  const [endTime, setEndTime] = useState("");

  const handleAdd = () => {
    if (!startTime || !endTime) return;
    addDisponibilityMutation.mutate({ startTime, endTime });
    setStartTime("");
    setEndTime("");
  };

  if (disponibilitiesQuery.isLoading) return <p>Loading...</p>;
  if (disponibilitiesQuery.isError) return <p>Error loading disponibilities.</p>;

  const disponibilities = disponibilitiesQuery.data;
  console.log(disponibilities);


  return (
    <div className="p-6 max-w-4xl mx-auto">
      <h1 className="text-2xl font-bold mb-6">My Disponibilities</h1>

      {/* Add New Disponibility */}
      <div className="flex gap-4 mb-6">
        <Input
          type="datetime-local"
          value={startTime}
          onChange={(e) => setStartTime(e.target.value)}
          className="border rounded-xl px-3 py-2"
          placeholder="Start Time"
        />
        <Input
          type="datetime-local"
          value={endTime}
          onChange={(e) => setEndTime(e.target.value)}
          className="border rounded-xl px-3 py-2"
          placeholder="End Time"
        />
        <Button onClick={handleAdd} className="bg-blue-600 text-white rounded-xl px-6">
          Add
        </Button>
      </div>

      {/* Disponibilities List */}
      <div className="space-y-4">
        {disponibilities.length === 0 && <p>No disponibilities yet.</p>}
        {disponibilities.map((dispo: Disponibility) => (
          <div
            key={dispo.id}
            className="flex justify-between items-center border rounded-xl px-4 py-2 bg-white shadow-sm"
          >
            <div>
              <p>
                <span className="font-semibold">Start:</span> {new Date(dispo.startTime).toLocaleString()}
              </p>
              <p>
                <span className="font-semibold">End:</span> {new Date(dispo.endTime).toLocaleString()}
              </p>
            </div>
            <Button
              onClick={() => deleteDisponibilityMutation.mutate(dispo.id)}
              className="bg-red-500 text-white rounded-xl px-4 py-2"
            >
              Delete
            </Button>
          </div>
        ))}
      </div>
    </div>
  );
}
