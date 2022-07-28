//login moudle
import { makeAutoObservable } from "mobx";
import { http } from "@/utils";

class LoginStore {
    token = "";
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
        this.token = res.data.data;
        
    }
}

export default LoginStore;