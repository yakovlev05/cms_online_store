import Header from "@/src/components/header";
import RegWindow from "@/src/components/reg-window";
import Footer from "@/src/components/footer";
import styles from '@/src/styles/login-reg.module.css'

export default function Reg() {
    return (
        <div className={styles.container}>
            <Header logo='/assets/placeholder/logo.svg'/>
            <RegWindow/>
            <Footer logo='/assets/placeholder/logo.svg'
                    vk_link='/#'
                    tg_link='/#'
            />
        </div>
    )
}