//login moudle
import { makeAutoObservable } from "mobx";
import { http, setToken, getToken } from "@/utils";

class LoginStore {
    token = getToken()||'';
    constructor() {
        //响应式
        makeAutoObservable(this);
    }
    getToken = async ({ username, password }) => {
        const res = await http.post('/api/user/user/loginUser', {
            password: password,
            username: username
        })
        //存入token
        this.token = res.data;
        console.log("123", this.token)
        //存入 localStorage
        setToken(this.token);
    }
}

export default LoginStore;