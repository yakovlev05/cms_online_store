'use client';

import { useState } from 'react';
import styles from '@/src/styles/cart.module.css';
import Image from 'next/image';

interface CartItem {
  id: number;
  name: string;
  price: number;
  quantity: number;
  image: string;
}

const initialCartItems: CartItem[] = [
  { id: 1, name: 'Букет роз', price: 1000, quantity: 1, image: '/assets/placeholder/cart-placeholder.jpg' },
  { id: 2, name: 'Букет роз', price: 1000, quantity: 1, image: '/assets/placeholder/cart-placeholder.jpg' },
  { id: 3, name: 'Букет роз', price: 1000, quantity: 1, image: '/assets/placeholder/cart-placeholder.jpg' },
];

export default function CartItems() {
  const [cartItems, setCartItems] = useState(initialCartItems);

  const handleQuantityChange = (id: number, delta: number) => {
    setCartItems((prev) =>
      prev.map((item) =>
        item.id === id
          ? { ...item, quantity: Math.max(1, item.quantity + delta) }
          : item
      )
    );
  };

  return (
    <div className={styles.itemsContainer}>
      <h2 className={styles.h2}>Товары</h2>
      {cartItems.map((item) => (
        <div key={item.id} className={styles.item}>
          <Image className={styles.img} src='/assets/placeholder/Checkbox.png' alt='чекбокс' width={42} height={42}/> 
          <div className={styles.itemName}>
          <Image className={styles.img} src={item.image} alt="изображение букета" width={86} height={86}/>
            <span>{item.name}</span>
          </div>
          <div className={styles.quantityContainer}>
            <button
              onClick={() => handleQuantityChange(item.id, -1)}
              className={styles.button}
            >
              -
            </button>
            <span className={styles.quantity}>{item.quantity}</span>
            <button
              onClick={() => handleQuantityChange(item.id, 1)}
              className={styles.button}
            >
              +
            </button>
          </div>
          <span>{item.price}₽</span>
        </div>
      ))}
    </div>
  );
}
