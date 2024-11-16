import styles from '@/src/styles/nav-link.module.css';
import React from "react";
import Link from "next/link";

interface Props{
    text: string;
    url: string;
}

const NavLink : React.FC<Props> = ({text, url})=>{
    return(
        <span className={styles.span}>
            <Link href={url}>{text}</Link>
        </span>
    )
}

export default NavLink;