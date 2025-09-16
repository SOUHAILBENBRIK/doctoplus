import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:mobile/controller/professionals_provider.dart';
import 'package:mobile/app_routes.dart';
import 'package:mobile/app_consants.dart';

class ProfessionalsScreen extends StatelessWidget {
  const ProfessionalsScreen({super.key});

  @override
  Widget build(BuildContext context) {
    final provider = Provider.of<ProfessionalsProvider>(context, listen: false);

    return Scaffold(
      appBar: AppBar(title: const Text("Professionnels disponibles")),
      body: FutureBuilder(
        future: provider.fetchProfessionals(),
        builder: (context, snapshot) {
          if (provider.loading) {
            return const Center(child: CircularProgressIndicator());
          }

          if (provider.professionals.isEmpty) {
            return const Center(child: Text("Aucun professionnel disponible"));
          }

          return ListView.builder(
            itemCount: provider.professionals.length,
            itemBuilder: (context, index) {
              final pro = provider.professionals[index];

              return Card(
                margin: const EdgeInsets.all(8),
                child: ListTile(
                  onTap: () {
                    Navigator.pushNamed(
                      context,
                      AppRoutes.disponibilities,
                      arguments: {
                        "proId": pro["id"],
                        "proName": pro["name"],
                      },
                    );
                  },
                  leading: const Icon(Icons.person, color: Colors.blueAccent),
                  title: Text(pro["name"] ?? "Sans nom"),
                  subtitle: Text(pro["specialty"] ?? "Spécialité inconnue"),
                  trailing: SizedBox(
                    width: AppConstants.getWidth(context) * 0.5,
                    child: Row(
                      mainAxisAlignment: MainAxisAlignment.end,
                      children: [
                        Spacer(),
                        Text(
                          "score: ${pro["score"]?.toString() ?? "0"}/100",
                          style: const TextStyle(
                              fontWeight: FontWeight.bold, fontSize: 15),
                        ),
                        Spacer(),
                        const Icon(Icons.arrow_forward_ios, size: 16),
                      ],
                    ),
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
