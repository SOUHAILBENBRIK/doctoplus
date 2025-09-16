import 'package:flutter/material.dart';

class AppConstants {
    static const String url = "http://10.0.2.2:8080/api";
    static double getWidth (BuildContext context) {
      return MediaQuery.of(context).size.width;
    }

    static double getHeight (BuildContext context) {
      return MediaQuery.of(context).size.height;
    }

}
