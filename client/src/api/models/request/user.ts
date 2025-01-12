export interface UpdateUserRequestDto {
    firstName: string | null;
    lastName: string | null;
    patronymic: string | null;
    phoneNumber: string;
    password: string
}