import styles from '@/src/styles/new.module.css';
import ProductCard from "@/src/components/product-card";

const SimilarProd = () => {
    return (
        <div className={styles.new}>
            <h2 className={styles.title}>Вам также может понравиться:</h2>
            <div className={styles.cardsContainer}>
                <ProductCard price={1000} oldPrice={2000} name='Букет роз'
                             img='/assets/placeholder/card-placeholder.jpg'/>
                <ProductCard price={1000} oldPrice={2000} name='Букет роз'
                             img='/assets/placeholder/card-placeholder.jpg'/>
                <ProductCard price={1000} oldPrice={2000} name='Букет роз'
                             img='/assets/placeholder/card-placeholder.jpg'/>
                <ProductCard price={1000} oldPrice={2000} name='Букет роз'
                             img='/assets/placeholder/card-placeholder.jpg'/>
                <ProductCard price={1000} oldPrice={2000} name='Букет роз'
                             img='/assets/placeholder/card-placeholder.jpg'/>
                <ProductCard price={1000} oldPrice={2000} name='Букет роз'
                             img='/assets/placeholder/card-placeholder.jpg'/>
                <ProductCard price={1000} oldPrice={2000} name='Букет роз'
                             img='/assets/placeholder/card-placeholder.jpg'/>
                <ProductCard price={1000} oldPrice={2000} name='Букет роз'
                             img='/assets/placeholder/card-placeholder.jpg'/>
            </div>
        </div>
    )
}

export default SimilarProd;