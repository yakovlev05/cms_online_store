import styles from '@/src/styles/cart.module.css';
import {CartResponseDto} from "@/src/api/models/response/cart";
import {DeliveryInfo} from "@/app/cart/page";
import {createOrder} from "@/src/api/service/orderService";
import {toast, Toaster} from "react-hot-toast";
import {deleteFromCart} from "@/src/api/service/cartService";
import {useRouter} from "next/navigation";

export default function SummaryBox({cart, deliveryInfo}: {
    cart: CartResponseDto[] | undefined,
    deliveryInfo: DeliveryInfo
}) {
    const router = useRouter();

    const handleSubmit = () => {
        if (!cart) return;

        // Выбранные для заказа предметы корзины
        const selectedCartItems = cart.filter(item => item.selected)

        createOrder({
            products: selectedCartItems.map(item => ({
                id: item.product.id,
                name: item.product.name,
                count: item.count,
                price: item.product.price
            })),
            customerInfo: {
                firstName: deliveryInfo.nameCustomer.split(' ')[1] || '',
                secondName: deliveryInfo.nameCustomer.split(' ')[0] || '',
                patronymic: deliveryInfo.nameCustomer.split(' ')[2] || '',
                phoneNumber: deliveryInfo.phoneCustomer
            },
            recipientInfo: {
                firstName: deliveryInfo.nameRecipient.split(' ')[1] || '',
                secondName: deliveryInfo.nameRecipient.split(' ')[0] || '',
                patronymic: deliveryInfo.nameRecipient.split(' ')[2] || '',
                phoneNumber: deliveryInfo.phoneRecipient
            },
            communicationMethodId: 1,
            paymentType: 'ONLINE_PAYMENT',
            commentForRecipient: '',
            orderComment: deliveryInfo.comment,
            receivingInfo: {
                type: deliveryInfo.type,
                dateTimeReceivingInSeconds: (deliveryInfo.dateReceivingInMS + deliveryInfo.timeReceivingInMS) / 1000,
                address: {
                    country: '',
                    state: '',
                    city: '',
                    street: '',
                    houseNumber: '',
                    flatNumber: ''
                }
            },
        })
            .then(r => {
                console.log(r)
                synchronizedCart(selectedCartItems)
                router.push('/payment/checkout?order-id=' + r.orderId)
            })
            .catch(err => toast.error(err.message))
    }

    const synchronizedCart = (itemsCart: CartResponseDto[]) => {
        itemsCart.forEach(item => {
            deleteFromCart(item.id, item.product.urlName)
                .catch(err => toast.error(err.message))
        })
    }

    return (
        <div className={styles.summaryContainer}>
            <Toaster/>
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
            <button className={styles.summaryButton} onClick={handleSubmit}>
                Заказать
            </button>
        </div>
    );
}
