import styles from '@/src/styles/auth-window.module.css';
import RegForm from "@/src/components/reg-form";
import Image from "next/image";
import NavLink from "@/src/components/nav-link";

const RegWindow = () => {
    return (
        <div className={styles.window}>
            <div className={styles.formContainer}>
                <RegForm/>
                <div className={styles.questions}>
                    <p>У вас уже есть аккаунт?<br/>
                        <NavLink text='Войти' url='/login'/>
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

export default RegWindow;