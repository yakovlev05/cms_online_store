import styles from '@/src/styles/auth-otp-selection.module.css'
import Image from "next/image";
import {Dispatch, SetStateAction} from "react";
import {AuthOtpProps} from "@/src/components/auth-otp";
import {createOtp, sendOtp} from "@/src/api/service/authService";
import {toast, Toaster} from "react-hot-toast";


const AuthOtpSelection = ({setOtp, otp}: { setOtp: Dispatch<SetStateAction<AuthOtpProps>>, otp: AuthOtpProps }) => {

    const handleChoose = (channel: "" | "TEXT_TO_SPEECH" | "FLASHCALL") => {
        createOtp({
            captchaToken: otp.captchaToken,
            channelType: channel,
            destination: otp.destination,
        })
            .then(r => {

                sendOtp({id: r.id})
                    .then(() => {
                        setOtp({...otp, step: 'confirmation', type: channel, id: r.id})
                        toast.success('Звоним...')
                    })
                    .catch(err => toast.error(err.message))

            })
            .catch(err => toast.error(err.message));
    }

    return (
        <div className={styles.container}>
            <Toaster/>
            <h2 className={styles.mainTitle}>Выберите способ:</h2>
            <div className={styles.selectionCardsContainer}>
                <div className={styles.selectionCard}
                     onClick={() => {
                         handleChoose('TEXT_TO_SPEECH')
                     }}>
                    <Image src='/assets/icon/call.svg' alt='call' width='20' height='20'/>
                    <p className={styles.selectionCardTitle}>Продиктованный код</p>
                </div>
                <div className={styles.selectionCard}
                     onClick={() => {
                         handleChoose('FLASHCALL')
                     }}>
                    <Image src='/assets/icon/flash.svg' alt='flash' width='20' height='20'/>
                    <p className={styles.selectionCardTitle}>Последние 4 цифры номера</p>
                </div>
            </div>
        </div>
    )
}

export default AuthOtpSelection;
