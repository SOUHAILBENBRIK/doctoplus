class User {
  String id;
  String name;
  String email;
  String? specialty;
  int? score;

  User({
    required this.id,
    required this.name,
    required this.email,
    this.specialty,
    this.score,
  });

  factory User.fromJson(Map<String, dynamic> json) {
    return User(
      id: json['id'],
      name: json['name'],
      email: json['email'],
      specialty: json['specialty'] ?? '',
      score: json['score'],
    );
  }

  Map<String, dynamic> toJson() => {
        'id': id,
        'name': name,
        'email': email,
        'specialty': specialty,
        'score': score,
      };
}
