import {CollectionResponseDto} from "@/src/api/models/response/catalog";

export async function getAllCollections(): Promise<CollectionResponseDto[]> {
    const response = await fetch('/api/v1/collections?page=0&limit=100', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        }
    })

    if (response.ok) {
        return response.json();
    } else {
        throw new Error('Ошибка при получении всех коллекций: ' + response.status);
    }
}
