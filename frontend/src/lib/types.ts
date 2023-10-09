//어차피 이 type은 결국 api 응답에 대응되는 타입이기 때문에 api 디렉토리에 두는게 맞을 듯?
//api에서도 Response 타입이 아닌, 해당 타입들이 반환되어야 할 듯 함.

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
};
