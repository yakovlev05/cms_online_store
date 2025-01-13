'use client';

import React, {useEffect, useState} from 'react';
import Link from 'next/link';
import styles from '@/src/styles/order-history.module.css';
import {getMyOrders} from "@/src/api/service/orderService";
import {OrderInfoResponseDto} from "@/src/api/models/response/order";
import {toast, Toaster} from "react-hot-toast";

const OrderHistory: React.FC = () => {
    // Здесь в будущем можно будет добавить логику получения заказов
    const [orders, setOrders] = useState<OrderInfoResponseDto[]>([]);

    useEffect(() => {
        getMyOrders()
            .then(r => setOrders(r))
            .catch(e => toast.error(e));
    }, []);

    return (
        <div className={styles.orderHistoryContainer}>
            <Toaster/>
            <h1 className={styles.pageTitle}>История заказов</h1>

            {
                orders.length === 0 &&
                <div className={styles.emptyOrderContainer}>
                    <p className={styles.emptyOrderText}>
                        Похоже, вы ещё ничего не заказывали
                    </p>
                    <Link
                        // можно указать рандомный адрес для динамики
                        href="/"
                        className={styles.catalogLink}
                    >
                        Перейти на главную
                    </Link>
                </div>
            }
            
            {
                orders.length > 0 &&
                orders.map((order, index) => (
                    <div key={index} className={styles.orderCard}>
                        <p>Заказ на сумму: {order.paymentInfo.finalSum}</p>
                        <p>Дата
                            заказа: {order.createdAt[0] + '-' + order.createdAt[1] + '-' + order.createdAt[2]}</p>
                    </div>
                ))
            }


        </div>
    );
};

export default OrderHistory;
