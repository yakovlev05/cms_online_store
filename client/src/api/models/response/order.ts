export interface OrderCreateResponseDto {
    orderId: string;
    status: 'PROCESSING' | 'INVALID_VALIDATION' | 'PLACED' | 'PAID' | 'CONFIRMED' | 'COLLECTING' | 'IN_DELIVERY' |
        'CANCELLED' | 'ORDER_IS_WAITING_IN_STORE' | 'COMPLETED';
}

export interface OrderInfoResponseDto {
    orderStatus: 'PROCESSING' | 'INVALID_VALIDATION' | 'PLACED' | 'PAID' | 'CONFIRMED' | 'COLLECTING' | 'IN_DELIVERY' |
        'CANCELLED' | 'ORDER_IS_WAITING_IN_STORE' | 'COMPLETED';
    productsCost: number;
    paymentInfo: {
        finalSum: number;
    };
    createdAt: number[];
}
