import {CategoryResponseDto, CollectionResponseDto, ProductResponseDto} from "@/src/api/models/response/catalog";

export async function getAllCollections(): Promise<CollectionResponseDto[]> {
    const response = await fetch(`${process.env.NEXT_PUBLIC_BACKEND_URL}/api/v1/collections?page=0&limit=100`, {
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

export async function getAllProducts(
    page: number = 0,
    limit: number = 20,
    directionSort: 'asc' | 'desc' = 'desc',
    keySort: 'price' | 'createdAt' = 'createdAt',
    searchQuery: string = '',
    categoryUrlName: string = '',
): Promise<ProductResponseDto[]> {
    const query = `page=${page}&limit=${limit}&directionSort=${directionSort}&keySort=${keySort}&searchQuery=${searchQuery}&categoryUrlName=${categoryUrlName}`
    const response = await fetch(`${process.env.NEXT_PUBLIC_BACKEND_URL}/api/v1/product?${query}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
        }
    })

    if (response.ok) {
        return response.json();
    } else {
        throw new Error(`Ошибка: ${response.status}`);
    }
}

export async function getAllCategories(
    page: number = 0,
    limit: number = 20,
    directionSort: 'asc' | 'desc' = 'desc',
    keySort: 'createdAt' = 'createdAt',
    searchQuery: string = '',
): Promise<CategoryResponseDto[]> {
    const query = `page=${page}&limit=${limit}&directionSort=${directionSort}&keySort=${keySort}&searchQuery=${searchQuery}`
    const response = await fetch(`${process.env.NEXT_PUBLIC_BACKEND_URL}/api/v1/category?${query}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
        }
    })

    if (response.ok) {
        return response.json();
    } else {
        throw new Error(`Ошибка: ${response.status}`);
    }
}
