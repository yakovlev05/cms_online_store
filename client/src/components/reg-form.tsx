import styles from '@/src/styles/auth-form.module.css';
import Input from '@/src/components/ui/input';
import Button from "@/src/components/ui/button";

const RegForm = () => {
    return (
        <div className={styles.container}>
            <h1 className={styles.title}>Регистрация</h1>
            <div className={styles.inputContainer}>
                <Input 
                    label='Номер телефона:' 
                    inputType='tel' 
                    inputName='phone' 
                    inputAutoComplete='tel' 
                />
                <Input 
                    label='Пароль:' 
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
            <Button text='Создать аккаунт' />
        </div>
    );
}

export default RegForm;
