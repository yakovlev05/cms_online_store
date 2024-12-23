export interface LoginResponseDto {
    accessToken: string;
    refreshToken: string;
    expiresInMs: bigint;
    refreshExpiresInMs: bigint;
}