import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:mobile/controller/disponibilities_provider.dart';

class DisponibilitiesScreen extends StatefulWidget {
  final String proId;
  final String proName;

  const DisponibilitiesScreen({
    super.key,
    required this.proId,
    required this.proName,
  });

  @override
  State<DisponibilitiesScreen> createState() => _DisponibilitiesScreenState();
}

class _DisponibilitiesScreenState extends State<DisponibilitiesScreen> {
  @override
  Widget build(BuildContext context) {
    final provider = Provider.of<DisponibilitiesProvider>(
      context,
      listen: false,
    );

    return Scaffold(
      appBar: AppBar(title: Text("Disponibilités de ${widget.proName}")),
      body: FutureBuilder(
        future: provider.fetchDisponibilities(widget.proId),
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(child: CircularProgressIndicator());
          }

          final slots = provider.slots;

          if (slots.isEmpty) {
            return const Center(child: Text("Aucune disponibilité"));
          }

          return ListView.builder(
            itemCount: slots.length,
            itemBuilder: (context, index) {
              final slot = slots[index];
              return Card(
                margin: const EdgeInsets.all(8),
                child: ListTile(
                  title: Text("${slot['date']} - ${slot['slot']}"),
                  trailing: ElevatedButton(
                    child: const Text("Réserver"),
                    onPressed: () async {
                      try {
                        await provider.bookSlot(widget.proId, slot);
                        setState(() {});
                        ScaffoldMessenger.of(context).showSnackBar(
                          const SnackBar(
                            content: Text("Rendezvous réservé avec succès!"),
                          ),
                        );
                      } catch (e) {
                        ScaffoldMessenger.of(
                          context,
                        ).showSnackBar(SnackBar(content: Text("Erreur: $e")));
                      }
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
