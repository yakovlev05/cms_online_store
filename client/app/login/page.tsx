import Header from "@/src/components/header";
import AuthWindow from "@/src/components/auth-window";
import Footer from "@/src/components/footer";
import styles from '@/src/styles/login.module.css'

export default function Login() {
    return (
        <div className={styles.container}>
            <Header logo='/assets/placeholder/logo.svg'/>
            <AuthWindow/>
            <Footer logo='/assets/placeholder/logo.svg'
                    vk_link='/#'
                    tg_link='/#'
            />
        </div>
    )
}