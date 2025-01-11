'use client';

import styles from '@/src/styles/cart.module.css';
import Image from 'next/image';
import {CartResponseDto} from "@/src/api/models/response/cart";


export default function CartItems(
    {cart, handleDeleteAction, handleIncreaseCountAction, handleDecreaseCountAction}:
    {
        cart: CartResponseDto[] | undefined,
        handleDeleteAction: (id: number, productUrlName: string) => void,
        handleIncreaseCountAction: (item: CartResponseDto) => void,
        handleDecreaseCountAction: (item: CartResponseDto) => void,
    }) {
    return (
        <div className={styles.itemsContainer}>
            <h2 className={styles.h2}>Товары</h2>
            {
                !cart && <p>Загрузка...</p>
            }
            {
                cart && cart.map((item) => (
                    <div key={item.id} className={styles.item}>
                        <Image className={styles.img} src='/assets/placeholder/Checkbox.png' alt='чекбокс' width={42}
                               height={42}/>
                        <div className={styles.itemName}>
                            <Image className={styles.img} src={item.product.mainPhotoUrl} alt="изображение букета"
                                   width={86} height={86}/>
                            <span>{item.product.name}</span>
                        </div>
                        <div className={styles.quantityContainer}>
                            <button
                                className={styles.button}
                                onClick={()=>handleDecreaseCountAction(item)}
                            >
                                -
                            </button>
                            <span className={styles.quantity}>{item.count}</span>
                            <button
                                className={styles.button}
                                onClick={() => handleIncreaseCountAction(item)}
                            >
                                +
                            </button>
                        </div>
                        <span>{item.product.price * item.count}₽</span>
                        <button className={styles.deleteButton}
                                onClick={() => handleDeleteAction(item.id, item.product.urlName)}>Удалить
                        </button>
                    </div>
                ))
            }
        </div>
    );
}
