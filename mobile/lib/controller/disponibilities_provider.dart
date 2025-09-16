import 'package:flutter/material.dart';
import 'package:dio/dio.dart';
import 'package:mobile/service/api_service.dart';

class DisponibilitiesProvider extends ChangeNotifier {
  final ApiService apiService;

  DisponibilitiesProvider({required this.apiService});

  List<Map<String, String>> _slots = [];
  List<Map<String, String>> get slots => _slots;

  bool _loading = false;
  bool get loading => _loading;

  /// Fetch available slots (date + time) for a professional
  Future<void> fetchDisponibilities(String proId) async {
    _loading = true;
    notifyListeners();

    try {
      Response response = await apiService.dio.get(
        "/disponibilities/pro/$proId",
      );

      // Expecting API returns list of { "date": "...", "slot": "..." }
      List<Map<String, String>> tempSlots = [];
      for (var item in response.data) {
        if (item['date'] != null && item['slot'] != null) {
          tempSlots.add({"date": item['date'], "slot": item['slot']});
        }
      }

      _slots = tempSlots;
    } catch (e) {
      print("Error fetching disponibilities: $e");
      _slots = [];
    } finally {
      _loading = false;
      notifyListeners();
    }
  }

  /// Book a slot for the professional
  Future<void> bookSlot(String proId, Map<String, String> slotInfo) async {
    try {
      DateTime dateTime = DateTime.parse(
        "${slotInfo['date']}T${slotInfo['slot']}",
      );
      await apiService.dio.post(
        "/rendezvous",
        data: {
          "pro": {"id": proId},
          "dateTime": dateTime.toIso8601String(),
        },
      );

      _slots.remove(slotInfo);
      notifyListeners();
    } catch (e) {
      print("Error booking slot: $e");
      rethrow;
    }
  }
}
