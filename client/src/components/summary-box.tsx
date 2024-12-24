import styles from '@/src/styles/cart.module.css';

export default function SummaryBox() {
    return (
        <div className={styles.summaryContainer}>
            <div className={styles.summaryItem}>
                <span>Товары:</span>
                <span>4000₽</span>
            </div>
            <div className={styles.summaryItem}>
                <span>Скидка:</span>
                <span>-2000₽</span>
            </div>
            <div className={styles.summaryItem}>
                <span>Доставка:</span>
                <span>бесплатно</span>
            </div>
            <hr />
            <div className={styles.my2}>
                <h2 className={styles.h2}>Итого</h2>
                <span>2000₽</span>
            </div>
            <button className={styles.summaryButton}>
                Заказать
            </button>
        </div>
    );
}
