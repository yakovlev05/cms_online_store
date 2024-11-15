import styles from '@/src/styles/footer.module.css';
import React from "react";
import Image from "next/image";
import Link from "next/link";

interface Props {
    logo: string;
    vk_link: string;
    tg_link: string;
}

const Footer: React.FC<Props> = ({logo, vk_link, tg_link}) => {
    return (
        <div className={styles.footer}>
            <div className={styles.infoContainer}>
                <Link href='/'>
                    <Image src={logo} alt='логотип' width='77' height='77'/>
                </Link>
                <div className={styles.urls}>
                    <Link href='/#'>Адрес</Link>
                    <Link href='/#'>Доставка</Link>
                    <Link href='/#'>Контакты</Link>
                    <Link href='/#'>О нас</Link>
                </div>
            </div>
            <div className={styles.networks}>
                <Link href={vk_link}>
                    <Image src='/assets/icon/vk_logo.svg' alt='лого вк' width='28' height='28'/>
                </Link>
                <Link href={tg_link}>
                    <Image src='/assets/icon/tg_logo.svg' alt='лого тг' width='28' height='25'/>
                </Link>
            </div>
        </div>
    )
}

export default Footer;