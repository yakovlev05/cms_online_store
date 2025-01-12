import {OrderCreateRequestDto} from "@/src/api/models/request/order";
import {OrderCreateResponseDto} from "@/src/api/models/response/order";

export async function createOrder(data: OrderCreateRequestDto): Promise<OrderCreateResponseDto> {
    const response = await fetch(`${process.env.NEXT_PUBLIC_BACKEND_URL}/api/v1/orders`, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
        },
        body: JSON.stringify(data),
    })

    if (response.ok) {
        return response.json();
    } else {
        throw new Error('Ошибка при создании заказа: ' + response.status);
    }
}
