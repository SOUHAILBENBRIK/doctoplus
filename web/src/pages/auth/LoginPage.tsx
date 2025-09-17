import { useAuthStore } from "@/hooks/authStore";
import { login } from "@/api/auth";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import {
  Card,
  CardHeader,
  CardTitle,
  CardContent,
  CardFooter,
} from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { Label } from "@/components/ui/label";
import { useState } from "react";
import { type LoginFormValues, loginSchema } from "@/validation";
import { Link, useNavigate } from "react-router-dom";

export default function LoginPage() {
  const setAuth = useAuthStore((s) => s.setAuth);
  const [error, setError] = useState<string | null>(null);
  const navigate = useNavigate();

  const form = useForm<LoginFormValues>({
    resolver: zodResolver(loginSchema),
    defaultValues: {
      email: "",
      password: "",
    },
  });

  const onSubmit = async (values: LoginFormValues) => {
    try {
      setError(null);
      const data = await login(values);

      setAuth({ user: data.user, token: data.token });

      switch (data.user.role) {
        case "PRO":
          navigate("/dashboard");
          break;
        case "PATIENT":
          navigate("/unauthorized");
          break;
        default:
          navigate("/unauthorized");
      }
    } catch (err: unknown) {
      setError("Invalid username or password.");
      console.error(err);
    }
  };

  return (
    <div className="flex w-screen min-h-screen items-center justify-center bg-gradient-to-br from-slate-50 via-blue-50/30 to-purple-50/20">
      {/* Background Elements */}
      <div className="absolute inset-0 pointer-events-none overflow-hidden">
        <div className="absolute top-20 left-10 w-32 h-32 bg-blue-200/30 rounded-full blur-3xl animate-pulse"></div>
        <div className="absolute top-40 right-20 w-40 h-40 bg-purple-200/20 rounded-full blur-3xl animate-pulse delay-1000"></div>
        <div className="absolute bottom-20 left-1/3 w-28 h-28 bg-indigo-200/25 rounded-full blur-3xl animate-pulse delay-2000"></div>
      </div>

      <div className="relative z-10 w-full max-w-md">
        <Card className="shadow-2xl border-0 bg-white/80 backdrop-blur-md rounded-3xl overflow-hidden">
          <CardHeader className="pb-6">
            <CardTitle className="text-center text-2xl font-bold text-slate-800">
              Welcome Back 👋
            </CardTitle>
          </CardHeader>

          <form onSubmit={form.handleSubmit(onSubmit)}>
            <CardContent className="space-y-6 px-8">
              {/* Email */}
              <div className="space-y-3">
                <Label
                  htmlFor="email"
                  className="text-sm font-semibold text-slate-700"
                >
                  Email
                </Label>
                <Input
                  id="email"
                  placeholder="Enter your email"
                  className="h-12 px-4 bg-white border-2 border-slate-200 rounded-xl focus:border-blue-500 focus:ring-2 focus:ring-blue-200 transition-all duration-300 text-slate-700 placeholder:text-slate-400"
                  {...form.register("email")}
                />
                {form.formState.errors.email && (
                  <p className="text-sm text-red-500 flex items-center gap-2">
                    <span className="w-4 h-4">⚠</span>
                    {form.formState.errors.email.message}
                  </p>
                )}
              </div>

              {/* Password */}
              <div className="space-y-3">
                <Label
                  htmlFor="password"
                  className="text-sm font-semibold text-slate-700"
                >
                  Password
                </Label>
                <Input
                  id="password"
                  type="password"
                  placeholder="Enter your password"
                  className="h-12 px-4 bg-white border-2 border-slate-200 rounded-xl focus:border-blue-500 focus:ring-2 focus:ring-blue-200 transition-all duration-300 text-slate-700 placeholder:text-slate-400"
                  {...form.register("password")}
                />
                {form.formState.errors.password && (
                  <p className="text-sm text-red-500 flex items-center gap-2">
                    <span className="w-4 h-4">⚠</span>
                    {form.formState.errors.password.message}
                  </p>
                )}
              

              </div>

              {/* Error Message */}
              {error && (
                <div className="bg-red-50 border border-red-200 rounded-xl p-4">
                  <p className="text-center text-sm text-red-600 flex items-center justify-center gap-2">
                    <span className="w-5 h-5">❌</span>
                    {error}
                  </p>
                </div>
              )}
            </CardContent>

            <CardFooter className="flex flex-col space-y-4 px-8 pb-8 pt-10">
              {/* Login Button */}
              <Button
                type="submit"
                className="w-full h-12 bg-gradient-to-r from-blue-600 to-purple-600 hover:from-blue-700 hover:to-purple-700 text-white text-lg font-semibold rounded-xl shadow-lg hover:shadow-xl transition-all duration-300 transform hover:scale-[1.02] border-0 disabled:opacity-50 disabled:cursor-not-allowed disabled:transform-none"
                disabled={form.formState.isSubmitting}
              >
                {form.formState.isSubmitting ? (
                  <div className="flex items-center gap-2">
                    <div className="w-5 h-5 border-2 border-white/30 border-t-white rounded-full animate-spin"></div>
                    Logging in...
                  </div>
                ) : (
                  <div className="flex items-center gap-2">
                    Login
                    <svg
                      className="w-5 h-5"
                      fill="none"
                      stroke="currentColor"
                      viewBox="0 0 24 24"
                    >
                      <path
                        strokeLinecap="round"
                        strokeLinejoin="round"
                        strokeWidth={2}
                        d="M13 7l5 5m0 0l-5 5m5-5H6"
                      />
                    </svg>
                  </div>
                )}
              </Button>

              {/* Register Link */}
              <div className="text-center p-4 bg-slate-50/80 rounded-xl border border-slate-200/50">
                <p className="text-slate-600">
                  Don’t have an account?{" "}
                  <Link
                    to="/register"
                    className="text-blue-600 hover:text-blue-500 font-semibold hover:underline underline-offset-2 transition-colors duration-300"
                  >
                    Register
                  </Link>
                </p>
              </div>
            </CardFooter>
          </form>
        </Card>
      </div>
    </div>
  );
}
