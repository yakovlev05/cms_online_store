'use client'
import styles from '@/src/styles/slider.module.css'
import React, {useState} from "react";
import Arrow from "@/src/components/ui/arrow";

interface Props {
    children: React.ReactNode; // элементы для отображения
    countShow?: number; // Сколько элементов одновременно отображать
    gap?: number; // Свойство gap, div class = window, минимальное расстояние между элементами
    sliderWidth: number;
    sliderHeight: number;
}

const Slider: React.FC<Props> = ({children, countShow = 3, gap = 24, sliderHeight, sliderWidth}) => {
    const [current, setCurrent] = useState(0); // текущий элемент
    const items = React.Children.toArray(children); // массив элементов для отображения
    // элементы для отображения
    const visibleItems = Array.from({length: countShow}, (_, i) => {
        const index = (current + i) % items.length;
        return items[index];
    });

    const handleNext = () => {
        setCurrent(prev => (prev + 1) % items.length);
    };

    const handlePrev = () => {
        setCurrent(prev => (prev - 1 + items.length) % items.length);
    };

    return (
        <div className={styles.slider}
             style={{
                 maxWidth: `${sliderWidth}px`,
                 maxHeight: `${sliderHeight}px`,
             }}
        >
            <button onClick={handlePrev} className={styles.button}>
                <Arrow flipVertical={true}/>
            </button>
            <div
                className={styles.window}
                style={{
                    gap: `${gap}px`
                }}
            >
                {visibleItems}
            </div>
            <button onClick={handleNext} className={styles.button}>
                <Arrow/>
            </button>
        </div>
    )
}

export default Slider;