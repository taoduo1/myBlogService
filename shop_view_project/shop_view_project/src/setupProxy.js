const proxy = require('http-proxy-middleware');
module.exports = function (app) {
    app.use(
        '/api',
        proxy.createProxyMiddleware({
            target: 'http://localhost:8300', // 后台服务地址以及端口号
            changeOrigin: true, // 是否跨域
            pathRewrite: {
                '^/api': '' // 路径重写,用/api代替target里的地址
            }
        })
    );
};