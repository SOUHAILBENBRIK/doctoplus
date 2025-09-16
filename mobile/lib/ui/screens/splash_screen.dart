import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:mobile/app_consants.dart';
import 'package:mobile/app_routes.dart';
import 'package:mobile/controller/auth_controller.dart';
import 'package:provider/provider.dart';
import 'package:shared_preferences/shared_preferences.dart';

class SplashScreen extends StatefulWidget {
  const SplashScreen({super.key});

  @override
  State<SplashScreen> createState() => _SplashScreenState();
}

class _SplashScreenState extends State<SplashScreen> {
  @override
  void initState() {
    super.initState();
    _checkAuth();
  }

  Future<void> _checkAuth() async {
    final prefs = await SharedPreferences.getInstance();
    final token = prefs.getString("token");
    final userStr = prefs.getString("user");

    await Future.delayed(const Duration(seconds: 1));

    if (token != null && userStr != null) {
      final authProvider = Provider.of<AuthProvider>(context, listen: false);
      await authProvider.loadUserFromPrefs();
      Navigator.pushReplacementNamed(context, AppRoutes.home);
    } else {
      Navigator.pushReplacementNamed(context, AppRoutes.login);
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            const Text(
              'docto+',
              style: TextStyle(
                fontSize: 36,
                fontWeight: FontWeight.bold,
                color: Colors.blueAccent,
                letterSpacing: 2,
              ),
            ),
            SizedBox(height: AppConstants.getHeight(context) * 0.2),
            const CircularProgressIndicator(),
          ],
        ),
      ),
    );
  }
}
