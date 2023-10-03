import {error} from "@sveltejs/kit";
import type {Player} from "$lib/types";

export const prerender: boolean = true;

type Param = {
    id: string;
}
type Fetch = (info: RequestInfo, init?: RequestInit) => Promise<Response>;

export type LoadResponse = {
    id: string;
    title: string;
    players: Player[];
}

export const load = async ({params, fetch}: {
    params: Param;
    fetch: Fetch;
}): Promise<LoadResponse> => {
    const roomId: string = params.id;

    const response: Response = await fetch(`/api/rooms/${roomId}/wait`);
    if (!response.ok) {
        throw error(404, 'Not found');
    }

    return await response.json();
}
