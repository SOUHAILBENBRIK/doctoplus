import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import ProtectedRoute from "@/components/ProtectedRoute";
import LoginPage from "@/pages/auth/LoginPage";
import RegisterPage from "@/pages/auth/RegisterPage";
import UnauthorizedPage from "./pages/UnauthorizedPage";
import DashboardLayout from "./pages/DashboardLayout";
import DisponibilitiesPage from "./pages/DisponibilitiesPage";
import RendezVousPage from "./pages/RenderVousPage";
import ProsPage from "./pages/ProsPage";



export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />
        <Route path="/unauthorized" element={<UnauthorizedPage />} />
        <Route element={<ProtectedRoute roles={["PRO"]} />}>
          <Route path="/dashboard" element={<DashboardLayout />} >
            <Route index element={<Navigate to="appointments" replace />} />
            <Route path="ranking" element={<ProsPage/>} />
            <Route path="availability" element={<DisponibilitiesPage />} />
            <Route path="appointments" element={<RendezVousPage />} />

          </Route>
        </Route>

        

        

        {/* 🚦 Default redirect */}
        <Route path="*" element={<Navigate to="/" replace />} />
      </Routes>
    </BrowserRouter>
  );
}
