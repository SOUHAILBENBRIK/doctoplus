import 'package:flutter/material.dart';
import 'package:mobile/app_routes.dart';
import 'package:mobile/controller/auth_controller.dart';
import 'package:provider/provider.dart';

class HomeScreen extends StatelessWidget {
  const HomeScreen({super.key});

  @override
  Widget build(BuildContext context) {
    final authProvider = Provider.of<AuthProvider>(context);
    final user = authProvider.user;

    return Scaffold(
      appBar: AppBar(
        title: Text("Bienvenue ${user?.name ?? ''} 👋"),
        actions: [
          IconButton(
            icon: const Icon(Icons.logout),
            onPressed: () async {
              await authProvider.logout();
              Navigator.pushReplacementNamed(context, "/login");
            },
          ),
        ],
      ),
      body: ListView(
        children: [
          _buildMenuItem(
            context,
            icon: Icons.people,
            title: "Consulter les professionnels",
            onTap: () {
              Navigator.pushNamed(context, AppRoutes.professionals);
            },
          ),
          _buildMenuItem(
            context,
            icon: Icons.event,
            title: "Consulter mes rendez-vous",
            onTap: () {
              Navigator.pushNamed(context, AppRoutes.rendezvous);
            },
          ),
        ],
      ),
    );
  }

  Widget _buildMenuItem(
    BuildContext context, {
    required IconData icon,
    required String title,
    required VoidCallback onTap,
  }) {
    return Card(
      margin: const EdgeInsets.symmetric(vertical: 8, horizontal: 16),
      child: ListTile(
        leading: Icon(icon, color: Colors.blueAccent),
        title: Text(title),
        trailing: const Icon(Icons.arrow_forward_ios, size: 16),
        onTap: onTap,
      ),
    );
  }
}
