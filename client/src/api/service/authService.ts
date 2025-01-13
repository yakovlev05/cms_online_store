import {
    CheckOtpRequestDto, ConfirmPhoneRequestDto,
    CreateOtpRequestDto,
    LoginRequestDto,
    RegistrationRequestDto, ResetPasswordRequestDto,
    SendOtpRequestDto
} from "@/src/api/models/request/auth";
import {CheckOtpResponseDto, CreateOtpResponseDto, LoginResponseDto} from "@/src/api/models/response/auth";
import {getAccessToken} from "@/src/util/auth";


export default async function login(request: LoginRequestDto): Promise<LoginResponseDto> {
    const response = await fetch(`${process.env.NEXT_PUBLIC_BACKEND_URL}/api/v1/auth/login`, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: JSON.stringify(request)
    })

    if (response.ok) {
        return response.json();
    } else if (response.status === 401) {
        throw new Error("Неверный логин/пароль")
    } else if (response.status === 403) {
        throw new Error('Confirmation required.')
    } else {
        throw new Error(`Ошибка: ${response.status}`);
    }
}

export async function checkAuth(): Promise<boolean> {
    try {
        const response = await fetch(`${process.env.NEXT_PUBLIC_BACKEND_URL}/api/v1/auth/check`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${getAccessToken()}`
            }
        })

        return response.ok;
    } catch {
        return false;
    }
}

export async function registration(request: RegistrationRequestDto) {
    const response = await fetch(`${process.env.NEXT_PUBLIC_BACKEND_URL}/api/v1/auth/registration`, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: JSON.stringify(request)
    })

    if (response.ok) {
        return response.json();
    } else {
        throw new Error(`Ошибка: ${response.status}`);
    }
}

export async function createOtp(data: CreateOtpRequestDto): Promise<CreateOtpResponseDto> {
    const response = await fetch(`${process.env.NEXT_PUBLIC_BACKEND_URL}/api/v1/otp`, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: JSON.stringify(data)
    })

    if (response.ok) {
        return response.json();
    } else {
        throw new Error((await response.json()).message);
    }
}

export async function sendOtp(data: SendOtpRequestDto) {
    const response = await fetch(`${process.env.NEXT_PUBLIC_BACKEND_URL}/api/v1/otp/send`, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: JSON.stringify(data)
    })

    if (response.ok) {
    } else {
        throw new Error((await response.json()).message);
    }
}

export async function checkOtp(data: CheckOtpRequestDto): Promise<CheckOtpResponseDto> {
    const response = await fetch(`${process.env.NEXT_PUBLIC_BACKEND_URL}/api/v1/otp/check`, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: JSON.stringify(data)
    })

    if (response.ok) {
        return response.json();
    } else {
        throw new Error((await response.json()).message);
    }
}

export async function confirmPhone(data: ConfirmPhoneRequestDto) {
    const response = await fetch(`${process.env.NEXT_PUBLIC_BACKEND_URL}/api/v1/auth/confirm-phone`, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: JSON.stringify(data)
    })

    if (response.ok) {
    } else {
        throw new Error((await response.json()).message);
    }
}

export async function resetPassword(data: ResetPasswordRequestDto) {
    const response = await fetch(`${process.env.NEXT_PUBLIC_BACKEND_URL}/api/v1/auth/reset-password`, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: JSON.stringify(data)
    })

    if (response.ok) {
    } else {
        throw new Error((await response.json()).message);
    }
}

