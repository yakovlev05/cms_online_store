"use client"
import styles from '@/src/styles/header.module.css';
import React, {useEffect, useState} from 'react';
import Link from 'next/link';
import Image from 'next/image';
import {checkAuth} from "@/src/api/service/authService";
import MiniLoader from "@/src/components/ui/mini-loader";
import {getAllCategories} from "@/src/api/service/catalogService";
import {toast, Toaster} from "react-hot-toast";
import {CategoryResponseDto} from "@/src/api/models/response/catalog";

interface Props {
    logo: string;
}

const Header: React.FC<Props> = ({logo}) => {
    const [isDropdownVisible, setDropdownVisible] = useState(false);
    const [categories, setCategories] = useState<CategoryResponseDto[]>([]);
    const [isAuthenticated, setIsAuthenticated] = useState<boolean | undefined>(undefined);


    const toggleDropdown = () => {
        setDropdownVisible(!isDropdownVisible);
    };

    useEffect(() => {
        checkAuth()
            .then((r) => setIsAuthenticated(r))

        getAllCategories(0, 100, 'desc', 'createdAt', '')
            .then((r) => setCategories(r))
            .catch(err => toast.error(err.message));
    }, []);

    return (
        <div className={styles.header}>
            <Toaster/>
            <Link href='/'>
                <Image src={logo} alt='лого' width='45' height='45'/>
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
                            {
                                categories.map((category) => (
                                    <Link href={`/catalog/${category.urlName}`} key={category.id}>{category.name}</Link>
                                ))
                            }
                        </div>
                    )}
                </div>
                {/*<Link href='/#'>Доставка</Link>*/}
                {/*<Link href='/#'>Контакты</Link>*/}
                <Link href='/story'>О нас</Link>
            </div>
            <div className={styles.icons}>
                {/*<Link href='/'>*/}
                {/*    <Image src='/assets/icon/find.svg' alt='поиск' width='24' height='24'/>*/}
                {/*</Link>*/}
                <Link href='/cart'>
                    <Image src='/assets/icon/cart_list.svg' alt='корзина' width='22' height='28'/>
                </Link>

                {
                    isAuthenticated === true &&
                    <Link href='/profile'>
                        <Image src={'/assets/icon/profile.svg'} alt='выход' width='28' height='24'/>
                    </Link>
                }

                {
                    isAuthenticated === false &&
                    <Link href='/login'>
                        <Image src={'/assets/icon/logout.svg'} alt='выход' width='28' height='24'/>
                    </Link>
                }

                {
                    isAuthenticated === undefined && <MiniLoader fontSize={28} color={'#fefae1'}/>
                }
            </div>
        </div>
    );
};

export default Header;
