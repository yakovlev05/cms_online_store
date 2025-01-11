'use client';

import CartItems from '@/src/components/cart-items';
import DeliveryOptions from '@/src/components/delivery-options';
import CustomerDetails from '@/src/components/customer-details';
import SummaryBox from '@/src/components/summary-box';
import Header from '@/src/components/header';
import Footer from '@/src/components/footer';
import styles from '@/src/styles/cart.module.css';
import {useEffect, useState} from "react";
import {CartResponseDto} from "@/src/api/models/response/cart";
import {getCart} from "@/src/api/service/cartService";
import {toast, Toaster} from "react-hot-toast";

export default function CartPage() {
    const [cart, setCart] = useState<CartResponseDto[] | undefined>(undefined);

    useEffect(() => {
        getCart()
            .then(r => setCart(r))
            .catch(err => toast.error(err.message));
    }, []);

    return (
        <div className={styles.home}>
            <Toaster/>
            <Header logo="/assets/placeholder/logo.svg"/>
            <h1 className={styles.title}>Корзина</h1>
            <div className={styles.homeContainer}>
                {/* Левая часть - товары и данные */}
                <div className={styles.homeLeftContainer}>
                    <CartItems cart={cart}/>
                    <DeliveryOptions/>
                    <CustomerDetails/>
                </div>

                {/* Правая часть - итоговое окно */}
                <div>
                    <SummaryBox cart={cart}/>
                </div>
            </div>
            <Footer
                logo="/assets/placeholder/logo.svg"
                vk_link="/#"
                tg_link="/#"
            />
        </div>
    );
}
