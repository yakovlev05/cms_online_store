import {AddCartRequestDto} from "@/src/api/models/request/cart";
import {getAccessToken} from "@/src/util/auth";
import {ProductResponseDto} from "@/src/api/models/response/catalog";
import {addItemToLocalCart, getLocalCart} from "@/src/util/cart";
import {CartResponseDto} from "@/src/api/models/response/cart";

export async function addCart(data: AddCartRequestDto, product?: ProductResponseDto) {
    const response = await fetch(`${process.env.NEXT_PUBLIC_BACKEND_URL}/api/v1/cart`, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Authorization': `Bearer ${getAccessToken()}`,
        },
        body: JSON.stringify(data),
    })

    if (response.ok) {
        return response.json();
    } else if (response.status === 401) {
        addItemToLocalCart(product);
    } else {
        throw new Error('Ошибка при добавлении товара в корзину: ' + response.status);
    }
}

export async function getCart(): Promise<CartResponseDto[]> {
    const response = await fetch(`${process.env.NEXT_PUBLIC_BACKEND_URL}/api/v1/cart`, {
        method: "GET",
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Authorization': `Bearer ${getAccessToken()}`,
        }
    })

    if (response.ok) {
        return response.json();
    } else if (response.status === 401) {
        return getLocalCart();
    } else {
        throw Error('Ошибка при получении списка корзины: ' + response.status);
    }
}
