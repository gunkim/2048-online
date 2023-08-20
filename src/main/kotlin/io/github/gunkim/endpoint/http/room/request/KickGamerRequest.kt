package io.github.gunkim.endpoint.http.room.request

import java.util.UUID

data class KickGamerRequest(val gamerId: UUID = UUID.randomUUID())
