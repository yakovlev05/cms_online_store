import styles from '@/src/styles/collections.module.css';
import Slider from "@/src/components/slider";
import Tile from "@/src/components/tile";

const Collections = () => {
    return (
        <div className={styles.collections}>
            <h2 className={styles.title}>Коллекции</h2>
            <Slider sliderWidth={1156}
                    sliderHeight={229}
                    countShow={5}
                    gap={24}
            >
                <Tile name='Монобукеты' img='/assets/placeholder/tile1.jpg'/>
                <Tile name='Для мужчин' img='/assets/placeholder/tile2.jpg'/>
                <Tile name='Для женщин' img='/assets/placeholder/tile3.jpg'/>
                <Tile name='Сезонные' img='/assets/placeholder/tile4.jpg'/>
                <Tile name='Классические' img='/assets/placeholder/tile5.jpg'/>
            </Slider>
        </div>
    )
}

export default Collections;