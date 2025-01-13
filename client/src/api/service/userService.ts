import {UpdateUserRequestDto} from "@/src/api/models/request/user";
import {UserResponseDto} from "@/src/api/models/response/user";
import {getAccessToken} from "@/src/util/auth";


export async function updateUser(userId: string, changed: UpdateUserRequestDto): Promise<UserResponseDto> {
    const response = await fetch(`${process.env.NEXT_PUBLIC_BACKEND_URL}/api/v1/users/${userId}`, {
        method: "PUT",
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Authorization': `Bearer ${getAccessToken()}`
        },
        body: JSON.stringify(changed)
    });

    if (response.ok) {
        return response.json();
    } else {
        throw new Error(`Ошибка обновления пользователя: ${response.status}`);
    }
}

// Получение текущего пользователя
export async function getCurrentUser(userId: string): Promise<UserResponseDto> {
    const response = await fetch(`${process.env.NEXT_PUBLIC_BACKEND_URL}/api/v1/users/${userId}`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${getAccessToken()}`
        }
    });

    if (response.ok) {
        return response.json();
    } else if (response.status === 401) {
        throw new Error("Не авторизован")
    } else {
        throw new Error(`Ошибка получения пользователя: ${response.status}`);
    }
}

// Удаление пользователя
export async function deleteUser(userId: string): Promise<void> {
    const response = await fetch(`${process.env.NEXT_PUBLIC_BACKEND_URL}/api/v1/users/${userId}`, {
        method: 'DELETE',
        headers: {
            'Authorization': `Bearer ${getAccessToken()}`
        }
    });

    if (!response.ok) {
        if (response.status === 404) {
            throw new Error("Пользователь не найден")
        } else {
            throw new Error(`Ошибка удаления пользователя: ${response.status}`);
        }
    }
}

export async function getMyUser(): Promise<UserResponseDto> {
    const response = await fetch(`${process.env.NEXT_PUBLIC_BACKEND_URL}/api/v1/users/me`, {
        method: "GET",
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Authorization': `Bearer ${getAccessToken()}`,
        },
    })

    if (response.ok) {
        return response.json();
    } else {
        throw new Error('Ошибка при получении информации о пользователе: ' + response.status);
    }
}
