import styles from '@/src/styles/auth-form.module.css'
import Input from '@/src/components/ui/input'
import Button from "@/src/components/ui/button";

const AuthForm = () => {
    return (
        <div className={styles.container}>
            <h1 className={styles.title}>Авторизация</h1>
            <div className={styles.inputContainer}>
                <Input label='Номер телефона:'
                       inputType='tel'
                       inputName='phone'
                       inputAutoComplete='tel'
                />
                <Input label='Пароль:'
                       inputType='password'
                       inputName='password'
                       inputAutoComplete='current-password'
                />
            </div>
            <Button text='Войти'/>
        </div>
    )
}

export default AuthForm;