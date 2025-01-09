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
