import styles from '@/src/styles/auth-window.module.css';
import AuthForm from "@/src/components/auth-form";
import Image from "next/image";
import NavLink from "@/src/components/nav-link";

const AuthWindow = () => {
    return (
        <div className={styles.window}>
            <div className={styles.formContainer}>
                <AuthForm/>
                <div className={styles.questions}>
                    <p>У вас нет аккаунта?<br/>
                        Пройдите <NavLink text='регистрацию' url='/#'/>
                    </p>
                    <NavLink text='Забыли пароль?' url='/#'/>
                </div>
            </div>
            <div className={styles.img}>
                <Image src='/assets/placeholder/login_photo.jpg' alt='фото цветов' width='480' height='640'/>
            </div>
        </div>
    )
}

export default AuthWindow;