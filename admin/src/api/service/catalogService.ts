import {ComponentResponseDto} from "../models/response/catalog.ts";

export async function getListComponents(
    page: number = 0,
    limit: number = 10,
    isDescending: boolean = true,
    keySort: string = "createdAt",
    searchQuery: string | null = null,
    showOnlyInStock: boolean = false
): Promise<ComponentResponseDto[]> {

    let params = `page=${page}&limit=${limit}&isDescending=${isDescending}&keySort=${keySort}&showOnlyInStock=${showOnlyInStock}`;

    if (searchQuery !== null) {
        params += `&searchQuery=${searchQuery}`;
    }

    const response = await fetch('/api/v1/components?' + params, {
        method: "GET",
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem("access_token")}`
        },
    });

    if (response.ok) {
        return response.json();
    } else if (response.status === 401) {
        throw new Error('Unauthorized');
    } else {
        throw new Error('Ошибка при GET запросе списка компонентов: ' + response.status);
    }
}
