'use client'

import {useState} from 'react';
import styles from '@/src/styles/auth-form.module.css';
import Button from "@/src/components/ui/button";
import Input from './ui/input';
import AuthOtp from "@/src/components/auth-otp";
import {resetPassword} from "@/src/api/service/authService";
import {toast, Toaster} from "react-hot-toast";
import {useRouter} from "next/navigation";

const PasswordResetForm = () => {
    const router = useRouter();
    const [step, setStep] = useState<'inputNumber' | 'otp' | 'password'>('inputNumber');
    const [phoneNumber, setPhoneNumber] = useState<string>('');
    const [form, setForm] = useState({
        password: '',
        confirmPassword: '',
        otpId: ''
    })

    const handleChange = () => {
        resetPassword({
            otpId: form.otpId,
            phoneNumber: phoneNumber,
            newPassword: form.password,
        })
            .then(() => {
                toast.success('Пароль изменён')
                router.push('/login')
            })
            .catch(err => toast.error(err.message));
    }

    return (
        <div>
            <Toaster/>
            {step === 'inputNumber' && (
                <div className={styles.container}>
                    <h1 className={styles.title}>Сброс пароля</h1>
                    <div className={styles.inputContainer}>
                        <Input label='Номер телефона:'
                               inputType='tel'
                               inputName='phone'
                               inputAutoComplete='tel'
                               onChange={e => setPhoneNumber(e.target.value)}
                        />
                    </div>
                    <Button text='Подтвердить номер' onClick={() => setStep('otp')}/>
                </div>
            )}
            {
                step === 'otp' &&
                <AuthOtp destination={phoneNumber} settings={{
                    onlyOtp: true, funcFinal: (otpId) => {
                        setStep('password')
                        setForm({...form, otpId: otpId})
                    }
                }}/>
            }
            {
                step === 'password' && (
                    <div className={styles.container}>
                        <h1 className={styles.title}>Сброс пароля</h1>
                        <div className={styles.inputContainer}>
                            <Input
                                label='Новый пароль:'
                                inputType='password'
                                inputName='password'
                                inputAutoComplete='new-password'
                                onChange={e => setForm({...form, password: e.target.value})}
                            />
                            <Input
                                label='Повторите пароль:'
                                inputType='password'
                                inputName='confirmPassword'
                                inputAutoComplete='new-password'
                                onChange={e => setForm({...form, confirmPassword: e.target.value})}
                            />
                        </div>
                        <Button text='Сбросить пароль' onClick={handleChange}/>
                    </div>
                )
            }
        </div>
    );
};

export default PasswordResetForm;
