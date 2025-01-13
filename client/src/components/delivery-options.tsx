'use client';

import {Dispatch, SetStateAction} from 'react';
import styles from '@/src/styles/cart.module.css';
import {DeliveryInfo} from "@/app/cart/page";

export default function DeliveryOptions({deliveryInfo, setDeliveryInfo}: {
    deliveryInfo: DeliveryInfo,
    setDeliveryInfo: Dispatch<SetStateAction<DeliveryInfo>>
}) {

    return (
        <div>
            {/* МНЕ БЫЛО ЛЕНЬ ДЕЛАТЬ ПРАВИЛО В STYLES */}
            <div style={{display: 'flex', justifyContent: 'space-between', alignItems: 'center'}}>
                <h2 className={styles.title}>Способ доставки</h2>
                <div className={styles.toggleButtons}>
                    <button
                        onClick={() => setDeliveryInfo({...deliveryInfo, type: 'SELF_CALL'})}
                        className={`${styles.button} ${deliveryInfo.type === 'SELF_CALL' ? styles.active : styles.inactive}`}
                    >Самовывоз
                    </button>
                    <button
                        onClick={() => setDeliveryInfo({...deliveryInfo, type: 'DELIVERY'})}
                        className={`${styles.button} ${deliveryInfo.type === 'DELIVERY' ? styles.active : styles.inactive}`}
                    >Курьером
                    </button>
                </div>
            </div>
            <div className={styles.inputsContainer}>
                <label className={styles.labelField}>
                    Дата получения:
                    <input type="date" className={styles.inputField} onChange={e => {
                        if (e.target.value) {
                            const date = Date.parse(e.target.value)
                            setDeliveryInfo({...deliveryInfo, dateReceivingInMS: date})
                        }
                    }}/>
                </label>
                <label className={styles.labelField}>
                    Время получения:
                    <input type="time" className={styles.inputField} onChange={e => {
                        console.log(e.target.value)
                        if (e.target.value) {
                            const time = e.target.value.split(':')
                            setDeliveryInfo({
                                ...deliveryInfo,
                                timeReceivingInMS: Number(time[0]) * 60 * 60 * 1000 + Number(time[1]) * 60 * 1000
                            })
                        }

                    }}/>
                </label>
                {/*<label className={styles.labelField}>*/}
                {/*    Пункт выдачи:*/}
                {/*    <input type="text" className={styles.inputField}/>*/}
                {/*</label>*/}
            </div>
        </div>
    );
}
