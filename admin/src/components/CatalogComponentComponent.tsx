import * as React from "react";
import {Checkbox, Divider, Input, Radio, Space, Typography} from "antd";
import ComponentCardComponent from "./ComponentCardComponent.tsx";
import {ComponentResponseDto} from "../api/models/response/catalog.ts";


const item: ComponentResponseDto = {id: 1, name: 'test', price: 100, isInStock: true}

const CatalogComponentComponent: React.FC = () => {
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
                            <Radio.Group onChange={(value) => {
                                console.log(value)
                            }}>

                                <Radio value='Сортировка кнопка'>Дата создания</Radio>

                            </Radio.Group>
                        </Space>


                        <Space direction='vertical'>
                            <p style={{margin: 0}}>Результат поиска в порядке:</p>
                            <Radio.Group onChange={(value) => {
                                console.log(value)
                            }}>
                                <Space direction='vertical'>
                                    <Radio value='Возрастания'>Возрастания</Radio>
                                    <Radio value='Убывания'>Убывания</Radio>
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
                        ></Input.Search>

                        <Checkbox.Group
                            options={[
                                {label: 'Показать только те, которые в наличии', value: 'inStock'}
                            ]}
                        />
                    </div>
                </div>
                <Divider/>
                <div style={{
                    display: 'flex',
                    flexDirection: 'column',
                    gap: '20px',
                    alignItems: 'center',
                }}>
                    <ComponentCardComponent item={item}/>
                    <ComponentCardComponent item={item}/>
                    <ComponentCardComponent item={item}/>
                    <ComponentCardComponent item={item}/>
                </div>
            </div>
        </div>
    )
}

export default CatalogComponentComponent;