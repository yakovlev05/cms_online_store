import styles from '@/src/styles/auth-otp.module.css'
import AuthOtpSelection from "@/src/components/auth-otp-selection";
import {SmartCaptcha} from "@yandex/smart-captcha";
import AuthOtpConfirmation from "@/src/components/auth-otp-confirmation";
import {useState} from "react";

export interface AuthOtpProps {
    destination: string;
    captchaToken: string;
    step: "captcha" | "selection" | "confirmation";
    type: 'TEXT_TO_SPEECH' | 'FLASHCALL' | '';
    id: string;
    code: string;
}

const AuthOtp = ({destination}: { destination: string }) => {
    const [otp, setOtp] = useState<AuthOtpProps>({
        destination: destination,
        captchaToken: "",
        step: "captcha",
        type: '',
        id: '',
        code: '',
    })

    return (
        <div className={styles.container}>
            <h1 className={styles.title}>Верификация телефона</h1>
            {
                otp.step === "captcha" &&
                <div className={styles.captcha}>
                    <SmartCaptcha sitekey={process.env.NEXT_PUBLIC_YANDEX_CAPTCHA_KEY || ""}
                                  onSuccess={e => setOtp({...otp, captchaToken: e, step: "selection"})}/>
                </div>
            }
            {
                otp.step === "selection" &&
                <AuthOtpSelection setOtp={setOtp} otp={otp}/>
            }
            {
                otp.step === 'confirmation' &&
                <AuthOtpConfirmation setOtp={setOtp} otp={otp}/>
            }
        </div>
    )
}

export default AuthOtp;
