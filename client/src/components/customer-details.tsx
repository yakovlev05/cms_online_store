import styles from '@/src/styles/cart.module.css';

export default function CustomerDetails() {
    return (
        <div>
            <h2 className={styles.title}>Данные заказчика</h2>
            <div className={styles.inputsContainer}>
                <div>
                    <label className={styles.labelField}>ФИО заказчика*:</label>
                    <input type="text" id="name" className={styles.inputField} />
                </div>
                <div>
                    <label className={styles.labelField}>Номер телефона заказчика*:</label>
                    <input type="tel" className={styles.inputField} />
                </div>
                <div>
                    <label className={styles.labelField}>ФИО получателя:</label>
                    <input type="text" id="name" className={styles.inputField} />
                </div>
                <div>
                    <label className={styles.labelField}>Номер телефона получателя:</label>
                    <input type="tel" className={styles.inputField} />
                </div>
                <div>
                    <label className={styles.labelField}>Комментарий к доставке:</label>
                    <textarea className={styles.inputFieldWide} rows={3}></textarea>
                </div>
            </div>
        </div>
    );
}
