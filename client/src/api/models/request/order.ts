export interface OrderCreateRequestDto {
    products: {
        id: number;
        count: number;
        name: string;
        price: number;
    }[];
    customerInfo: {
        firstName: string;
        secondName: string;
        patronymic: string;
        phoneNumber: string;
    };
    recipientInfo: {
        firstName: string;
        secondName: string;
        patronymic: string;
        phoneNumber: string;
    };
    communicationMethodId: number;
    paymentType: 'ONLINE_PAYMENT' | 'OFFLINE_PAYMENT';
    commentForRecipient: string;
    orderComment: string;
    receivingInfo: {
        type: 'DELIVERY' | 'SELF_CALL';
        dateTimeReceivingInSeconds: number;
        address: {
            country: string;
            state: string;
            city: string;
            street: string;
            houseNumber: string;
            flatNumber: string;
        }
    };
}
