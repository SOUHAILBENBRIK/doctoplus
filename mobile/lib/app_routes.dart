import 'package:flutter/material.dart';
import 'package:mobile/ui/screens/Disponibilities_screen.dart';
import 'package:mobile/ui/screens/home_screen.dart';
import 'package:mobile/ui/screens/login_screen.dart';
import 'package:mobile/ui/screens/professionals_screen.dart';
import 'package:mobile/ui/screens/register_screen.dart';
import 'package:mobile/ui/screens/rendezvous_screen.dart';
import 'package:mobile/ui/screens/splash_screen.dart';

class AppRoutes {
  static const String splash = '/splash';
  static const String login = '/login';
  static const String register = '/register';
  static const String home = '/home';
  static const String rendezvous = '/rendezvous';
  static const String professionals = '/professionals';
  static const String disponibilities = '/disponibilities';


  static Map<String, WidgetBuilder> getRoutes() {
    return {
      splash: (context) => SplashScreen(),
      login: (context) => LoginScreen(),
      register: (context) => RegisterScreen(),
      home: (context) => HomeScreen(),
      rendezvous: (context) => RendezVousListScreen(),
      professionals: (context) => ProfessionalsScreen(),
      disponibilities: (context) => DisponibilitiesScreen(
            proId: ModalRoute.of(context)!.settings.arguments != null
                ? (ModalRoute.of(context)!.settings.arguments
                    as Map<String, dynamic>)['proId']
                : '',
            proName: ModalRoute.of(context)!.settings.arguments != null
                ? (ModalRoute.of(context)!.settings.arguments
                    as Map<String, dynamic>)['proName']
                : '',
          ),
    };
  } 

}