import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:mobile/model/rendezvous.dart';
import 'package:mobile/model/user.dart';
import 'package:mobile/service/api_service.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:dio/dio.dart';

class AuthProvider extends ChangeNotifier {
  final ApiService apiService;

  AuthProvider({required this.apiService});

  User? _user;
  User? get user => _user;
  bool _loading = false;
  bool get loading => _loading;

  Future<void> register(String name, String email, String password) async {
    _loading = true;
    notifyListeners();
    try {
      Response response = await apiService.dio.post(
        "/auth/register",
        data: {"name": name, "email": email, "password": password , "role": "PATIENT"},
      );
      await _saveTokenAndUser(response.data);
    } catch (e) {
      print(e);
      rethrow;
    } finally {
      _loading = false;
      notifyListeners();
    }
  }

  Future<void> login(String email, String password) async {
    _loading = true;
    notifyListeners();
    try {
      Response response = await apiService.dio.post(
        "/auth/login",
        data: {"email": email, "password": password},
      );
      print(response.data);
      await _saveTokenAndUser(response.data);
    } catch (e) {
      print(e);
      rethrow;
    } finally {
      _loading = false;
      notifyListeners();
    }
  }

  Future<void> logout() async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.clear();
    _user = null;
    apiService.setToken("");
    notifyListeners();
  }

  Future<void> _saveTokenAndUser(Map<String, dynamic> data) async {
    final prefs = await SharedPreferences.getInstance();
    String token = data['token'];
    Map<String, dynamic> userJson = data['user'];

    await prefs.setString("token", token);
    await prefs.setString(
      "user",
      jsonEncode(userJson),
    );

    apiService.setToken(token);
    _user = User.fromJson(userJson);
    notifyListeners();
  }

  Future<void> loadUserFromPrefs() async {
    final prefs = await SharedPreferences.getInstance();
    String? token = prefs.getString("token");
    String? userStr = prefs.getString("user");

    if (token != null && userStr != null) {
      apiService.setToken(token);
      _user = User.fromJson(
        Map<String, dynamic>.from(await jsonDecode(userStr)),
      );
      notifyListeners();
    }
  }
}
