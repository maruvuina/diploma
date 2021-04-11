export interface LoginResponse {
    authenticationToken: string;
    username: string;
    refreshToken: string;
    expiresAt: Date;
    idUser: number;
    roles: Array<string>;
}