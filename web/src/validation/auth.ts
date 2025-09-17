import { z } from "zod";



export const loginSchema = z.object({
  email: z.email(),
  password: z.string().min(5, { message: "Password must be at least 6 characters long" }),
});

export type LoginFormValues = z.infer<typeof loginSchema>;


export const registerSchema = z.object({
  name: z.string().min(2, { message: "First name must be at least 2 characters long" }),
  email: z.email({ message: "Invalid email address" }),
  password: z.string().min(6, { message: "Password must be at least 6 characters long" }),
  specialty: z.string().min(2, { message: "Specialty must be at least 2 characters long" }),
});

export type RegisterFormValues = z.infer<typeof registerSchema>;

