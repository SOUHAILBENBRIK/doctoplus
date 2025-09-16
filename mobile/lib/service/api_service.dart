import 'package:dio/dio.dart';
import 'package:mobile/app_consants.dart';
import 'package:shared_preferences/shared_preferences.dart';

class ApiService {
  final Dio dio = Dio(BaseOptions(
    baseUrl: AppConstants.url,
    headers: {"Content-Type": "application/json"},
  ));

  ApiService() {
    // Add interceptor to automatically attach token to requests
    dio.interceptors.add(
      InterceptorsWrapper(
        onRequest: (options, handler) async {
          final token = await getToken();
          if (token != null && token.isNotEmpty) {
            options.headers["Authorization"] = "Bearer $token";
          }
          return handler.next(options); // continue
        },
        onError: (DioException e, handler) {
          // You can handle 401/403 globally here if needed
          return handler.next(e);
        },
      ),
    );
  }

  void setToken(String token) {
    dio.options.headers["Authorization"] = "Bearer $token";
  }

  Future<String?> getToken() async {
    final prefs = await SharedPreferences.getInstance();
    return prefs.getString("token");
  }
}
