import { Card, Form, Input, Button, Checkbox, message } from "antd"
import logo from "@/assets/logo.png"
import './index.scss'
import { useStore } from "@/store"
import { useNavigate } from "react-router-dom"


const Login = () => {

    const { loginStore } = useStore();
    const navigate = useNavigate();

    //登录方法
    async function onFinish(values) {
        try{
            await loginStore.getToken({
                username: values.username,
                password: values.password
            });
            //跳转首页
            navigate('/layout');
            message.success('登录成功');
        }catch(e){
            message.success(e.response?.data?.message || '登录失败');
        }
        
    };


    return (
        <div className="login">
            <Card className="login-container">
                <img className="login-logo" src={logo} alt="" />
                <Form
                    initialValues={{
                        remember: true,
                    }}
                    validateTrigger={['onBlur']}
                    onFinish={onFinish}
                    autoComplete="off"
                >

                    <Form.Item
                        name="username"
                        rules={[
                            {
                                required: true,
                                message: '请输入用户名！',
                            }
                        ]}
                    >
                        <Input size="large" placeholder="请输入用户名" />
                    </Form.Item>
                    <Form.Item
                        name="password"
                        rules={[
                            {
                                required: true,
                                message: '请输入密码！',
                            },
                        ]}
                    >
                        <Input.Password size="large" placeholder="请输入密码" />
                    </Form.Item>
                    <Form.Item
                        name="remember"
                        valuePropName="checked"
                    >
                        <Checkbox className="login-checkbox-label" >
                            我已阅读并同意用户协议和隐私条款
                        </Checkbox>
                    </Form.Item>
                    <Form.Item>
                        <Button type="primary" htmlType="submit" size="large" block>
                            登录
                        </Button>
                    </Form.Item>
                </Form>
            </Card>
        </div>
    )
}
export default Login