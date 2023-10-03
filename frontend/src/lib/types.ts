export type Player = {
    id: string;
    name: string;
    profileImageUrl: string;
    host: boolean;
    ready: boolean;
}

export type Gamer = {
    userId: string;
    name: string;
    score: number;
    board: number[][];
};

export type GamerProfile = {
    id: string;
    name: string;
    profileImageUrl: string;
    score: number;
}