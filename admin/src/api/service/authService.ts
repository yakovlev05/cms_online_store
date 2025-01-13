import {LoginRequestDto} from "../models/request/auth.ts";
import {LoginResponseDto} from "../models/response/auth.ts";

export default async function login(request: LoginRequestDto): Promise<LoginResponseDto> {
    const response = await fetch(`${import.meta.env.VITE_API_BASE_URL}/api/v1/auth/login?isClient=false`, {
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