'use client';

import { useState } from 'react';
import styles from '@/src/styles/cart.module.css';

export default function DeliveryOptions() {
    const [deliveryType, setDeliveryType] = useState<'pickup' | 'courier'>(
        'pickup'
    );

    return (
        <div>
            {/* МНЕ БЫЛО ЛЕНЬ ДЕЛАТЬ ПРАВИЛО В STYLES */}
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                <h2 className={styles.title}>Способ доставки</h2>
                <div className={styles.toggleButtons}>
                    <button
                        onClick={() => setDeliveryType('pickup')}
                        className={`${styles.button} ${deliveryType === 'pickup' ? styles.active : styles.inactive}`}
                    >Самовывоз</button>
                    <button
                        onClick={() => setDeliveryType('courier')}
                        className={`${styles.button} ${deliveryType === 'courier' ? styles.active : styles.inactive}`}
                    >Курьером</button>
                </div>
            </div>
            <div className={styles.inputsContainer}>
                <label className={styles.labelField}>
                    Дата получения:
                    <input type="date" className={styles.inputField} />
                </label>
                <label className={styles.labelField}>
                    Время получения:
                    <input type="time" className={styles.inputField} />
                </label>
                <label className={styles.labelField}>
                    Пункт выдачи:
                    <input type="text" className={styles.inputField} />
                </label>
            </div>
        </div>
    );
}
