export interface LoginResponseDto {
    accessToken: string;
    refreshToken: string;
    expiresInMs: bigint;
    refreshExpiresInMs: bigint;
}

export interface CreateOtpResponseDto {
    id: string;
}

export interface CheckOtpResponseDto {
    status: string;
}
