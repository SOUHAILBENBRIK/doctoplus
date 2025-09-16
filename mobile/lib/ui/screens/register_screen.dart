import 'package:flutter/material.dart';
import 'package:mobile/app_consants.dart';
import 'package:mobile/app_routes.dart';
import 'package:mobile/controller/auth_controller.dart';
import 'package:provider/provider.dart';

class RegisterScreen extends StatefulWidget {
  const RegisterScreen({super.key});

  @override
  State<RegisterScreen> createState() => _RegisterScreenState();
}

class _RegisterScreenState extends State<RegisterScreen> {
  final _formKey = GlobalKey<FormState>();
  final _nameController = TextEditingController();
  final _emailController = TextEditingController();
  final _passwordController = TextEditingController();

  Future<void> _register(AuthProvider auth) async {
    if (_formKey.currentState!.validate()) {
      try {
        await auth.register(
          _nameController.text.trim(),
          _emailController.text.trim(),
          _passwordController.text.trim(),
        );

        if (auth.user != null) {
          Navigator.pushReplacementNamed(
            context,
            AppRoutes.home,
          );
        }
      } catch (e) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text("Registration failed: $e")),
        );
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    final auth = Provider.of<AuthProvider>(context);

    return Scaffold(
      appBar: AppBar(title: const Text("Register")),
      body: Center(
        child: Padding(
          padding: const EdgeInsets.all(16.0),
          child: Form(
            key: _formKey,
            child: SingleChildScrollView(
              child: Column(
                children: [
                  TextFormField(
                    controller: _nameController,
                    decoration: const InputDecoration(labelText: "Name"),
                    validator: (value) =>
                        value!.isEmpty ? "Please enter your name" : null,
                  ),
                  SizedBox(height: AppConstants.getHeight(context) * 0.02),
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
                        value!.length < 6 ? "Password must be at least 6 characters" : null,
                  ),
                  SizedBox(height: AppConstants.getHeight(context) * 0.02),
                  auth.loading
                      ? const CircularProgressIndicator()
                      : ElevatedButton(
                          onPressed: () => _register(auth),
                          child: const Text("Register"),
                        ),
                  SizedBox(height: AppConstants.getHeight(context) * 0.02),
                  TextButton(
                    onPressed: () => Navigator.pop(context),
                    child: const Text("Already have an account? Login"),
                  )
                ],
              ),
            ),
          ),
        ),
      ),
    );
  }
}
