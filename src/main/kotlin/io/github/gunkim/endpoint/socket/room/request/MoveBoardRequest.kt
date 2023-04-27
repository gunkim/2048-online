package io.github.gunkim.endpoint.socket.room.request

import io.github.gunkim.domain.game.MoveType

data class MoveBoardRequest(val direction: MoveType = MoveType.UNKNOWN)
