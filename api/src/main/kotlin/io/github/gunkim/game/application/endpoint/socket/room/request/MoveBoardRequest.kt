package io.github.gunkim.game.application.endpoint.socket.room.request

import io.github.gunkim.game.domain.vo.MoveType

data class MoveBoardRequest(val direction: MoveType = MoveType.UNKNOWN)
