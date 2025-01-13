export interface UpdateUserRequestDto {
    fistName: string | null;
    lastName: string | null;
    patronymic: string | null;
    phoneNumber: string;
    password: string | null;
}
