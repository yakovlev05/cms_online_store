import styles from '@/src/styles/auth-window.module.css';
import ResetForm from "@/src/components/reset-form";
import Image from "next/image";
import NavLink from "@/src/components/nav-link";

const ResetWindow = () => {
    return (
        <div className={styles.window}>
            <div className={styles.formContainer}>
                <ResetForm/>
                <div className={styles.questions}>
                    <p>Вернуться ко входу?<br/>
                        <NavLink text='Войти' url='/login'/>
                    </p>
                </div>
            </div>
            <div className={styles.img}>
                <Image src='/assets/placeholder/login_photo.jpg' alt='фото цветов' width='480' height='640'/>
            </div>
        </div>
    )
}

export default ResetWindow;