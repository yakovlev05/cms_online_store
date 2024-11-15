import styles from '@/src/styles/banner.module.css';
import Image from "next/image";
import React from "react";

interface Props {
    img: string;
    title: string;
    description: string;
}

const Banner: React.FC<Props> = ({img, title, description}) => {
    return (
        <div className={styles.banner}>
            <Image src={img} alt='баннер' width='640' height='480'/>
            <div className={styles.textContainer}>
                <h1 className={styles.title}>{title}</h1>
                <p className={styles.description}>{description}</p>
            </div>
        </div>
    )
}

export default Banner;