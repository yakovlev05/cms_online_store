import styles from '@/src/styles/tile.module.css'
import Image from "next/image";
import React from "react";
import Link from 'next/link';

interface Props {
    name: string,
    img: string,
}

const Tile: React.FC<Props> = ({name, img}) => {
    return (
        <div className={styles.tile}>
            <Link href="/catalog/classic" passHref>
                <p className={styles.name}>{name}</p>
                <Image src={img} alt='букет' width='180' height='180' className={styles.img}/>
            </Link>
        </div>
    )
}

export default Tile;