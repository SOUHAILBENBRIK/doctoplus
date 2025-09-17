import { usePros } from "@/hooks/usePros";
import { useState } from "react";

export default function ProsPage() {
  const { prosQuery } = usePros();
  const [selectedSort, setSelectedSort] = useState<"asc" | "desc">("desc");

  if (prosQuery.isLoading) {
    return (
      <div className="flex items-center justify-center h-screen">
        <p className="text-slate-700 text-lg">Loading pros...</p>
      </div>
    );
  }

  if (prosQuery.isError) {
    return (
      <div className="flex items-center justify-center h-screen">
        <p className="text-red-600 text-lg">Error fetching pros. Please try again.</p>
      </div>
    );
  }

  // Sort by score (descending by default)
  const sortedPros = [...(prosQuery.data ?? [])].sort((a, b) =>
    selectedSort === "desc" ? b.score - a.score : a.score - b.score
  );

  return (
    <div className="p-6 min-h-screen bg-slate-50">
      <h1 className="text-2xl font-bold mb-6 text-slate-800">Professional List</h1>

      {/* Sort Toggle */}
      <div className="mb-4 flex gap-4">
        <button
          className={`px-4 py-2 rounded-xl font-medium border transition-colors duration-200 ${
            selectedSort === "desc"
              ? "bg-blue-600 text-white border-blue-600"
              : "bg-white text-slate-700 border-slate-300 hover:bg-blue-50"
          }`}
          onClick={() => setSelectedSort("desc")}
        >
          Highest Score
        </button>
        <button
          className={`px-4 py-2 rounded-xl font-medium border transition-colors duration-200 ${
            selectedSort === "asc"
              ? "bg-blue-600 text-white border-blue-600"
              : "bg-white text-slate-700 border-slate-300 hover:bg-blue-50"
          }`}
          onClick={() => setSelectedSort("asc")}
        >
          Lowest Score
        </button>
      </div>

      {/* Pros List */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {sortedPros.map((pro) => (
          <div
            key={pro.id}
            className="bg-white rounded-xl shadow-sm p-4 hover:shadow-md transition-shadow duration-200"
          >
            <h2 className="text-lg font-semibold text-slate-800">
              {pro.name} {pro.lastName}
            </h2>
            <p className="text-slate-500 mt-1">Score: {pro.score} / 100</p>
            <p className="text-slate-500 mt-1">Specialty: {pro.specialty}</p>
          </div>
        ))}
      </div>

      {sortedPros.length === 0 && (
        <p className="text-center text-slate-500 mt-8">No professionals found.</p>
      )}
    </div>
  );
}
