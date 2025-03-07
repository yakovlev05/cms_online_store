import {useRouter, useSearchParams} from "next/navigation";
import {useEffect, useState} from "react";
import {OrderInfoResponseDto} from "@/src/api/models/response/order";
import {PaymentUrlResponseDto} from "@/src/api/models/response/payment";
import {getOrderInfo} from "@/src/api/service/orderService";
import {getPaymentUrl} from "@/src/api/service/paymentService";
import {toast} from "react-hot-toast";
import MiniLoader from "@/src/components/ui/mini-loader";
import Button from "@/src/components/ui/button";
import styles from '@/src/styles/checkout-component.module.css'

const Checkout = () => {
    const params = useSearchParams();
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
                        toast.error('Таймаут проверки заказа')
                        return;
                    }

                    if (order.orderStatus === 'PROCESSING') {
                        return
                    } else if (order.orderStatus === 'PLACED') {
                        let countPaymentRequests = 0;
                        
                        const paymentIntervalId = setInterval(()=>{
                            
                            countPaymentRequests++;
                            if(countPaymentRequests==10){
                                clearInterval(orderIntervalId);
                                toast.error('не смогли дождаться получения url оплаты')
                                return;
                            }
                            
                            getPaymentUrl(orderId)
                                .then(payment => {
                                    setPayment(payment);
                                    setOrder(order);
                                    clearInterval(paymentIntervalId);
                                })
                                .catch((err) => toast.error(err.message));
                            
                        }, 1000)
                        
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
    )
}

export default Checkout;
