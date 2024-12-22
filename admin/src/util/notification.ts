import toast from "react-hot-toast";

export default function errorAlert(message: string): void {
    toast.error(message);
}