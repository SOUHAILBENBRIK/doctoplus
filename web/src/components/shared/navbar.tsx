import { Button } from "../ui/button";
import { useNavigate } from "react-router-dom";
import { useAuthStore } from "@/hooks/authStore";
import { CircleUserRound } from "lucide-react";

export default function Navbar() {
  const navigate = useNavigate();
  const { user, token } = useAuthStore();
  const isAuthenticated = !!user && !!token;

  return (
    <nav className="sticky top-0 z-50 w-full bg-white/90 backdrop-blur-md border-b border-slate-200/50 shadow-sm">
      <div className="w-full px-2 md:px-5 lg:px-10">
        <div className="flex items-center justify-between h-20">
          {/* Project Name */}
          <div
            className="flex-shrink-0 cursor-pointer"
            onClick={() => navigate("/")}
          >
            <h1 className="text-2xl font-extrabold bg-gradient-to-r from-blue-600 to-purple-600 bg-clip-text text-transparent animate-pulse">
              Docto+
            </h1>
          </div>

          {/* Right Side Actions */}
          <div className="flex items-center space-x-4">
            {!isAuthenticated ? (
              <div className="flex items-center space-x-3">
                <Button
                  onClick={() => navigate("/login")}
                  className="px-6 py-2.5 bg-white text-slate-700 border-2 border-slate-200 rounded-xl font-semibold hover:border-blue-300 hover:text-blue-600 hover:bg-blue-50/50 transition-all duration-300 hover:scale-105 shadow-sm hover:shadow-md"
                >
                  Login
                </Button>
                <Button
                  onClick={() => navigate("/register")}
                  className="px-6 py-2.5 bg-gradient-to-r from-blue-600 to-purple-600 text-white rounded-xl font-semibold hover:from-blue-700 hover:to-purple-700 transition-all duration-300 hover:scale-105 shadow-md hover:shadow-lg border-0"
                >
                  Register
                </Button>
              </div>
            ) : (
              <div className="flex items-center space-x-4">
                <CircleUserRound className="h-8 w-8 text-slate-700" />
                <p className="text-slate-700 font-medium">{user.username}</p>
              </div>
            )}
          </div>
        </div>
      </div>
    </nav>
  );
}
