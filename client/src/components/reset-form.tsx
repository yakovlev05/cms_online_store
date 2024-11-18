'use client'

import { useState } from 'react';
import styles from '@/src/styles/auth-form.module.css';
import Button from "@/src/components/ui/button";
import Input from './ui/input';

const PasswordResetForm = () => {
    const [step, setStep] = useState(1);

    const handleNextStep = () => {
        setStep(step + 1);
    };

    return (
        <div>
            {step === 1 && (
                <div className={styles.container}>
                    <h1 className={styles.title}>Сброс пароля</h1>
                    <div className={styles.inputContainer}>
                        <Input label='Номер телефона:'
                            inputType='tel'
                            inputName='phone'
                            inputAutoComplete='tel'
                        />
                    </div>
                    <Button text='Подтвердить номер' onClick={handleNextStep}/>
                </div>
            )}
            {step === 2 && (
                <div className={styles.container}>
                    <h1 className={styles.title}>Введите код</h1>
                    <div className={styles.inputContainer}>
                        <Input label='Код:'
                            inputType='text'
                            inputName='code'
                            inputAutoComplete='code'
                        />
                    </div>
                    <Button text='Подтвердить код' onClick={handleNextStep}/>
                </div>
            )}
            {step === 3 && (
                <div className={styles.container}>
                    <h1 className={styles.title}>Сброс пароля</h1>
                    <div className={styles.inputContainer}>
                        <Input 
                            label='Новый пароль:' 
                            inputType='password' 
                            inputName='password' 
                            inputAutoComplete='new-password' 
                        />
                        <Input 
                            label='Повторите пароль:' 
                            inputType='password' 
                            inputName='confirmPassword' 
                            inputAutoComplete='new-password' 
                        />
                    </div>
                    <Button text='Сбросить пароль'/>
                </div>
            )}
            {step === 4 && (
                <div className={styles.container}>
                    <h1 className={styles.title}>Сброс пароля</h1>
                    
                </div>
            )}
        </div>
    );
};

export default PasswordResetForm;
