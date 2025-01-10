'use client'
import styles from '@/src/styles/input.module.css';
import React from "react";

interface Props {
    label: string;
    placeholder?: string;
    inputName: string;
    inputAutoComplete: string;
    inputType: string;
    onChange?: (e: React.ChangeEvent<HTMLInputElement>) => void;
}

const Input: React.FC<Props> = ({label, placeholder = label, inputName, inputAutoComplete, inputType, onChange}) => {
    return (
        <div>
            <p className={styles.label}>{label}</p>
            <input placeholder={placeholder} type={inputType} autoComplete={inputAutoComplete} name={inputName}
                   className={styles.input} onChange={e => onChange && onChange(e)}/>
        </div>
    )
}

export default Input;