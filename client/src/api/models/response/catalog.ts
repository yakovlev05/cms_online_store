export interface CollectionResponseDto {
    id: number;
    photo: MediaResponseDto;
    category: CategoryResponseDto;
}

export interface MediaResponseDto {
    name: string;
    url: string;
    fileName: string;
}

export interface CategoryResponseDto {
    id: number;
    name: string;
    urlName: string;
}

export interface ProductResponseDto {
    id: number;
    name: string;
    urlName: string;
    description: string;
    price: number;
    priceDiscount: number;
    components: ComponentResponseDto[];
    categories: CategoryResponseDto[];
    photoUrls: string[];
    mainPhotoUrl: string;
}

export interface ComponentResponseDto {
    name: string;
    count: number;
    price: number;
    inStock: boolean;
}
