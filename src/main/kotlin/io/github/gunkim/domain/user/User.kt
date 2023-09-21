package io.github.gunkim.domain.user

import java.util.UUID

data class User(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val email: String,
    val social: Social = Social.GOOGLE,
    val profileImageUrl: String? = null,
    val role: Role = Role.USER
) {
    init {
        require(name.isNotBlank()) { "이름이 비어있을 수 없습니다." }
        require(EMAIL_REGEX.matches(email)) { "이메일 형식이 올바르지 않습니다." }
    }

    fun update(name: String, profileImageUrl: String) = User(
        id,
        name,
        email,
        social,
        profileImageUrl,
        role
    )

    fun isSameId(id: UUID) = this.id == id

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    companion object {
        private val EMAIL_REGEX =
            Regex("^([\\w\\.\\_\\-])*[a-zA-Z0-9]+([\\w\\.\\_\\-])*([a-zA-Z0-9])+([\\w\\.\\_\\-])+@([a-zA-Z0-9]+\\.)+[a-zA-Z0-9]{2,8}\$")
    }
}
