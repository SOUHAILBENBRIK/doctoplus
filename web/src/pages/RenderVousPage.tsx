import { useRendezVous } from "@/hooks/useRendezVous";
import { useState } from "react";
import { format } from "date-fns";

export default function RendezVousPage() {
  const { rendezVousQuery } = useRendezVous();
  const [selectedStatus, setSelectedStatus] = useState<string>("ALL");

  if (rendezVousQuery.isLoading) {
    return (
      <div className="flex items-center justify-center h-screen">
        <p className="text-slate-700 text-lg">Loading rendezvous...</p>
      </div>
    );
  }

  if (rendezVousQuery.isError) {
    return (
      <div className="flex items-center justify-center h-screen">
        <p className="text-red-600 text-lg">
          Error fetching rendezvous. Please try again.
        </p>
      </div>
    );
  }

  const filteredRendezvous =
    selectedStatus === "ALL"
      ? rendezVousQuery.data
      : rendezVousQuery.data?.filter((rv) => rv.status === selectedStatus);

  return (
    <div className="p-6 min-h-screen bg-slate-50">
      <h1 className="text-2xl font-bold mb-6 text-slate-800">My Rendezvous</h1>

      {/* Status Filter */}
      <div className="mb-4 flex items-center gap-4">
        {["ALL", "RESERVED", "CANCELED"].map((status) => (
          <button
            key={status}
            className={`px-4 py-2 rounded-xl font-medium border transition-colors duration-200 ${
              selectedStatus === status
                ? "bg-blue-600 text-white border-blue-600"
                : "bg-white text-slate-700 border-slate-300 hover:bg-blue-50"
            }`}
            onClick={() => setSelectedStatus(status)}
          >
            {status}
          </button>
        ))}
      </div>

      {/* Rendezvous List */}
      <div className="space-y-4">
        {filteredRendezvous?.length === 0 && (
          <p className="text-slate-500 text-center">No rendezvous found.</p>
        )}

        {filteredRendezvous?.map((rv) => (
          <div
            key={rv.id}
            className="flex justify-between items-center p-4 bg-white rounded-xl shadow-sm hover:shadow-md transition-shadow duration-200"
          >
            <div className="flex flex-col">
              <p className="font-medium text-slate-700">
                Patient ID: {rv.userId}
              </p>
              <p className="text-sm text-slate-500">
                {format(new Date(rv.dateTime), "dd MMM yyyy, HH:mm")}
              </p>
            </div>
            <span
              className={`px-3 py-1 rounded-full font-medium text-sm ${
                rv.status === "RESERVED"
                  ? "bg-green-100 text-green-700"
                  : rv.status === "CANCELED"
                  ? "bg-red-100 text-red-700"
                  : "bg-gray-100 text-gray-700"
              }`}
            >
              {rv.status}
            </span>
          </div>
        ))}
      </div>
    </div>
  );
}
