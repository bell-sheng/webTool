import React, {memo} from "react";

import {HashRouter} from "react-router-dom";

import "./assets/css/base.css"
import HomeHeader from "./components/home-header";
import HomeFooter from "./components/home-footer";
import Router from "./router";
import {Affix, Avatar, Button, Col, Layout, Row, Slider, Space, Tooltip} from "antd";
import {PlayCircleOutlined, StepBackwardOutlined, StepForwardOutlined, UserOutlined} from "@ant-design/icons";

function App() {
    return (
        <HashRouter>
            <HomeHeader/>
            <Router/>
            <HomeFooter/>
        </HashRouter>
    );
}

export default memo(App);