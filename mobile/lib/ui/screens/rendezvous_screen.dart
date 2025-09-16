import 'package:flutter/material.dart';
import 'package:mobile/controller/auth_controller.dart';
import 'package:mobile/controller/rendezvous_provider.dart';
import 'package:provider/provider.dart';

class RendezVousListScreen extends StatelessWidget {
  const RendezVousListScreen({super.key});

  @override
  Widget build(BuildContext context) {
    final authProvider = Provider.of<AuthProvider>(context, listen: false);
    final rendezVousProvider = Provider.of<RendezVousProvider>(
      context,
      listen: false,
    );

    // Fetch data once using FutureBuilder
    return Scaffold(
      appBar: AppBar(title: const Text("Mes Rendez-vous")),
      body: FutureBuilder(
        future: authProvider.user != null
            ? rendezVousProvider.fetchPatientRendezVous(authProvider.user!.id)
            : Future.value([]),
        builder: (context, snapshot) {
          if (rendezVousProvider.loading) {
            return const Center(child: CircularProgressIndicator());
          }

          if (rendezVousProvider.rendezVousList.isEmpty) {
            return const Center(child: Text("Aucun rendez-vous disponible"));
          }

          return ListView.builder(
            itemCount: rendezVousProvider.rendezVousList.length,
            itemBuilder: (context, index) {
              final rv = rendezVousProvider.rendezVousList[index];
              return Card(
                margin: const EdgeInsets.all(8),
                child: ListTile(
                  title: Text("Avec: ${rv.pro.name}"),
                  subtitle: Text("Date: ${rv.dateTime}"),
                  trailing: IconButton(
                    icon: const Icon(Icons.cancel, color: Colors.red),
                    onPressed: () async {
                      await rendezVousProvider.cancelRendezVous(rv.id);
                      ScaffoldMessenger.of(context).showSnackBar(
                        const SnackBar(content: Text("Rendez-vous annulé ❌")),
                      );
                    },
                  ),
                ),
              );
            },
          );
        },
      ),
    );
  }
}
