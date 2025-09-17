import { useAuthStore } from "@/hooks/authStore";
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
import { type RegisterFormValues, registerSchema } from "@/validation";
import { Link, useNavigate } from "react-router-dom";
import { register } from "@/api";

export default function RegisterPage() {
  const setAuth = useAuthStore((s) => s.setAuth);
  const [error, setError] = useState<string | null>(null);
  const navigate = useNavigate();

  const form = useForm<RegisterFormValues>({
    resolver: zodResolver(registerSchema),
    defaultValues: {
      name: "",
      specialty: "",
      email: "",
      password: "",
    },
  });

  const onSubmit = async (values: RegisterFormValues) => {
    try {
      setError(null);

      // Add role = PRO and default score = 100
      const data = await register({ ...values, role: "PRO", score: 100 });

      setAuth({ user: data.user, token: data.token });
      navigate("/dashboard"); 
    } catch (err: unknown) {
      setError("Registration failed. Please check your inputs.");
      console.error(err);
    }
  };

  return (
    <div className="flex w-screen min-h-screen items-center justify-center bg-gradient-to-br from-slate-50 via-blue-50/30 to-purple-50/20 p-4">
      <div className="relative z-10 w-full max-w-lg">
        <Card className="shadow-2xl border-0 bg-white/80 backdrop-blur-md rounded-3xl overflow-hidden">
          <CardHeader className="pb-2">
            <CardTitle className="text-center text-2xl font-bold text-slate-800">
              Create PRO Account
            </CardTitle>
          </CardHeader>

          <form onSubmit={form.handleSubmit(onSubmit)}>
            <CardContent className="space-y-6 px-8 overflow-y-auto">
              {/* Name & Specialty Fields */}
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div className="space-y-3">
                  <Label htmlFor="name">First Name</Label>
                  <Input
                    id="name"
                    placeholder="Enter your first name"
                    {...form.register("name")}
                  />
                  {form.formState.errors.name && (
                    <p className="text-sm text-red-500">
                      {form.formState.errors.name.message}
                    </p>
                  )}
                </div>

                <div className="space-y-3">
                  <Label htmlFor="specialty">Specialty</Label>
                  <Input
                    id="specialty"
                    placeholder="Enter your specialty"
                    {...form.register("specialty")}
                  />
                  {form.formState.errors.specialty && (
                    <p className="text-sm text-red-500">
                      {form.formState.errors.specialty.message}
                    </p>
                  )}
                </div>
              </div>

              {/* Email */}
              <div className="space-y-3">
                <Label htmlFor="email">Email</Label>
                <Input
                  id="email"
                  type="email"
                  placeholder="Enter your email"
                  {...form.register("email")}
                />
                {form.formState.errors.email && (
                  <p className="text-sm text-red-500">
                    {form.formState.errors.email.message}
                  </p>
                )}
              </div>

              {/* Password */}
              <div className="space-y-3">
                <Label htmlFor="password">Password</Label>
                <Input
                  id="password"
                  type="password"
                  placeholder="Enter your password"
                  {...form.register("password")}
                />
                {form.formState.errors.password && (
                  <p className="text-sm text-red-500">
                    {form.formState.errors.password.message}
                  </p>
                )}
              </div>

              {/* Error Message */}
              {error && (
                <div className="bg-red-50 border border-red-200 rounded-xl p-4">
                  <p className="text-center text-sm text-red-600">{error}</p>
                </div>
              )}
            </CardContent>

            <CardFooter className="flex flex-col space-y-4 px-8 pb-8 pt-10">
              <Button
                type="submit"
                className="w-full h-12 bg-gradient-to-r from-purple-600 to-blue-600 text-white text-lg font-semibold rounded-xl shadow-lg"
                disabled={form.formState.isSubmitting}
              >
                {form.formState.isSubmitting ? "Creating Account..." : "Create Account"}
              </Button>

              <div className="text-center p-4 bg-slate-50/80 rounded-xl border border-slate-200/50">
                <p className="text-slate-600">
                  Already have an account?{" "}
                  <Link to="/login" className="text-blue-600 font-semibold hover:underline">
                    Login
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
