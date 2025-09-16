import 'package:flutter/material.dart';
import 'package:dio/dio.dart';
import 'package:mobile/model/rendezvous.dart';
import 'package:mobile/service/api_service.dart';


class RendezVousProvider extends ChangeNotifier {
  final ApiService apiService;

  RendezVousProvider({required this.apiService});

  List<RendezVous> _rendezVousList = [];
  List<RendezVous> get rendezVousList => _rendezVousList;

  bool _loading = false;
  bool get loading => _loading;

  // Fetch patient rendezvous
  Future<void> fetchPatientRendezVous(String userId) async {
    _loading = true;
    notifyListeners();
    try {
      Response response = await apiService.dio.get("/rendezvous/user/$userId");
      _rendezVousList = (response.data as List)
          .map((json) => RendezVous.fromJson(json))
          .toList();
    } catch (e) {
      print(e);
    } finally {
      _loading = false;
      notifyListeners();
    }
  }

  // Create rendezvous
  Future<void> createRendezVous(String proId, DateTime dateTime) async {
    try {
      Response response = await apiService.dio.post(
        "/rendezvous",
        data: {
          "proId": proId,
          "dateTime": dateTime.toIso8601String(),
        },
      );
      // Add new rendezvous to local list
      _rendezVousList.add(RendezVous.fromJson(response.data));
      notifyListeners();
    } catch (e) {
      print(e);
      rethrow;
    }
  }

  // Cancel rendezvous
  Future<void> cancelRendezVous(String id) async {
    try {
      await apiService.dio.delete("/rendezvous/$id");
      _rendezVousList.removeWhere((rv) => rv.id == id);
      notifyListeners();
    } catch (e) {
      print(e);
      rethrow;
    }
  }

  // Rate professional
  Future<void> ratePro(String rendezVousId, int rating) async {
    try {
      await apiService.dio.post("/rendezvous/$rendezVousId/rate?rating=$rating");
    } catch (e) {
      print(e);
      rethrow;
    }
  }
}
