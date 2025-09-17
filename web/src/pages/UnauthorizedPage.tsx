import { Link } from "react-router-dom";

export default function UnauthorizedPage() {
  return (
    <div className="flex flex-col items-center justify-center h-screen text-center">
      <h1 className="text-4xl font-bold mb-4">403 - Unauthorized</h1>
      <p className="mb-4">You do not have permission to access this page.</p>
      <Link to="/login" className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700">
        Go to Login
      </Link>
    </div>
  );
}
