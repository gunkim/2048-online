import {error} from "@sveltejs/kit";

export const prerender = true;

type ParamType = {
    id: string;
}
type FetchType = (info: RequestInfo, init?: RequestInit) => Promise<Response>;

export const load = async ({params, fetch}: {
    params: ParamType;
    fetch: FetchType;
}) => {
    const roomId = params.id;

    const response = await fetch(`/api/rooms/${roomId}/wait`);
    if (response.ok) {
        return await response.json();
    }

    throw error(404, 'Not found');
}