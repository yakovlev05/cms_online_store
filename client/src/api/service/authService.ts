import {LoginRequestDto} from "@/src/api/models/request/auth";
import {LoginResponseDto} from "@/src/api/models/response/auth";
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
