import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';
//引入index.scss
import './index.scss';
//导入antd样式文件
import 'antd/dist/antd.min.css'

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);
