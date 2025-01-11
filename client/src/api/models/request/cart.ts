export interface AddCartRequestDto {
    productUrlName: string;
}

export interface ChangeCartRequestDto {
    count: number;
    selected: boolean;
}
