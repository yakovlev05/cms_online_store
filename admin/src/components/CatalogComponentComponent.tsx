import * as React from "react";
import {Button, Checkbox, Divider, Input, InputNumber, Modal, Radio, Space, Typography} from "antd";
import ComponentCardComponent from "./ComponentCardComponent.tsx";
import {ComponentResponseDto} from "../api/models/response/catalog.ts";
import {useEffect, useState} from "react";
import {createComponent, deleteComponent, getListComponents} from "../api/service/catalogService.ts";
import {errorAlert, successAlert} from "../util/notification.ts";
import {ComponentRequestDto} from "../api/models/request/catalog.ts";
import {PlusOutlined} from "@ant-design/icons";

interface params {
    page: number;
    limit: number;
    isDescending: boolean;
    keySort: string;
    searchQuery: string | null;
    showOnlyInStock: boolean;
}

const CatalogComponentComponent: React.FC = () => {
    const [params, setParams] = useState<params>({
        page: 0,
        limit: 5,
        isDescending: true,
        keySort: "createdAt",
        searchQuery: null,
        showOnlyInStock: false,
    });
    const [hasMore, setHasMore] = useState<boolean>(true);
    const [items, setItems] = useState<ComponentResponseDto[]>([])

    // для создания нового компонента
    const [newComponentShowModal, setNewComponentShowModal] = useState(false);
    const [newComponent, setNewComponent] = useState<ComponentRequestDto>({
        price: 0,
        name: '',
        inStock: false,
    });

    useEffect(() => {
        const response = getListComponents(
            params.page,
            params.limit,
            params.isDescending,
            params.keySort,
            params.searchQuery,
            params.showOnlyInStock
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


    const handleDelete = (item: ComponentResponseDto) => {
        deleteComponent(item.name)
            .then(() => {
                setParams({...params, page: 0})
                successAlert(`Компонент ${item.name} удалён`)
            })
            .catch((e) => errorAlert(e.message));
    }

    const handleUpdate = (item: ComponentResponseDto) => {
        setItems(items.map(e => e.id === item.id ? item : e))
    }

    const handleCreate = (item: ComponentRequestDto) => {
        createComponent(item)
            .then(() => {
                setParams({...params, page: 0})
                successAlert(`Компонент создан`)
                setNewComponent({inStock: false, price: 0, name: ''})
                setNewComponentShowModal(false)
            })
            .catch((e) => {
                errorAlert(e.message)
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
                              }}
            >
                Управление компонентами
            </Typography.Title>
            <div style={{
                backgroundColor: 'white',
                borderRadius: '8px',
                height: '100%',
                padding: '30px',
            }}>
                <div style={{
                    display: 'flex',
                    justifyContent: 'space-around'
                }}>
                    <div style={{gap: '50px', display: 'flex'}}>
                        <Space direction='vertical'>
                            <p style={{margin: 0}}>Ключ сортировки:</p>
                            <Radio.Group
                                value={params.keySort}
                                onChange={(e) => {
                                    setParams({...params, keySort: e.target.value, page: 0});
                                }}>
                                <Space direction='vertical'>
                                    <Radio value='createdAt'>Дата создания</Radio>
                                    <Radio value='price'>Цена</Radio>
                                </Space>
                            </Radio.Group>
                        </Space>


                        <Space direction='vertical'>
                            <p style={{margin: 0}}>Результат поиска в порядке:</p>
                            <Radio.Group
                                value={String(params.isDescending)}
                                onChange={(e) => 
                                    setParams({...params, isDescending: e.target.value === 'true', page: 0})}>
                                <Space direction='vertical'>
                                    <Radio value='false'>Возрастания</Radio>
                                    <Radio value='true'>Убывания</Radio>
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
                            placeholder='Введите название компонента'
                            loading={false}
                            size='middle'
                            enterButton='Поиск'
                            onSearch={(e) => setParams({...params, searchQuery: e, page: 0})}
                        ></Input.Search>

                        <Checkbox.Group
                            onChange={(e) => setParams({...params, showOnlyInStock: e.includes('inStock'), page: 0})}
                            value={[params.showOnlyInStock ? "inStock" : null]}
                            options={[{label: 'Показать только те, которые в наличии', value: 'inStock'}]}
                        />

                        <Button type="default" icon={<PlusOutlined/>}
                                onClick={() => setNewComponentShowModal(true)}>Создать новый</Button>
                        <Modal
                            title='Создание компонента'
                            open={newComponentShowModal}
                            onCancel={() => setNewComponentShowModal(false)}
                            okText='Создать'
                            cancelText='Отмена'
                            onOk={() => handleCreate(newComponent)}
                            // width={700}
                        >
                            <div style={{display: 'flex', flexDirection: 'column', gap: '10px'}}>
                                <div>
                                    <p style={{margin: '0px'}}>Название</p>
                                    <Input placeholder='Название'
                                           value={newComponent.name}
                                           onChange={(e) => setNewComponent({...newComponent, name: e.target.value})}
                                    />
                                </div>

                                <div style={{display: 'flex', gap: '20px', alignItems: 'end'}}>
                                    <div>
                                        <p style={{margin: '0px'}}>Цена ₽</p>
                                        <InputNumber min={1} defaultValue={10}
                                                     value={newComponent.price}
                                                     onInput={(e) => setNewComponent({
                                                         ...newComponent,
                                                         price: Number(e)
                                                     })}
                                        />
                                    </div>

                                    <Checkbox.Group
                                        options={[{label: 'В наличии', value: 'inStock'}]}
                                        value={[newComponent.inStock ? 'inStock' : null]}
                                        onChange={(e) => setNewComponent({
                                            ...newComponent,
                                            inStock: e.includes('inStock')
                                        })}
                                    />
                                </div>
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
                                <ComponentCardComponent item={item} key={item.id} onDelete={handleDelete}
                                                        onUpdate={handleUpdate}/>
                            )
                        })
                    }
                    {
                        hasMore && items.length !== 0 && <Button type="primary"
                                                                 onClick={() => setParams({
                                                                     ...params,
                                                                     page: params.page + 1
                                                                 })}
                        >Загрузить ещё</Button>
                    }
                </div>
            </div>
        </div>
    )
}

export default CatalogComponentComponent;