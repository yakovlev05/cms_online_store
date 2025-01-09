"use client"
import styles from '@/src/styles/header.module.css';
import React, { useState } from 'react';
import Link from 'next/link';
import Image from 'next/image';

interface Props {
    logo: string;
}

const Header: React.FC<Props> = ({ logo }) => {
    const [isDropdownVisible, setDropdownVisible] = useState(false);

    const toggleDropdown = () => {
        setDropdownVisible(!isDropdownVisible);
    };

    return (
        <div className={styles.header}>
            <Link href='/'>
                <Image src={logo} alt='лого' width='45' height='45' />
            </Link>
            <div className={styles.nav}>
                <div
                    className={styles.navItem}
                    onMouseEnter={toggleDropdown}
                    onMouseLeave={toggleDropdown}
                >
                    <span>Каталог</span>
                    {isDropdownVisible && (
                        <div className={styles.dropdown}>
                            <Link href="/catalog/novelties">Новинки</Link>
                            <Link href="/catalog/seasonal">Сезонные</Link>
                            <Link href="/catalog/monobouquets">Монобукеты</Link>
                            <Link href="/catalog/formen">Для мужчин</Link>
                            <Link href="/catalog/forwomen">Для женщин</Link>
                            <Link href="/catalog/classic">Классические</Link>
                        </div>
                    )}
                </div>
                <Link href='/#'>Доставка</Link>
                <Link href='/#'>Контакты</Link>
                <Link href='/#'>О нас</Link>
            </div>
            <div className={styles.icons}>
                <Link href='/'>
                    <Image src='/assets/icon/find.svg' alt='поиск' width='24' height='24' />
                </Link>
                <Link href='/cart'>
                    <Image src='/assets/icon/cart_list.svg' alt='корзина' width='22' height='28' />
                </Link>
                <Link href='/login'>
                    <Image src='/assets/icon/logout.svg' alt='выход' width='28' height='24' />
                </Link>
            </div>
        </div>
    );
};

export default Header;
