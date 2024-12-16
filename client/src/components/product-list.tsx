"use client";

import React, { useState } from "react";
import ProductCard from "@/src/components/product-card";
import styles from "@/src/styles/new.module.css";
import Link from "next/link";

interface Product {
  id: number;
  name: string;
  price: number;
  oldPrice: number;
  img: string;
  category: string;
}

interface ProductListProps {
  category: string;
}

const ProductList: React.FC<ProductListProps> = ({ category }) => {
  const [sortBy, setSortBy] = useState<"name" | "price" | null>(null); // Состояние сортировки

  const products: Product[] = [
    { id: 1, name: "Новинка 1", price: 1500, oldPrice: 2000, img: '/assets/placeholder/nono.png', category: "novelties" },
    { id: 2, name: "Сезонный 1", price: 1400, oldPrice: 1900, img: '/assets/placeholder/seasonal.jpg', category: "seasonal" },
    { id: 3, name: "Монобукет 1", price: 1700, oldPrice: 2200, img: '/assets/placeholder/monob.jpg', category: "monobouquets" },
    { id: 4, name: "Для мужчин 1", price: 1800, oldPrice: 2300, img: '/assets/placeholder/men.jpg', category: "formen" },
    { id: 5, name: "Для женщин 1", price: 1200, oldPrice: 1700, img: '/assets/placeholder/women.png', category: "forwomen" },
    { id: 6, name: "Классический 1", price: 1200, oldPrice: 900, img: '/assets/placeholder/classic.jpg', category: "classic" },
    { id: 7, name: "Новинка 2", price: 1300, oldPrice: 1800, img: '/assets/placeholder/nono.png', category: "novelties" },
    { id: 8, name: "Сезонный 2", price: 1000, oldPrice: 900, img: '/assets/placeholder/seasonal.jpg', category: "seasonal" },
    { id: 9, name: "Монобукет 2", price: 10700, oldPrice: 22200, img: '/assets/placeholder/monob.jpg', category: "monobouquets" },
    { id: 10, name: "Для мужчин 2", price: 2800, oldPrice: 2300, img: '/assets/placeholder/men.jpg', category: "formen" },
    { id: 11, name: "Для женщин 2", price: 1488, oldPrice: 1500, img: '/assets/placeholder/women.png', category: "forwomen" },
    { id: 12, name: "Классический 2", price: 1100, oldPrice: 2100, img: '/assets/placeholder/classic.jpg', category: "classic" },
    { id: 13, name: "Новинка 3", price: 1500, oldPrice: 2000, img: '/assets/placeholder/nono.png', category: "novelties" },
    { id: 14, name: "Сезонный 3", price: 1400, oldPrice: 1900, img: '/assets/placeholder/seasonal.jpg', category: "seasonal" },
    { id: 15, name: "Монобукет 3", price: 1700, oldPrice: 2200, img: '/assets/placeholder/monob.jpg', category: "monobouquets" },
    { id: 16, name: "Для мужчин 3", price: 1800, oldPrice: 2300, img: '/assets/placeholder/men.jpg', category: "formen" },
    { id: 17, name: "Для женщин 3", price: 1200, oldPrice: 1700, img: '/assets/placeholder/women.png', category: "forwomen" },
    { id: 18, name: "Классический 3", price: 1000, oldPrice: 2100, img: '/assets/placeholder/classic.jpg', category: "classic" },
    { id: 19, name: "Новинка 3", price: 1500, oldPrice: 2000, img: '/assets/placeholder/nono.png', category: "novelties" },
    { id: 20, name: "Сезонный 3", price: 1400, oldPrice: 1900, img: '/assets/placeholder/seasonal.jpg', category: "seasonal" },
    { id: 21, name: "Монобукет 3", price: 1700, oldPrice: 2200, img: '/assets/placeholder/monob.jpg', category: "monobouquets" },
    { id: 22, name: "Для мужчин 3", price: 1800, oldPrice: 2300, img: '/assets/placeholder/men.jpg', category: "formen" },
    { id: 23, name: "Для женщин 3", price: 1200, oldPrice: 1700, img: '/assets/placeholder/women.png', category: "forwomen" },
    { id: 24, name: "Классический 4", price: 900, oldPrice: 2100, img: '/assets/placeholder/classic.jpg', category: "classic" },
    { id: 25, name: "Новинка 4", price: 1500, oldPrice: 2000, img: '/assets/placeholder/nono.png', category: "novelties" },
    { id: 26, name: "Сезонный 4", price: 1400, oldPrice: 1900, img: '/assets/placeholder/seasonal.jpg', category: "seasonal" },
    { id: 27, name: "Монобукет 4", price: 1700, oldPrice: 2200, img: '/assets/placeholder/monob.jpg', category: "monobouquets" },
    { id: 28, name: "Для мужчин 4", price: 1800, oldPrice: 2300, img: '/assets/placeholder/men.jpg', category: "formen" },
    { id: 29, name: "Для женщин 4", price: 1200, oldPrice: 1700, img: '/assets/placeholder/women.png', category: "forwomen" },
    { id: 30, name: "Классический 5", price: 1600, oldPrice: 2100, img: '/assets/placeholder/classic.jpg', category: "classic" },
  ];

  // Фильтрация по категории
  const filteredProducts = products.filter((product) => product.category === category);

  // Сортировка товаров
  const sortedProducts = [...filteredProducts].sort((a, b) => {
    if (sortBy === "name") {
      return a.name.localeCompare(b.name); // Сортировка по имени
    }
    if (sortBy === "price") {
      return a.price - b.price; // Сортировка по цене (по возрастанию)
    }
    return 0;
  });

  return (
    <div className={styles.home}>
      <div className={styles.sortContainer}>
        <span>Сортировать по: </span>
        <button onClick={() => setSortBy("name")}> имени</button>
        <span> / </span>
        <button onClick={() => setSortBy("price")}>цене</button>
      </div>
      
      <div className={styles.cardsContainer}>
        {sortedProducts.length > 0 ? (
          sortedProducts.map((product) => (
            <ProductCard
              key={product.id}
              name={product.name}
              price={product.price}
              oldPrice={product.oldPrice}
              img={product.img}
            />
          ))
        ) : (
          <p>Товары в данной категории отсутствуют.</p>
        )}
      </div>
    </div>
  );
};

export default ProductList;