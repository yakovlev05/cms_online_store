import styles from '@/src/styles/cart.module.css';
import {DeliveryInfo} from "@/app/cart/page";
import {Dispatch, SetStateAction} from "react";

export default function CustomerDetails({deliveryInfo, setDeliveryInfo}: {
    deliveryInfo: DeliveryInfo,
    setDeliveryInfo: Dispatch<SetStateAction<DeliveryInfo>>
}) {
    return (
        <div>
            <h2 className={styles.title}>Данные заказчика</h2>
            <div className={styles.inputsContainer}>
                <div>
                    <label className={styles.labelField}>ФИО заказчика*:</label>
                    <input type="text" id="name" className={styles.inputField} defaultValue={deliveryInfo.nameCustomer}
                           onChange={e => setDeliveryInfo({...deliveryInfo, nameCustomer: e.target.value})}/>
                </div>
                <div>
                    <label className={styles.labelField}>Номер телефона заказчика*:</label>
                    <input type="tel" className={styles.inputField} defaultValue={deliveryInfo.phoneCustomer}
                           onChange={e => setDeliveryInfo({...deliveryInfo, phoneCustomer: e.target.value})}/>
                </div>
                <div>
                    <label className={styles.labelField}>ФИО получателя:</label>
                    <input type="text" id="name" className={styles.inputField}
                           onChange={e => setDeliveryInfo({...deliveryInfo, nameRecipient: e.target.value})}/>
                </div>
                <div>
                    <label className={styles.labelField}>Номер телефона получателя:</label>
                    <input type="tel" className={styles.inputField}
                           onChange={e => setDeliveryInfo({...deliveryInfo, phoneRecipient: e.target.value})}/>
                </div>
                <div>
                    <label className={styles.labelField}>Комментарий к доставке:</label>
                    <textarea className={styles.inputFieldWide} rows={3}
                              onChange={e => setDeliveryInfo({...deliveryInfo, comment: e.target.value})}></textarea>
                </div>
            </div>
        </div>
    );
}
