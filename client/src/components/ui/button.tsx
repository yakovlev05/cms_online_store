import styles from '@/src/styles/button.module.css';
import React from "react";

interface Props {
    text: string;
}

const Button: React.FC<Props> = ({text}) => {
    return (
        <button type='submit' className={styles.button}>{text}</button>
    )
}

export default Button;