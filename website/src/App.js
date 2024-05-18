import React, {memo} from "react";

import {HashRouter} from "react-router-dom";

import "./assets/css/base.css"
import HomeHeader from "./components/home-header";
import HomeFooter from "./components/home-footer";
import Router from "./router";
import {Affix, Avatar, Button, Col, Layout, Row, Slider, Space, Tooltip} from "antd";
import {PlayCircleOutlined, StepBackwardOutlined, StepForwardOutlined, UserOutlined} from "@ant-design/icons";

function App() {
    const [bottom, setBottom] = React.useState(100);
    const layoutStyle = {
        marginLeft: 5,
        marginTop: 5
    };
    const {Header, Footer, Sider, Content} = Layout;
    return (
        <HashRouter>
            <HomeHeader/>
            <Router/>
            <Affix offsetBottom={bottom}>
                <Row gutter={16} align="middle">
                    <Col flex={2} align="right">
                        <Space>
                            <Tooltip title="上一曲">
                                <Button shape="circle" icon={<StepBackwardOutlined/>}/>
                            </Tooltip>
                            <Tooltip title="播放">
                                <Button shape="circle" icon={<PlayCircleOutlined/>}/>
                            </Tooltip>
                            <Tooltip title="下一曲">
                                <Button shape="circle" icon={<StepForwardOutlined/>}/>
                            </Tooltip>
                        </Space>
                    </Col>
                    <Col flex={2}>
                        <Row>
                            <Col flex="50px" align="middle">
                                <Avatar shape="square" size={48} icon={<UserOutlined/>}/>
                            </Col>
                            <Col flex="auto">
                                <Content style={layoutStyle}>{'周杰伦-听妈妈的话'}</Content>
                                <Slider defaultValue={30} disabled={false}/>
                            </Col>
                        </Row>
                    </Col>
                    <Col flex={2}>col-9</Col>
                </Row>
            </Affix>
            <HomeFooter/>
        </HashRouter>
    );
}

export default memo(App);