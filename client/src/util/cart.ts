import {ProductResponseDto} from "@/src/api/models/response/catalog";
import {CartResponseDto} from "@/src/api/models/response/cart";
import {ChangeCartRequestDto} from "@/src/api/models/request/cart";

export interface LocalCartItem {
    count: number;
    product: {
        id: number;
        name: string;
        urlName: string;
        mainPhotoUrl: string;
        price: number;
        discount: number;
        available: boolean;
    }
    selected: boolean;
}

export function addItemToLocalCart(product?: ProductResponseDto) {
    if (product === undefined) {
        throw Error('Ошибка добавления в локальную корзину: product undefined')
    }

    const cart: LocalCartItem[] = JSON.parse(localStorage.getItem('cart') || '[]');
    console.log('Получили корзину: ' + cart)

    const newItem: LocalCartItem = {
        count: 1,
        product: {
            id: product.id,
            name: product.name,
            urlName: product.urlName,
            mainPhotoUrl: product.mainPhotoUrl,
            price: product.price,
            discount: product.priceDiscount,
            available: true
        },
        selected: true,
    }

    const productIndex = cart.findIndex(item => item.product.id === product.id);

    if (productIndex !== -1) {
        throw new Error('Уже добавлено в корзину')
    } else {
        cart.push(newItem);
        localStorage.setItem('cart', JSON.stringify(cart));
    }
}

export function getLocalCart(): CartResponseDto[] {
    return JSON.parse(localStorage.getItem('cart') || '[]')
        .map((item: LocalCartItem, index: number): CartResponseDto => ({
            id: index,
            count: item.count,
            product: {
                name: item.product.name,
                urlName: item.product.urlName,
                mainPhotoUrl: item.product.mainPhotoUrl,
                price: item.product.price,
                discount: item.product.discount,
                available: item.product.available
            },
            selected: item.selected
        }));
}

export function checkInLocalCart(productUrlName: string) {
    return JSON.parse(localStorage.getItem('cart') || '[]')
        .some((item: LocalCartItem) => item.product.urlName === productUrlName)
}

export function deleteFromLocalCart(productUrlName: string) {
    const cart: LocalCartItem[] = JSON.parse(localStorage.getItem('cart') || '[]');
    const filtered = cart.filter((item: LocalCartItem) => item.product.urlName !== productUrlName)

    localStorage.setItem('cart', JSON.stringify(filtered));
}

export function updateLocalCart(productUrlName: string, changed: ChangeCartRequestDto) {
    console.log(productUrlName)
    console.log(changed)
    const cart: LocalCartItem[] = JSON.parse(localStorage.getItem('cart') || '[]')
    console.log(cart)
    const changedCart = cart.map((item: LocalCartItem) => {
        if (item.product.urlName === productUrlName) {
            return {...item, selected: changed.selected, count: changed.count}
        } else {
            return item
        }
    })

    localStorage.setItem('cart', JSON.stringify(changedCart));
}
