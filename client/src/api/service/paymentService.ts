import {PaymentUrlResponseDto} from "@/src/api/models/response/payment";

export async function getPaymentUrl(orderId: string): Promise<PaymentUrlResponseDto> {
    const response = await fetch(`${process.env.NEXT_PUBLIC_BACKEND_URL}/api/v1/payments/${orderId}`, {
        method: "GET",
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
        },
    })

    if (response.ok) {
        return response.json();
    } else {
        throw new Error('Ошибка при получении url оплаты: ' + response.status);
    }
}
