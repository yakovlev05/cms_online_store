'use client';

import React from 'react';
import Link from 'next/link';
import styles from '@/src/styles/order-history.module.css';

const OrderHistory: React.FC = () => {
    // Здесь в будущем можно будет добавить логику получения заказов
    const hasOrders = false;

    return (
        <div className={styles.orderHistoryContainer}>
            <h1 className={styles.pageTitle}>История заказов</h1>

            {!hasOrders ? (
                <div className={styles.emptyOrderContainer}>
                    <p className={styles.emptyOrderText}>
                        Похоже, вы ещё ничего не заказывали
                    </p>
                    <Link
                        // можно указать рандомный адрес для динамики
                        href="/catalog"
                        className={styles.catalogLink}
                    >
                        Перейти в Каталог
                    </Link>
                </div>
            ) : (
                // Здесь будет список заказов в будущем
                <div>
                    {/* Список заказов */}
                </div>
            )}
        </div>
    );
};

export default OrderHistory;
