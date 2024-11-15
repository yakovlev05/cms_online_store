import Header from "@/src/components/header";
import Banner from "@/src/components/banner";
import styles from '@/src/styles/home.module.css';
import Collections from "@/src/components/collections";
import New from "@/src/components/ui/new";
import Footer from "@/src/components/footer";

export default function Home() {
    return (
        <div className={styles.home}>
            <Header logo='/assets/placeholder/logo.svg'/>
            <Banner img='/assets/placeholder/banner.jpg'
                    title='Добавьте праздничного блеска!'
                    description='Мы обновили нашу коллекцию в соответствии с сезоном.
                    Так что отправьте кому-то самую роскошную доставку цветов на это Рождество!'
            />
            <Collections/>
            <New/>
            <Footer logo='/assets/placeholder/logo.svg' vk_link='/#' tg_link='/#'/>
        </div>
    )
}
