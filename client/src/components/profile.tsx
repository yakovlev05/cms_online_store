'use client';

import React, {useState, useEffect} from 'react';
import Image from 'next/image';
import styles from '@/src/styles/profile.module.css';
import {getMyUser, updateUser} from '@/src/api/service/userService';
import {toast, Toaster} from "react-hot-toast";
import {checkAuth} from "@/src/api/service/authService";
import {useRouter} from 'next/navigation';
import {UserResponseDto} from "@/src/api/models/response/user";

interface UserProfileProps {
    userId: string;
}

const UserProfile: React.FC<UserProfileProps> = () => {
    const [user, setUser] = useState<UserResponseDto>({
        address: '',
        phoneNumber: '',
        id: '',
        lastName: '',
        patronymic: '',
        firstName: ''
    });
    const [form, setForm] = useState({
        firstName: '',
        lastName: '',
        patronymic: '',
        phoneNumber: '',
        password: '',
    })
    const [isEditing, setIsEditing] = useState(false);
    const [isLoading, setIsLoading] = useState(true);
    const router = useRouter();

// Загрузка данных пользователя
    useEffect(() => {
        getMyUser()
            .then(r => {
                setUser(r)
                setForm({
                    firstName: r.firstName || '',
                    lastName: r.lastName || '',
                    password: '',
                    patronymic: r.patronymic || '',
                    phoneNumber: r.phoneNumber,
                })
                setIsLoading(false);
            })
            .catch(err => toast.error(err.message));

        checkAuth()
            .then((r) => {
                if (!r) {
                    router.replace('/login')
                }
            })
            .catch((err) => toast(err.message))
    }, [router]);


    // Рендеринг аватара (можно убрать, если нет аватара)
    const renderAvatar = () => (
        <div style={{
            width: '50px',
            height: '50px',
            position: 'relative'
        }}>
            <Image
                src={'/assets/icon/profile.svg'}
                alt={`Аватар ${user?.firstName} ${user?.lastName}`}
                fill
                style={{
                    borderRadius: '50%',
                    objectFit: 'cover'
                }}
                sizes="(max-width: 768px) 50px, 50px"
                priority
            />
        </div>
    );

    const handleSubmit = () => {
        updateUser(user.id, {
            fistName: form.firstName,
            lastName: form.lastName,
            patronymic: form.patronymic,
            phoneNumber: form.phoneNumber,
            password: form.password === null || form.password === '' ? null : form.password,
        })
            .then((r) => {
                setUser(r)
                toast.success('Успешно обновлено')
                setIsEditing(false);
            })
            .catch(err => toast.error(err.message));
    }


    return (
        <div className={styles.container}>
            <Toaster/>
            <div className={styles.avatarContainer}>
                {renderAvatar()}
            </div>

            {
                isLoading && <p>Загрузка...</p>
            }

            {
                !isLoading && !isEditing &&
                <div className={styles.infoContainer}>
                    <div className={styles.profileInfo}>
                        <h2 className={styles.profileName}>
                            {`${(user.lastName || '') + ' ' + (user.firstName || '') + ' ' + (user.patronymic || '')}`}
                        </h2>
                        <div className={styles.profileDetails}>
                            <p>Телефон: {user.phoneNumber}</p>
                            <p>Адрес: {user.address || 'Не указано'}</p>
                        </div>
                    </div>
                    <button
                        className={styles.editButton}
                        onClick={() => setIsEditing(true)}
                    >
                        Редактировать
                    </button>
                </div>
            }

            {
                !isLoading && isEditing &&
                <div
                    className={styles.form}
                >
                    <div>
                        <label className={styles.formLabel}>ФИО:</label>
                        <input
                            className={styles.formInput}
                            type="text"
                            name="lastName"
                            defaultValue={user.lastName || ''}
                            onChange={e => setForm({...form, lastName: e.target.value})}
                            placeholder="Фамилия"
                            required
                        />
                        <input
                            className={styles.formInput}
                            type="text"
                            name="firstName"
                            defaultValue={user.firstName || ''}
                            onChange={e => setForm({...form, firstName: e.target.value})}
                            placeholder="Имя"
                            required
                        />
                        <input
                            className={styles.formInput}
                            type="text"
                            name="patronymic"
                            defaultValue={user.patronymic || ''}
                            onChange={e => setForm({...form, patronymic: e.target.value})}
                            placeholder="Отчество"
                        />
                    </div>

                    <div>
                        <label className={styles.formLabel}>Номер телефона:</label>
                        <input
                            className={styles.formInput}
                            type="tel"
                            name="phoneNumber"
                            defaultValue={user.phoneNumber}
                            onChange={e => setForm({...form, phoneNumber: e.target.value})}
                            placeholder="7XXXXXXXXXX"
                            required
                        />
                    </div>

                    <div>
                        <label className={styles.formLabel}>Новый пароль (опционально):</label>
                        <input
                            className={styles.formInput}
                            type="text"
                            name="password"
                            onChange={e => setForm({...form, password: e.target.value})}
                            placeholder="Минимум 8 символов"
                        />
                    </div>

                    <div className={styles.formActions}>
                        <button
                            type="submit"
                            className={styles.saveButton}
                            onClick={handleSubmit}
                        >
                            Сохранить
                        </button>
                        <button
                            type="button"
                            className={styles.cancelButton}
                            onClick={() => setIsEditing(false)}
                        >
                            Отмена
                        </button>
                    </div>
                </div>
            }

        </div>
    )
};

export default UserProfile;
