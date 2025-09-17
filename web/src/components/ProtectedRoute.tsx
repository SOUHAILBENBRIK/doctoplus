import { Navigate, Outlet } from "react-router-dom";
import { useAuthStore } from "@/hooks/authStore";

interface ProtectedRouteProps {
  roles?: string[]; // allowed roles
}

export default function ProtectedRoute({ roles }: ProtectedRouteProps) {
  const { user, token } = useAuthStore();

  if (!token) return <Navigate to="/login" />;
  if (roles && user && !roles.includes(user.role)) {
    return <Navigate to="/unauthorized" />;
  }
  return <Outlet />;
}
