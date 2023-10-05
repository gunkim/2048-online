import {error} from "@sveltejs/kit";
import type {Gamer} from "$lib/types";

export const prerender: boolean = true;

type ParamType = {
    id: string;
}
type FetchType = (info: RequestInfo, init?: RequestInit) => Promise<Response>;

type ApiResponse = {
    endTime: Date,
    gamers: Gamer[]
}

export type LoadResponse = {
    id: string;
    endTime: Date,
    gamers: Gamer[]
}

export const load = async ({params, fetch}: {
    params: ParamType;
    fetch: FetchType;
}): Promise<LoadResponse> => {
    const roomId = params.id;

    const response = await fetch(`/api/rooms/${roomId}/games`);
    if (!response.ok) {
        throw error(404, 'Not found');
    }

    const gameData: ApiResponse = await response.json();

    return {
        id: roomId,
        ...gameData
    }
}
