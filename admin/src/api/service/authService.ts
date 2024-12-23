import {LoginRequestDto} from "../models/request/auth.ts";
import {LoginResponseDto} from "../models/response/auth.ts";

export default async function login(request: LoginRequestDto): Promise<LoginResponseDto> {
    const response = await fetch('/api/v1/auth/login', {
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