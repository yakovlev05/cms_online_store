import Header from '@/src/components/header';
import Footer from '@/src/components/footer';
import styles from '@/src/styles/story.module.css';
import Image from 'next/image';

export default function StoryPage() {
    return (
        <div className={styles.home}>
            <Header logo="/assets/placeholder/logo.svg" />
            
            <main className={styles.aboutContainer}>
                <section className={styles.heroSection}>
                    <h1>Цветочная Летопись: Bloomify</h1>
                    <p>История о том, как студенческая мечта превратилась в цветущую реальность</p>
                </section>

                <section className={styles.storySection}>
                    <div className={styles.storyContent}>
                        <h2>Как всё начиналось</h2>
                        <p>
                            Все началось в уютной университетской аудитории, где четыре студента 
                            радиофака - гениальный бэкенд-программист Алексей, креативный дизайнер Александр,
                            умелец на все руки фронтенд-программист Юрий и 
                            умный аналитик Трофим - объединились мечтой возродить
                            искусство передавать эмоции через цветы.
                        </p>
                        <p>
                            Вдохновленные историей дедушки-флориста, мы создали Bloomify - 
                            платформу, где каждый букет - это уникальное послание, история, 
                            эмоциональный мост между людьми.
                        </p>
                    </div>
                    <div className={styles.storyImage}>
                        <Image 
                            src="/assets/placeholder/ourteam1.png" 
                            alt="Команда Bloomify" 
                            width={637} 
                            height={358}
                        />
                    </div>
                </section>

                <section className={styles.socialImpactSection}>
                    <h2 className={styles.title}>Наша философия</h2>
                    <div className={styles.impactGrid}>
                        <div className={styles.impactItem}>
                            <h3>Язык Эмоций</h3>
                            <p>Цветы - универсальный набор чувств и переживаний</p>
                        </div>
                        <div className={styles.impactItem}>
                            <h3>Уникальность</h3>
                            <p>Каждый букет - как отпечаток пальца, неповторимый и особенный</p>
                        </div>
                        <div className={styles.impactItem}>
                            <h3>Технологичность</h3>
                            <p>Современный подход к флористике с душой и заботой</p>
                        </div>
                    </div>
                </section>

                <section className={styles.socialImpactSection}>
                    <h2>Наш социальный вклад</h2>
                    <div className={styles.impactGrid}>
                        <div className={styles.impactItem}>
                            <h3>Поддержка фермеров</h3>
                            <p>Мы работаем только с локальными производителями</p>
                        </div>
                        <div className={styles.impactItem}>
                            <h3>Экология</h3>
                            <p>Используем только экологичную упаковку</p>
                        </div>
                        <div className={styles.impactItem}>
                            <h3>Благотворительность</h3>
                            <p>Программа "Цветок надежды" - букеты с добрым сердцем</p>
                        </div>
                    </div>
                </section>
            </main>
            
            <Footer
                logo="/assets/placeholder/logo.svg"
                vk_link="/#"
                tg_link="/#"
            />
        </div>
    );
}
