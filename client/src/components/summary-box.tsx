import styles from '@/src/styles/cart.module.css';
import {CartResponseDto} from "@/src/api/models/response/cart";

export default function SummaryBox({cart}: { cart: CartResponseDto[] | undefined }) {
    return (
        <div className={styles.summaryContainer}>
            <div className={styles.summaryItem}>
                <span>Товары:</span>
                <span>
                    {cart && cart.length > 0
                        ? cart.map(item => item.count * (item.product.price + item.product.discount))
                            .reduce((a, b) => a + b)
                        : 0
                    }₽
                </span>
            </div>
            <div className={styles.summaryItem}>
                <span>Скидка:</span>
                <span>
                    {cart && cart.length > 0
                        ? cart.map(item => item.count * item.product.discount).reduce((a, b) => a + b)
                        : 0
                    }₽
                </span>
            </div>
            <div className={styles.summaryItem}>
                <span>Доставка:</span>
                <span>бесплатно</span>
            </div>
            <hr/>
            <div className={styles.my2}>
                <h2 className={styles.h2}>Итого</h2>
                <span>
                    {
                        cart && cart.length > 0
                            ? cart.map(item => item.count * item.product.price).reduce((a, b) => a + b)
                            : 0
                    }₽
                </span>
            </div>
            <button className={styles.summaryButton}>
                Заказать
            </button>
        </div>
    );
}
