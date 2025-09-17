import { useNavigate } from "react-router-dom";
import { LogOut, CalendarDays, Clock, Sparkle } from "lucide-react";
import { useAuthStore } from "@/hooks/authStore";

export default function Sidebar() {
  const navigate = useNavigate();
  const { logout } = useAuthStore();

  const handleLogout = () => {
    logout();
    navigate("/login");
  };

  const iconSize = 20; 

  return (
    <aside className="w-56 h-full bg-white/90 backdrop-blur-md border-r border-slate-200/50 shadow-sm flex flex-col p-6">
      
      <div className="flex flex-col space-y-3">
        {/* Availability */}
        <button
          onClick={() => navigate("/dashboard/availability")}
          className="flex items-center gap-3 px-4 py-2 rounded-xl font-medium text-slate-700 
                     hover:bg-blue-50 hover:text-blue-600 hover:shadow-sm transition-all duration-200"
        >
          <CalendarDays size={iconSize} />
          Availability
        </button>

        {/* Appointment */}
        <button
          onClick={() => navigate("/dashboard/appointments")}
          className="flex items-center gap-3 px-4 py-2 rounded-xl font-medium text-slate-700 
                     hover:bg-blue-50 hover:text-blue-600 hover:shadow-sm transition-all duration-200"
        >
          <Clock size={iconSize} />
          Appointment
        </button>

        {/* Ranking */}
        <button
          onClick={() => navigate("/dashboard/ranking")}
          className="flex items-center gap-3 px-4 py-2 rounded-xl font-medium text-slate-700 
                     hover:bg-blue-50 hover:text-blue-600 hover:shadow-sm transition-all duration-200"
        >
          <Sparkle size={iconSize} />
          Ranking
        </button>
      </div>

      {/* Push logout button to bottom */}
      <div className="flex-grow" />

      <button
        onClick={handleLogout}
        className="flex items-center gap-3 px-4 py-2 rounded-xl font-medium text-red-500 
                   hover:bg-red-50 hover:text-red-600 hover:shadow-sm transition-all duration-200"
      >
        <LogOut size={iconSize} />
        Logout
      </button>
    </aside>
  );
}
