export interface LoginRequestDto {
    phoneNumber: string;
    password: string;
}

export interface RegistrationRequestDto {
    phoneNumber: string;
    password: string;
}

export interface CreateOtpRequestDto {
    captchaToken: string;
    channelType: string;
    destination: string;
}

export interface SendOtpRequestDto {
    id: string;
}

export interface CheckOtpRequestDto {
    id: string;
    code: string;
}

export interface ConfirmPhoneRequestDto {
    phoneNumber: string;
    otpId: string;
}
