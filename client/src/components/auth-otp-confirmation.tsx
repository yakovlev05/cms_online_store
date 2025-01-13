import styles from '@/src/styles/auth-otp-confirmation.module.css'
import Button from "@/src/components/ui/button";
import {Dispatch, SetStateAction} from "react";
import {AuthOtpProps, OtpSettings} from "@/src/components/auth-otp";
import {checkOtp, confirmPhone, sendOtp} from "@/src/api/service/authService";
import {toast, Toaster} from "react-hot-toast";


const AuthOtpConfirmation = ({setOtp, otp, settings}
                             : {
    setOtp: Dispatch<SetStateAction<AuthOtpProps>>,
    otp: AuthOtpProps,
    settings: OtpSettings
}) => {

    const handleCheck = () => {
        checkOtp({code: otp.code, id: otp.id})
            .then((r) => {
                if (r.status === 'OK') {
                    if (settings.onlyOtp) {
                        toast.success('Код верный')
                        settings.funcFinal(otp.id)
                        return;
                    }

                    confirmPhone({otpId: otp.id, phoneNumber: otp.destination})
                        .then(() => {
                            toast.success('Успешно подтверждено, войдите в аккаунт')
                            window.location.href = "/login"
                        })
                        .catch((err) => toast.error(err.message));

                } else if (r.status === 'WRONG_CODE') {
                    toast.error('Неверный код')
                } else {
                    toast.error(r.status)
                    toast.error('Попробуйте снова')
                }

            })
            .catch(err => toast.error(err.message));
    }

    const handleRetry = () => {
        sendOtp({id: otp.id})
            .then(() => toast.success('Звоним...'))
            .catch(err => toast.error(err.message))
    }

    return (
        <div className={styles.containerConfirmation}>
            <Toaster/>
            <h2 className={styles.title}>
                {
                    otp.type === 'TEXT_TO_SPEECH' && `Введите продиктованный код, мы звоним на номер: ${otp.destination}`
                }
                {
                    otp.type === 'FLASHCALL' && `Введите 4 последние цифры номера, мы звоним на номер: ${otp.destination}`
                }
            </h2>
            <input placeholder='Код подтверждения' className={styles.inputCode}
                   onChange={(e) => setOtp({...otp, code: e.target.value})}/>
            <Button text='Подтвердить' onClick={handleCheck}/>
            <p className={styles.retry} onClick={handleRetry}>Не пришёл код?</p>
        </div>
    )
}

export default AuthOtpConfirmation;
