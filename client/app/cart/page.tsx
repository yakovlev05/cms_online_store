'use client';

import CartItems from '@/src/components/cart-items';
import DeliveryOptions from '@/src/components/delivery-options';
import CustomerDetails from '@/src/components/customer-details';
import SummaryBox from '@/src/components/summary-box';
import Header from '@/src/components/header';
import Footer from '@/src/components/footer';
import styles from '@/src/styles/cart.module.css';

export default function CartPage() {
    return (
        <div className={styles.home}>
            <Header logo="/assets/placeholder/logo.svg" />
            <h1 className={styles.title}>Корзина</h1>
            <div className={styles.homeContainer}>
                {/* Левая часть - товары и данные */}
                <div className="lg:col-span-2 space-y-8">
                    <CartItems />
                    <DeliveryOptions />
                    <CustomerDetails />
                </div>

                {/* Правая часть - итоговое окно */}
                <div>
                    <SummaryBox />
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
