import Header from "@/src/components/header";
import ResetWindow from "@/src/components/reset-window";
import Footer from "@/src/components/footer";
import styles from '@/src/styles/login.module.css'

export default function Reset() {
    return (
        <div className={styles.container}>
            <Header logo='/assets/placeholder/logo.svg'/>
            <ResetWindow/>
            <Footer logo='/assets/placeholder/logo.svg'
                    vk_link='/#'
                    tg_link='/#'
            />
        </div>
    )
}