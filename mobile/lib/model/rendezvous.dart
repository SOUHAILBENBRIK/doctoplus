import 'package:mobile/model/user.dart';

class RendezVous {
  String id;
  String userId;
  User pro;
  DateTime dateTime;
  String status;

  RendezVous({
    required this.id,
    required this.userId,
    required this.pro,
    required this.dateTime,
    required this.status,
  });

  factory RendezVous.fromJson(Map<String, dynamic> json) {
    return RendezVous(
      id: json['id'],
      userId: json['userId'],
      pro: User.fromJson(json['pro']),
      dateTime: DateTime.parse(json['dateTime']),
      status: json['status'],
    );
  }
}


