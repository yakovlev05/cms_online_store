export const validatePhone = (phone: string): boolean => {
    if (phone.length == 0) {
        throw new Error("Введите номер телефона")
    }

    phone.split('').forEach(char => {
        if (!'0123456789'.includes(char)) {
            throw new Error("Введите номер в формате 7XXXXXXXXXX");
        }
    })
    return true;
}
