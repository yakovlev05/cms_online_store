'use client'
import Header from "@/src/components/header";
import Footer from "@/src/components/footer";
import styles from '@/src/styles/checkout.module.css'
import {Toaster} from "react-hot-toast";
import Checkout from "@/src/components/checkout";
import {Suspense} from "react";

export default function CheckoutPage() {
    return (
        <div className={styles.container}>
            <Toaster/>
            <Header logo='/assets/placeholder/logo.svg'/>
            <Suspense>
                <Checkout/>
            </Suspense>
            <Footer logo='/assets/placeholder/logo.svg'
                    vk_link='/#'
                    tg_link='/#'
            />
        </div>
    )
}
