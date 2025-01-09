import Header from '@/src/components/header';
import Footer from '@/src/components/footer';
import styles from '@/src/styles/story.module.css';

export default function StoryPage() {
    return (
        <div className={styles.home}>
            <Header logo="/assets/placeholder/logo.svg" />
            <Footer
                logo="/assets/placeholder/logo.svg"
                vk_link="/#"
                tg_link="/#"
            />
        </div>
    );
}