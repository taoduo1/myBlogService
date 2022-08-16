//封装 js用于存储token

const key = "token-key";

const setToken = (token) =>{
    return window.localStorage.setItem(key,token);
}
const getToken = () => {
    return window.localStorage.getItem(key);
}

const removeToken = ()=>{
    return window.localStorage.removeItem(key);
}

export {
    setToken,
    getToken,
    removeToken
}