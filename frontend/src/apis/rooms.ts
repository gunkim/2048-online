export type Room = {
    id: number;
    title: string;
    maxPlayer: number;
    currentPlayer: number;
}

export const getRooms = async (): Promise<Room[]> => {
    const response = await fetch('/api/rooms');
    return await response.json();
}

export const createRoom = async (title: string): Promise<Response> => {
    return fetch("/api/rooms", {
        method: 'POST',
        headers: {
            "content-type": "application/json"
        },
        body: JSON.stringify({
            title: title
        })
    });
}

export const joinRoom = async (roomId: number): Promise<Response> => {
    return fetch(`/api/rooms/${roomId}/join`, {
        method: 'POST',
        headers: {
            "content-type": "application/json"
        }
    });
}