import {Button, Flex, Form, FormProps, Input} from "antd";
import {useState} from "react";
import login from '../api/service/authService.ts'
import {saveAccessToken, saveRefreshToken} from "../util/auth.ts";
import {errorAlert} from "../util/notification.ts";
import {useNavigate} from "react-router";
// import {errorAlert} from "../util/notification.ts";

type FieldType = {
    username?: string;
    password?: string;
}

export default function Login() {
    const [loading, setLoading] = useState<boolean>(false);
    const navigate = useNavigate();

    const onFinish: FormProps<FieldType>['onFinish'] = (values) => {
        setLoading(true);
        const response = login({
            phoneNumber: values.username || '',
            password: values.password || ''
        })

        response
            .then((r) => {
                saveAccessToken(r.accessToken)
                saveRefreshToken(r.refreshToken)
                navigate('/catalog/category',{viewTransition: true})
            })
            .catch((err) => {
                errorAlert(err.message);
            })
            .finally(() => setLoading(false));
    }

    return (
        <Flex align='center' justify='center' style={{height: '100vh'}}>
            <Form
                labelCol={{span: 8}}
                wrapperCol={{span: 16}}
                name='basic'
                autoComplete='on'
                style={{maxWidth: 600, width: '100%'}}
                onFinish={onFinish}
            >
                <Form.Item<FieldType>
                    label='Логин'
                    name='username'
                    rules={[{required: true, message: 'Введите логин!'}]}
                >
                    <Input/>
                </Form.Item>

                <Form.Item<FieldType>
                    label='Пароль'
                    name='password'
                    rules={[{required: true, message: 'Введите пароль!'}]}
                >
                    <Input.Password/>
                </Form.Item>

                <Form.Item label={null}>
                    <Button type="primary" htmlType="submit" loading={loading}>
                        Войти
                    </Button>
                </Form.Item>
            </Form>
        </Flex>
    )
}