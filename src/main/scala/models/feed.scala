package models

final case class Feed(user: Int, activity: String, store: String, is_public: Boolean, id: String) {}

final case class GenericMessage(code: Int, message: String) {}
