import styles from '@/src/styles/header.module.css'
import React from "react";
import Link from "next/link";
import Image from "next/image";

interface Props {
    logo: string
}

const Header: React.FC<Props> = ({logo}) => {
    return (
        <div className={styles.header}>
            <Link href='/'>
                <Image src={logo} alt='лого' width='77' height='77'/>
            </Link>
            <div className={styles.nav}>
                <Link href='/#'>Каталог</Link>
                <Link href='/#'>Доставка</Link>
                <Link href='/#'>Контакты</Link>
                <Link href='/#'>О нас</Link>
            </div>
            <div className={styles.icons}>
                <Link href='/'>
                    <Image src='/assets/icon/find.svg' alt='поиск' width='24' height='24'/>
                </Link>
                <Link href='/'>
                    <Image src='/assets/icon/cart_list.svg' alt='корзина' width='22' height='28'/>
                </Link>
                <Link href='/'>
                    <Image src='/assets/icon/logout.svg' alt='выход' width='28' height='24'/>
                </Link>
            </div>
        </div>
    )
}

export default Header;