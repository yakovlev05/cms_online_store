'use client'
import styles from '@/src/styles/auth-form.module.css'
import Input from '@/src/components/ui/input'
import Button from "@/src/components/ui/button";
import React, {useEffect, useState} from "react";
import {LoginRequestDto} from "@/src/api/models/request/auth";
import login, {checkAuth} from "@/src/api/service/authService";
import {saveAccessToken, saveRefreshToken} from "@/src/util/auth";
import {useRouter} from "next/navigation";
import {toast, Toaster} from "react-hot-toast";
import {validatePhone} from "@/src/util/phone";

const AuthForm = () => {
    const [form, setForm] = useState<LoginRequestDto>({password: "", phoneNumber: ""})
    const router = useRouter()

    const onSubmit = () => {
        try {
            validatePhone(form.phoneNumber)
        } catch (error) {
            toast.error((error as Error).message)
            return;
        }

        const response = login(form)

        response
            .then((r) => {
                saveAccessToken(r.accessToken)
                saveRefreshToken(r.refreshToken)
                router.push('/')
            })
            .catch((err) => {
                toast.error(err.message)
            });
    }

    useEffect(() => {
        checkAuth()
            .then((r) => {
                if (r) {
                    router.replace('/profile')
                }
            })
            .catch((err) => toast(err.message))
    }, [router]);

    return (
        <div className={styles.container}>
            <Toaster/>
            <h1 className={styles.title}>Авторизация</h1>
            <div className={styles.inputContainer}>
                <Input label='Номер телефона:'
                       inputType='tel'
                       inputName='phone'
                       inputAutoComplete='tel'
                       placeholder='7XXXXXXXXXX'
                       onChange={e => setForm({...form, phoneNumber: e.currentTarget.value})}
                />
                <Input label='Пароль:'
                       inputType='password'
                       inputName='password'
                       inputAutoComplete='current-password'
                       onChange={e => setForm({...form, password: e.target.value})}
                />
            </div>
            <Button text='Войти' onClick={onSubmit}/>
        </div>
    )
}

export default AuthForm;