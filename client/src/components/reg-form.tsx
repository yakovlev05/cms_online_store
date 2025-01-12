'use client'
import styles from '@/src/styles/auth-form.module.css';
import Input from '@/src/components/ui/input';
import Button from "@/src/components/ui/button";
import AuthOtp from "@/src/components/auth-otp";
import {useState} from "react";
import {validatePhone} from "@/src/util/phone";
import {toast, Toaster} from "react-hot-toast";
import {registration} from "@/src/api/service/authService";

const RegForm = () => {
    const [form, setForm] = useState({
        password: "",
        confirmPassword: "",
        phoneNumber: ""
    })
    const [enableOtp, setEnableOtp] = useState(false);

    const handleSubmit = () => {
        try {
            validatePhone(form.phoneNumber)
        } catch (error) {
            toast.error((error as Error).message)
            return;
        }

        if (form.password !== form.confirmPassword) {
            toast.error("Пароли не совпадают")
            return;
        }

        if (form.password.length < 7) {
            toast.error('Минимальная длина пароля - 8')
            return;
        }

        registration({password: form.password, phoneNumber: form.phoneNumber})
            .then(() => setEnableOtp(true))
            .catch(err => toast.error(err.message));
    }

    return (
        <div className={styles.container}>
            <Toaster/>
            {
                !enableOtp &&
                <>
                    <h1 className={styles.title}>Регистрация</h1>
                    <div className={styles.inputContainer}>
                        <Input
                            label='Номер телефона:'
                            inputType='tel'
                            inputName='phone'
                            inputAutoComplete='tel'
                            placeholder='7XXXXXXXXXX'
                            onChange={(e) => setForm({...form, phoneNumber: e.target.value})}
                        />
                        <Input
                            label='Пароль:'
                            inputType='password'
                            inputName='password'
                            inputAutoComplete='new-password'
                            placeholder='Минимум 8 символов'
                            onChange={(e) => setForm({...form, password: e.target.value})}
                        />
                        <Input
                            label='Повторите пароль:'
                            inputType='password'
                            inputName='confirmPassword'
                            inputAutoComplete='new-password'
                            placeholder='Введите ещё раз'
                            onChange={(e) => setForm({...form, confirmPassword: e.target.value})}
                        />
                    </div>
                    <Button text='Создать аккаунт' onClick={handleSubmit}/>
                </>
            }
            {enableOtp && <AuthOtp destination={form.phoneNumber}/>}
        </div>
    );
}

export default RegForm;
