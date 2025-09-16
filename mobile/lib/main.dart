import 'package:flutter/material.dart';
import 'package:mobile/app_routes.dart';
import 'package:mobile/controller/auth_controller.dart';
import 'package:mobile/controller/disponibilities_provider.dart';
import 'package:mobile/controller/professionals_provider.dart';
import 'package:mobile/controller/rendezvous_provider.dart';
import 'package:mobile/service/api_service.dart';
import 'package:mobile/ui/screens/splash_screen.dart';
import 'package:provider/provider.dart';

void main() {
    WidgetsFlutterBinding.ensureInitialized();
  final apiService = ApiService();
  runApp(
    MultiProvider(
      providers: [
        ChangeNotifierProvider(create: (_) => AuthProvider(apiService: apiService)),
        ChangeNotifierProvider(create: (_) => RendezVousProvider(apiService: apiService)),
        ChangeNotifierProvider(create: (_) => DisponibilitiesProvider(apiService: apiService)),
        ChangeNotifierProvider(create: (_) => ProfessionalsProvider(apiService: apiService)),
      ],
      child: MyApp(),
    ),
  );
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.blueAccent),
      ),
      initialRoute: AppRoutes.splash,
      routes: AppRoutes.getRoutes(),
    );
  }
}
