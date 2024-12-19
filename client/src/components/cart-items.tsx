'use client';

import { useState } from 'react';

interface CartItem {
  id: number;
  name: string;
  price: number;
  quantity: number;
  image: string;
}

const initialCartItems: CartItem[] = [
  { id: 1, name: 'Букет роз', price: 1000, quantity: 1, image: '/rose.png' },
  { id: 2, name: 'Букет роз', price: 1000, quantity: 1, image: '/rose.png' },
  { id: 3, name: 'Букет роз', price: 1000, quantity: 1, image: '/rose.png' },
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
    <div>
      <h2 className="text-2xl font-semibold mb-4">Товары</h2>
      {cartItems.map((item) => (
        <div key={item.id} className="flex items-center justify-between mb-4">
          <div className="flex items-center">
            <img src={item.image} alt={item.name} className="w-16 h-16 mr-4" />
            <span>{item.name}</span>
          </div>
          <div className="flex items-center">
            <button
              onClick={() => handleQuantityChange(item.id, -1)}
              className="p-2"
            >
              -
            </button>
            <span className="mx-2">{item.quantity}</span>
            <button
              onClick={() => handleQuantityChange(item.id, 1)}
              className="p-2"
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
