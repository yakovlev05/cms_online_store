'use client';

import React, {useState, useEffect} from 'react';
import Image from 'next/image';
import styles from '@/src/styles/profile.module.css';
import {updateUser, getMyUser} from '@/src/api/service/userService';
import {UserResponseDto} from '@/src/api/models/response/user';
import {UpdateUserRequestDto} from '@/src/api/models/request/user';

interface UserProfileProps {
    userId: string;
}

const UserProfile: React.FC<UserProfileProps> = ({userId}) => {
    const [user, setUser] = useState<UserResponseDto | null>(null);
    const [isEditing, setIsEditing] = useState(false);
    const [formData, setFormData] = useState({
        firstName: '',
        lastName: '',
        patronymic: '',
        phoneNumber: '',
        address: ''
    });
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

// Загрузка данных пользователя
    useEffect(() => {
        const fetchUserData = async () => {
            try {
                setIsLoading(true);
                const userData = await getMyUser();
                setUser(userData);
                setFormData({
                    firstName: userData.firstName || '',
                    lastName: userData.lastName || '',
                    patronymic: userData.patronymic || '',
                    phoneNumber: userData.phoneNumber,
                    address: userData.address || ''
                });
            } catch (err) {
                setError('Не удалось загрузить данные пользователя');
                console.error(err);
            } finally {
                setIsLoading(false);
            }
        };

        fetchUserData();
    }, [userId]);


    const handleInputChange = (
        e: React.ChangeEvent<HTMLInputElement>
    ) => {
        const {name, value} = e.target;
        setFormData(prev => ({
            ...prev,
            [name]: value.trim() // Удаляем пробелы по краям
        }));
    };

    // Обработчик обновления пользователя
    const handleSubmit = async (
        e: React.FormEvent<HTMLFormElement>
    ) => {
        e.preventDefault();

        try {
            const updateData: UpdateUserRequestDto = {
                password: "",
                firstName: formData.firstName || null,
                lastName: formData.lastName || null,
                patronymic: formData.patronymic || null,
                phoneNumber: formData.phoneNumber
            };

            await updateUser(userId, updateData);

            setUser(prev => prev ? {
                ...prev,
                firstName: updateData.firstName || prev.firstName,
                lastName: updateData.lastName || prev.lastName,
                patronymic: updateData.patronymic || prev.patronymic,
                phoneNumber: updateData.phoneNumber
            } : null);

            setIsEditing(false);
        } catch (err) {
            setError('Не удалось обновить профиль');
            console.error(err);
        }
    };


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

    // Состояние загрузки
    if (isLoading) {
        return <div>Загрузка...</div>;
    }

    // Обработка ошибки
    if (error) {
        return <div>{error}</div>;
    }

    // Если пользователь не найден
    if (!user) {
        return <div>Пользователь не найден</div>;
    }

    return (
        <div className={styles.container}>
            <div className={styles.avatarContainer}>
                {renderAvatar()}
            </div>

            {isLoading ? (
                <div className={styles.loadingState}>Загрузка...</div>
            ) : error ? (
                <div className={styles.errorState}>{error}</div>
            ) : !user ? (
                <div className={styles.errorState}>Пользователь не найден</div>
            ) : !isEditing ? (
                <div>
                    <div className={styles.profileInfo}>
                        <h2 className={styles.profileName}>
                            {`${user.lastName} ${user.firstName} ${user.patronymic}`}
                        </h2>
                        <div className={styles.profileDetails}>
                            <p>Телефон: {user.phoneNumber}</p>
                            <p>Адрес: {user.address}</p>
                        </div>
                    </div>
                    <button
                        className={styles.editButton}
                        onClick={() => setIsEditing(true)}
                    >
                        Редактировать
                    </button>
                </div>
            ) : (
                <form
                    onSubmit={handleSubmit}
                    className={styles.form}
                >
                    <div>
                        <label className={styles.formLabel}>ФИО:</label>
                        <input
                            className={styles.formInput}
                            type="text"
                            name="lastName"
                            value={formData.lastName}
                            onChange={handleInputChange}
                            placeholder="Фамилия"
                            required
                        />
                        <input
                            className={styles.formInput}
                            type="text"
                            name="firstName"
                            value={formData.firstName}
                            onChange={handleInputChange}
                            placeholder="Имя"
                            required
                        />
                        <input
                            className={styles.formInput}
                            type="text"
                            name="patronymic"
                            value={formData.patronymic}
                            onChange={handleInputChange}
                            placeholder="Отчество"
                        />
                    </div>

                    <div>
                        <label className={styles.formLabel}>Номер телефона:</label>
                        <input
                            className={styles.formInput}
                            type="tel"
                            name="phoneNumber"
                            value={formData.phoneNumber}
                            onChange={handleInputChange}
                            placeholder="Номер телефона"
                            required
                        />
                    </div>

                    <div className={styles.formActions}>
                        <button
                            type="submit"
                            className={styles.saveButton}
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
                </form>
            )}
        </div>
    )
};

export default UserProfile;
