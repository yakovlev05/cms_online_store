import Header from '@/src/components/header';
import UserProfile from '@/src/components/profile';
import OrderHistory from '@/src/components/order-history';
import Footer from '@/src/components/footer';
import styles from '@/src/styles/profile.module.css'

export default function Profile() {
    return (
        <div className={styles.mainContainer}>
            <Header logo='/assets/placeholder/logo.svg'/>
            <div className={styles.profileContainer}>
                <UserProfile/>
                <OrderHistory/>
            </div>
            <Footer logo='/assets/placeholder/logo.svg'
                    vk_link='/#'
                    tg_link='/#'
            />
        </div>
    )
}
