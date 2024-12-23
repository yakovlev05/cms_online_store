import toast from "react-hot-toast";

export function errorAlert(message: string): void {
    toast.error(message);
}

export function successAlert(message: string): void {
    toast.success(message);
}