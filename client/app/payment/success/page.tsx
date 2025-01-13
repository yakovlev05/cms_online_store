import Header from "@/src/components/header";
import Footer from "@/src/components/footer";
import styles from "@/src/styles/success.module.css";
import Image from "next/image";
import Link from "next/link";

export default function SuccessPage() {
    return (
        <div className={styles.container}>
            <Header logo='/assets/placeholder/logo.svg'/>
            <div className={styles.info}>
                <Image src='/assets/icon/success.svg' alt='success' width='150' height='150'/>
                <h1 className={styles.title}>Успешная оплата!</h1>
                <Link href='/' className={styles.url}>Перейти к покупкам</Link>
            </div>
            <Footer logo='/assets/placeholder/logo.svg'
                    vk_link='/#'
                    tg_link='/#'
            />
        </div>
    )
}
