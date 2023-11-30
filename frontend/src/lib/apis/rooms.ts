export type Room = {
    id: number;
    title: string;
    maxPlayer: number;
    currentPlayer: number;
}

export type Timer = {
    time: number;
}

export const getRooms = async (): Promise<Room[]> => {
    const response = await fetch('/api/rooms');
    return await response.json();
}

export const createRoom = async (title: string, timer: number): Promise<Response> => {
    return fetch("/api/rooms", {
        method: 'POST',
        headers: {
            "content-type": "application/json"
        },
        body: JSON.stringify({
            title: title,
            timer: timer
        })
    });
}

export const joinRoom = async (roomId: string): Promise<Response> => {
    return fetch(`/api/rooms/${roomId}/join`, {
        method: 'POST',
        headers: {
            "content-type": "application/json"
        }
    });
}

export const readyRoom = async (roomId: number): Promise<Response> => {
    return fetch(`/api/rooms/${roomId}/ready`, {
        method: 'PUT',
        headers: {
            "content-type": "application/json"
        }
    });
}

export const leaveRoom = async (roomId: number): Promise<Response> => {
    return await fetch(`/api/rooms/${roomId}/leave`, {
        method: 'DELETE',
        headers: {
            "content-type": "application/json"
        }
    });
}

export const startRoom = async (roomId: number): Promise<Response> => {
    return await fetch(`/api/rooms/${roomId}/start`, {
        method: 'PUT',
        headers: {
            "content-type": "application/json"
        }
    });
}

export const getTimer = async (): Promise<Response> => await fetch('/api/game/timer', {
    method: 'GET',
    headers: {
        "content-type": "application/json"
    }
})
