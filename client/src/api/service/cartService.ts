import {AddCartRequestDto} from "@/src/api/models/request/cart";
import {getAccessToken} from "@/src/util/auth";
import {ProductResponseDto} from "@/src/api/models/response/catalog";
import {addItemToLocalCart, checkInLocalCart, deleteFromLocalCart, getLocalCart} from "@/src/util/cart";
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

export async function checkIsInCart(productUrlName: string): Promise<boolean> {
    const response = await fetch(`${process.env.NEXT_PUBLIC_BACKEND_URL}/api/v1/cart/${productUrlName}`, {
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
        return checkInLocalCart(productUrlName);
    } else {
        throw new Error('Ошибка проверки наличия товара в корзине: ' + response.status);
    }
}

export async function deleteFromCart(cartItemId: number, productUrlName: string) {
    const response = await fetch(`${process.env.NEXT_PUBLIC_BACKEND_URL}/api/v1/cart/${cartItemId}`, {
        method: "DELETE",
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Authorization': `Bearer ${getAccessToken()}`,
        }
    })

    if (response.ok) {

    } else if (response.status === 401) {
        deleteFromLocalCart(productUrlName);
    } else {
        throw Error('Ошибка удаления из корзины' + response.status);
    }
}
