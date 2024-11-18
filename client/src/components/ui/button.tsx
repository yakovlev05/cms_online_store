import styles from '@/src/styles/button.module.css';
import React from "react";

interface Props {
    text: string;
    onClick?: () => void;
}

const Button: React.FC<Props> = ({text, onClick}) => {
    return (
        <button onClick={onClick} type='submit' className={styles.button}>{text}</button>
    )
}

export default Button;