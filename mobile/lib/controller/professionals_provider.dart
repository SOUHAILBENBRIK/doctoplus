import 'package:flutter/material.dart';
import 'package:dio/dio.dart';
import 'package:mobile/service/api_service.dart';

class ProfessionalsProvider extends ChangeNotifier {
  final ApiService apiService;

  ProfessionalsProvider({required this.apiService});

  List<dynamic> professionals = [];
  bool loading = false;

  Future<void> fetchProfessionals() async {
    loading = true;
    notifyListeners();
    try {
      Response response = await apiService.dio.get("/users/pros");
      professionals = response.data;
    } catch (e) {
      print("Error fetching professionals: $e");
      professionals = [];
    } finally {
      loading = false;
      notifyListeners();
    }
  }
}
