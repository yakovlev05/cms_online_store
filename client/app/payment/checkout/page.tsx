'use client'
import {useRouter, useSearchParams} from "next/navigation";
import {useEffect, useState} from "react";
import Header from "@/src/components/header";
import Footer from "@/src/components/footer";
import styles from '@/src/styles/checkout.module.css'
import MiniLoader from "@/src/components/ui/mini-loader";
import Button from "@/src/components/ui/button"
import {getOrderInfo} from "@/src/api/service/orderService";
import {OrderInfoResponseDto} from "@/src/api/models/response/order";
import {toast, Toaster} from "react-hot-toast";
import {PaymentUrlResponseDto} from "@/src/api/models/response/payment";
import {getPaymentUrl} from "@/src/api/service/paymentService";

export default function CheckoutPage() {
    const params = useSearchParams()
    const router = useRouter();
    const [order, setOrder] = useState<OrderInfoResponseDto | undefined>(undefined);
    const [payment, setPayment] = useState<PaymentUrlResponseDto | undefined>(undefined);

    useEffect(() => {
        if (!params.get('order-id')) {
            router.replace('/')
        }

        const orderId = params.get('order-id') || ''
        let countOrderRequests = 0;
        const orderIntervalId = setInterval(() => {
            countOrderRequests++;

            getOrderInfo(orderId)
                .then(order => {
                    if (countOrderRequests === 10) {
                        clearInterval(orderIntervalId);
                        setOrder(order);
                        return;
                    }

                    if (order.orderStatus === 'PROCESSING') {
                        return
                    } else if (order.orderStatus === 'PLACED') {

                        getPaymentUrl(orderId)
                            .then(payment => {
                                setPayment(payment);
                                setOrder(order);
                            })
                            .catch((err) => toast.error(err.message));
                        clearInterval(orderIntervalId);
                        return;
                    } else {
                        setOrder(order);
                        return;
                    }
                })
                .catch(err => toast.error(err.message))

        }, 1000)

    }, [params, router]);

    return (
        <div className={styles.container}>
            <Toaster/>
            <Header logo='/assets/placeholder/logo.svg'/>
            <div className={styles.loaderContainer}>
                {
                    !order &&
                    <>
                        <h1 className={styles.title}>Собираем заказ...</h1>
                        <MiniLoader color='#798648' fontSize={70}/>
                    </>
                }
                {
                    order && order.orderStatus === 'PLACED' &&
                    <>
                        <h1 className={styles.title}>Заказ готов к оплате</h1>
                        <p className={styles.cost}>Сумма заказа: {order.productsCost}</p>
                        {
                            payment &&
                            <Button text='Перейти к оплате' onClick={() => router.push(payment?.confirmationUrl)}/>
                        }
                    </>
                }
                {
                    order && order.orderStatus !== 'PLACED' &&
                    <>
                        <h1 className={styles.title}>Ошибка валидации: {order.orderStatus}</h1>
                        <Button text='Вернуться в корзину' onClick={() => router.replace('/cart')}/>
                    </>
                }


            </div>
            <Footer logo='/assets/placeholder/logo.svg'
                    vk_link='/#'
                    tg_link='/#'
            />
        </div>
    )
}
