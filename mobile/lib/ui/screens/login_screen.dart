import 'package:flutter/material.dart';
import 'package:mobile/app_consants.dart';
import 'package:mobile/app_routes.dart';
import 'package:mobile/controller/auth_controller.dart';
import 'package:provider/provider.dart';

class LoginScreen extends StatefulWidget {
  const LoginScreen({super.key});

  @override
  State<LoginScreen> createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen> {
  final _emailController = TextEditingController();
  final _passwordController = TextEditingController();
  final _formKey = GlobalKey<FormState>();

  Future<void> _login(AuthProvider auth) async {
    if (_formKey.currentState!.validate()) {
      try {
        await auth.login(
          _emailController.text.trim(),
          _passwordController.text.trim(),
        );
        if (auth.user != null) {
          Navigator.pushReplacementNamed(context, AppRoutes.home);
        }
      } catch (e) {
        ScaffoldMessenger.of(
          context,
        ).showSnackBar(SnackBar(content: Text("Login failed: $e")));
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    final auth = Provider.of<AuthProvider>(context);

    return Scaffold(
      appBar: AppBar(title: const Text("Login")),
      body: Center(
        child: Padding(
          padding: const EdgeInsets.all(16.0),
          child: Form(
            key: _formKey,
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                TextFormField(
                  controller: _emailController,
                  decoration: const InputDecoration(labelText: "Email"),
                  validator: (value) =>
                      value!.isEmpty ? "Please enter your email" : null,
                ),
                SizedBox(height: AppConstants.getHeight(context) * 0.02),
                TextFormField(
                  controller: _passwordController,
                  decoration: const InputDecoration(labelText: "Password"),
                  obscureText: true,
                  validator: (value) =>
                      value!.isEmpty ? "Please enter your password" : null,
                ),
                SizedBox(height: AppConstants.getHeight(context) * 0.02),
                auth.loading
                    ? const CircularProgressIndicator()
                    : ElevatedButton(
                        onPressed: () => _login(auth),
                        child: const Text("Login"),
                      ),
                SizedBox(height: AppConstants.getHeight(context) * 0.02),
                TextButton(
                  onPressed: () {
                    Navigator.pushNamed(
                      context,
                      AppRoutes.register,
                    );
                  },
                  child: const Text("Don’t have an account? Register"),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
