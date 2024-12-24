import React, {useEffect, useState} from "react";
import {Button, Divider, Input, Modal, Radio, Space, Typography} from "antd";
import {PlusOutlined} from "@ant-design/icons";
import {createCategory, deleteCategory, getListCategories} from "../api/service/catalogService.ts";
import {CategoryResponseDto} from "../api/models/response/catalog.ts";
import {errorAlert, successAlert} from "../util/notification.ts";
import CategoryCardComponent from "./CategoryCardComponent.tsx";
import {CategoryRequestDto} from "../api/models/request/catalog.ts";

interface params {
    page: number,
    limit: number,
    directionSort: string,
    keySort: string,
    searchQuery: string,
}

const CatalogCategoryComponent: React.FC = () => {
    const [params, setParams] = useState<params>({
        page: 0,
        limit: 5,
        directionSort: 'desc',
        keySort: 'createdAt',
        searchQuery: ''
    })
    const [hasMore, setHasMore] = useState<boolean>(true);
    const [items, setItems] = useState<CategoryResponseDto[]>([]);


    // для создания новой категории
    const [newCategoryMode, setNewCategoryMode] = useState<boolean>(false);
    const [newCategory, setNewCategory] = useState<CategoryRequestDto>({
        name: ''
    });
    useEffect(() => {
        const response = getListCategories(
            params.page,
            params.limit,
            params.directionSort,
            params.keySort,
            params.searchQuery
        );

        response
            .then((r) => {
                if (r.length < params.limit) {
                    setHasMore(false)
                }

                setItems(prev => {
                    if (params.page > 0) {
                        return [...prev, ...r];
                    } else {
                        return r;
                    }
                })
            })
            .catch((err) => {
                errorAlert(err.message);
            })

    }, [params]);

    const handleDelete = (item: CategoryResponseDto) => {
        deleteCategory(item.urlName)
            .then(() => {
                setParams({...params, page: 0})
                successAlert(`Категория ${item.name} удалена`)
            })
            .catch((err) => {
                errorAlert(err.message)
            })
    }

    const handleUpdate = (item: CategoryResponseDto) => {
        setItems(items.map(e => e.id === item.id ? item : e))
    }

    const handleCreate = (item: CategoryRequestDto) => {
        createCategory(item)
            .then(() => {
                setParams({...params, page: 0})
                successAlert('Категория создана')
                setNewCategory({name: ''})
                setNewCategoryMode(false)
            })
            .catch((err) => {
                errorAlert(err.message)
            })
    }

    return (
        <div style={{
            flex: '1',
            background: '#f5f5f5',
            display: 'flex',
            flexDirection: 'column',
            padding: '0 50px 50px 50px',
            gap: '50px',
        }}>
            <Typography.Title level={2}
                              style={{
                                  background: 'white',
                                  borderRadius: '8px',
                                  height: '70px',
                                  display: 'flex',
                                  alignItems: 'center',
                                  justifyContent: 'center',
                              }}>Управление категориями
            </Typography.Title>

            <div style={{
                backgroundColor: 'white',
                borderRadius: '8px',
                height: '100%',
                padding: '30px',
            }}>
                <div style={{display: 'flex', justifyContent: 'space-around'}}>
                    <div style={{gap: '50px', display: 'flex'}}>
                        <Space direction='vertical'>
                            <p style={{margin: 0}}>Ключ сортировки:</p>
                            <Radio.Group
                                value={params.keySort}
                                onChange={(e) => {
                                    setParams({...params, keySort: e.target.value, page: 0});
                                }}
                            >
                                <Space direction='vertical'>
                                    <Radio value='createdAt'>Дата создания</Radio>
                                </Space>
                            </Radio.Group>
                        </Space>

                        <Space direction='vertical'>
                            <p style={{margin: 0}}>Результат поиска в порядке:</p>
                            <Radio.Group
                                value={params.directionSort}
                                onChange={(e) => setParams({...params, directionSort: e.target.value, page: 0})}
                            >
                                <Space direction='vertical'>
                                    <Radio value='asc'>Возрастания</Radio>
                                    <Radio value='desc'>Убывания</Radio>
                                </Space>
                            </Radio.Group>
                        </Space>

                    </div>

                    <div style={{
                        display: 'flex',
                        flexDirection: 'column',
                        maxWidth: '555px',
                        width: '100%',
                        gap: '20px'
                    }}>
                        <Input.Search
                            placeholder='Введите название категории'
                            loading={false}
                            size='middle'
                            enterButton='Поиск'
                            onSearch={(e) => setParams({...params, searchQuery: e, page: 0})}
                        ></Input.Search>

                        <Button type="default" icon={<PlusOutlined/>}
                            onClick={() => setNewCategoryMode(true)}
                        >Добавить категорию</Button>
                        <Modal
                            title='Создание категории'
                            open={newCategoryMode}
                            onCancel={() => setNewCategoryMode(false)}
                            okText='Сохранить'
                            cancelText='Отмена'
                            onOk={() => handleCreate(newCategory)}
                        >
                            <div>
                                <Input
                                    placeholder='Название'
                                    value={newCategory.name}
                                    onChange={e => setNewCategory({...newCategory, name: e.target.value})}
                                ></Input>
                            </div>
                        </Modal>
                    </div>
                </div>
                <Divider/>
                <div style={{
                    display: 'flex',
                    flexDirection: 'column',
                    gap: '20px',
                    alignItems: 'center',
                }}>
                    {
                        items.map((item) => {
                            return (
                                <CategoryCardComponent
                                    item={item}
                                    key={item.id}
                                    onDelete={handleDelete}
                                    onUpdate={handleUpdate}
                                />
                            )
                        })
                    }

                    {
                        hasMore && items.length !== 0 && <Button type="primary"
                                                                 onClick={() => setParams({
                                                                     ...params,
                                                                     page: params.page + 1
                                                                 })}>
                            Загрузить ещё
                        </Button>
                    }
                </div>
            </div>
        </div>
    )
}

export default CatalogCategoryComponent