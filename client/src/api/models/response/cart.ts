export interface CartResponseDto {
    id: number;
    count: number;
    product: {
        name: string;
        urlName: string;
        mainPhotoUrl: string;
        price: number;
        discount: number;
        available: boolean;
    },
    selected: boolean;
}
